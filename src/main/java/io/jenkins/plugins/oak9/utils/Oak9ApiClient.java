package io.jenkins.plugins.oak9.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.model.TaskListener;
import io.jenkins.plugins.oak9.model.ApiResponse;
import io.jenkins.plugins.oak9.model.ValidationResult;
import okhttp3.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Oak9ApiClient {

    /**
     * The base url of the oak9 API
     */
    private String baseUrl;

    /**
     * oak9 API Key
     */
    private String key;

    /**
     * oak9 org ID
     */
    private String orgId;

    /**
     * oak9 Project ID
     */
    private String projectId;

    /**
     * okhttp3 client
     */
    private OkHttpClient client;

    /**
     * Max attempts to communicate with an API endpoint before giving up
     */
    private static final int maxAttempts = 30;

    /**
     * Jenkins task listener, primarily used for logging
     */
    private TaskListener jenkinsTaskListener;

    /**
     * A delay in ms to wait before re-attempting an API request that received a transient error
     */
    private static final int transientErrorDelayInMs = 1000;

    /**
     * Constructor
     *  @param baseUrl
     * @param key
     * @param orgId
     * @param projectId
     * @param jenkinsTaskListener
     */
    public Oak9ApiClient(
            String baseUrl,
            String key,
            String orgId,
            String projectId,
            TaskListener jenkinsTaskListener,
            OkHttpClient httpClient
    ) {
        this.baseUrl = baseUrl;
        this.key = key;
        this.orgId = orgId;
        this.projectId = projectId;
        this.jenkinsTaskListener = jenkinsTaskListener;
        this.client = httpClient;
    }

    /**
     * post file for validation endpoint without providing a starting count of attempts
     *
     * @param file
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ValidationResult postFileValidation(String fileName, ByteArrayOutputStream file) throws IOException, InterruptedException {
        return this.postFileValidation(fileName, file, 0);
    }

    /**
     * post file for validation endpoint with a starting count of attempts
     * @param file the zip file to be posted
     * @param attempts current number of attempts
     * @return the oak9 validation result
     * @throws IOException thrown if the file is invalid or the oak9 API request fails
     */
    public ValidationResult postFileValidation(String fileName, ByteArrayOutputStream file, int attempts) throws IOException, InterruptedException {
        InputStream fileContent = new ByteArrayInputStream(file.toByteArray());
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", fileName,
                        new RequestBodyBuilder(fileContent, MediaType.parse("application/zip")).create().getRequestBody())
                .build();

        String credentials = Credentials.basic(this.orgId, this.key);
        Request request = new Request.Builder()
                .header("Authorization", credentials)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .url(this.baseUrl + "/" + this.orgId + "/validations/" + this.projectId + "/queue/iac?files")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            switch (response.code()) {
                case 200:
                    ObjectMapper mapper = new ObjectMapper();
                    ResponseBody responseBody = response.body();
                    if (responseBody == null) {
                        throw new IOException("Unable to determine results from API response.\n");
                    } else {
                        ApiResponse apiResponse = mapper.readValue(responseBody.charStream(), ApiResponse.class);
                        return apiResponse.getResult();
                    }
                case 503:
                    jenkinsTaskListener.getLogger().println("oak9 API rate limit reached. Waiting 1s before trying again.\n");
                    if (attempts > this.maxAttempts) {
                        throw new IOException("Communication with oak9 API has timed out.\n");
                    }

                    attempts++;
                    pauseApiRequests(transientErrorDelayInMs);
                    return postFileValidation(fileName, file , attempts);
                default:
                    throw new IOException("Communication with oak9 API unsuccessful. Received error code " + response.code() + "\n");
            }
        }
    }

    /**
     * Poll oak9 for validation status
     *
     * @param result the scan for which we need to check the status
     * @return the validation result portion of the API response
     * @throws IOException thrown if the oak9 api request fails
     */
    public ApiResponse pollStatus(ValidationResult result) throws IOException {
        return pollStatus(result, 0);
    }

    /**
     * Sleep function that handles exceptions thrown by the sleep function
     *
     * @param delay an integer expressing the length to sleep in milliseconds
     * @throws IOException thrown if sleep fails
     */
    private void pauseApiRequests(int delay) throws IOException {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new IOException("Unable to sleep thread");
        }
    }

    /**
     * Poll oak9 for validation status
     *
     * @param result the scan for which we need to check the status
     * @param attempts the number of attempts thus far
     * @return the ValidationResult portion of the API response
     * @throws IOException thrown in the event of a communication failure with the oak9 API
     */
    public ApiResponse pollStatus(ValidationResult result, int attempts) throws IOException {
        String credentials = Credentials.basic(this.orgId, this.key);
        Request request = new Request.Builder()
                .header("Authorization", credentials)
                .url(this.baseUrl + "/" + this.orgId + "/validations/" + this.projectId + "/" + result.getRequestId() + "/status")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    throw new IOException("Unable to read API response body.\n");
                } else {
                    ApiResponse apiResponse = mapper.readValue(responseBody.charStream(), ApiResponse.class);
                    switch (apiResponse.getStatus().toLowerCase()) {
                        case "queued":
                        case "inprogress":
                            if (attempts > maxAttempts) {
                                throw new IOException("Max attempts reached to obtain an Oak9 status. Aborting.\n");
                            }

                            attempts++;
                            jenkinsTaskListener.getLogger().println("Waiting for results (" + attempts + " seconds elapsed)\n");
                            pauseApiRequests(transientErrorDelayInMs);
                            return pollStatus(result, attempts);
                        case "completed":
                            jenkinsTaskListener.getLogger().println("Scanning completed. Returning results...\n");
                            return apiResponse;
                        default:
                            throw new IOException("Unexpected task status: " + apiResponse.getStatus() + ".\n");
                    }
                }
            } else {
                // if we got a timeout or a slow down response, let's wait 10s and try again
                if (response.code() == 503 || response.code() == 504) {
                    attempts++;
                    jenkinsTaskListener.getLogger().println("Received a rate limit or timeout. Pausing 10s before trying again.\n");
                    pauseApiRequests(transientErrorDelayInMs);
                    return pollStatus(result, attempts);
                }
                throw new IOException("API Request Unsuccessful. Received error code: " + response.code() + "\n");
            }
        }
    }
}
