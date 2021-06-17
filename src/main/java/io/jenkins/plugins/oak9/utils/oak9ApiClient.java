package io.jenkins.plugins.oak9.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.model.TaskListener;
import io.jenkins.plugins.oak9.model.ApiResponse;
import io.jenkins.plugins.oak9.model.ValidationResult;
import okhttp3.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class oak9ApiClient<Int> {

    /**
     * The base url of the oak9 API
     */
    private String baseUrl;

    /**
     * oak9 API Key
     */
    private final String key;

    /**
     * oak9 org ID
     */
    private final String orgId;

    /**
     * oak9 Project ID
     */
    private final String projectId;

    /**
     * okhttp3 client
     */
    private final OkHttpClient client;

    /**
     * Max attempts to communicate with an API endpoint before giving up
     */
    private final int maxAttempts = 30;

    /**
     * Jenkins task listener, primarily used for logging
     */
    private final TaskListener jenkinsTaskListener;

    /**
     * Constructor
     *
     * @param baseUrl
     * @param key
     * @param orgId
     * @param projectId
     * @param jenkinsTaskListener
     */
    public oak9ApiClient(String baseUrl, String key, String orgId, String projectId, TaskListener jenkinsTaskListener) {
        this.baseUrl = baseUrl;
        this.key = key;
        this.orgId = orgId;
        this.projectId = projectId;
        this.jenkinsTaskListener = jenkinsTaskListener;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * post file for validation endpoint without providing a starting count of attempts
     *
     * @param file
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ValidationResult postFileValidation(File file) throws IOException, InterruptedException {
        return this.postFileValidation(file, 0);
    }

    /**
     * post file for validation endpoint with a starting count of attempts
     * @param file
     * @param attempts
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ValidationResult postFileValidation(File file, int attempts) throws IOException, InterruptedException {

        if (!file.exists()) {
            throw new InterruptedException("File sent to API POST does not exist.\n");
        } else {
            jenkinsTaskListener.getLogger().println("File exists, size is " + file.length());
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", file.getName(),
                        RequestBody.create(file, MediaType.parse("application/zip")))
                .build();

        String credentials = Credentials.basic(this.orgId, this.key);
        Request request = new Request.Builder()
                .header("Authorization", credentials)
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Accept-Encoding", "gzip, deflate, br")
                .url(this.baseUrl + "/" + this.orgId + "/validations/proj-" + this.projectId + "/queue/iac?files")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            switch (response.code()) {
                case 200:
                    ObjectMapper mapper = new ObjectMapper();
                    ApiResponse apiResponse = mapper.readValue(response.body().charStream(), ApiResponse.class);
                    return apiResponse.getResult();
                case 503:
                    jenkinsTaskListener.getLogger().println("oak9 API rate limit reached. Waiting 1s before trying again.\n");
                    if (attempts > this.maxAttempts) {
                        throw new IOException("Communication with oak9 API has timed out.\n");
                    }

                    attempts++;
                    Thread.sleep(1000);
                    return postFileValidation(file , attempts);
                default:
                    throw new IOException("Communication with oak9 API unsuccessful. Received error code " + response.code() + "\n");
            }
        }
    }

    /**
     * Poll oak9 for validation status
     *
     * @param result
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ValidationResult pollStatus(ValidationResult result) throws IOException, InterruptedException {
        return pollStatus(result, 0);
    }

    /**
     * Poll oak9 for validation status
     *
     * @param result
     * @param attempts
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ValidationResult pollStatus(ValidationResult result, int attempts) throws IOException, InterruptedException {
        String credentials = Credentials.basic(this.orgId, this.key);
        Request request = new Request.Builder()
                .header("Authorization", credentials)
                .url(this.baseUrl + "/" + this.orgId + "/validations/proj-" + this.projectId + "/" + result.getRequestId() + "/status")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                ApiResponse apiResponse = mapper.readValue(response.body().charStream(), ApiResponse.class);
                switch (apiResponse.getStatus().toLowerCase()) {
                    case "queued":
                    case "inprogress":
                        if (attempts > this.maxAttempts) {
                            throw new IOException("Max attempts reached to obtain an Oak9 status. Aborting.\n");
                        }

                        attempts++;
                        jenkinsTaskListener.getLogger().println("Waiting for results (" + attempts + " seconds elapsed)\n");
                        Thread.sleep(1000);
                        return pollStatus(result, attempts);
                    case "completed":
                        jenkinsTaskListener.getLogger().println("Scanning completed. Returning results...\n");
                        return apiResponse.getResult();
                    default:
                        throw new IOException("Unexpected task status: " + apiResponse.getStatus() + ".\n");
                }
            } else {
                // if we got a timeout or a slow down response, let's wait 10s and try again
                if (response.code() == 503 || response.code() == 504) {
                    attempts++;
                    jenkinsTaskListener.getLogger().println("Received a rate limit or timeout. Pausing 10s before trying again.\n");
                    Thread.sleep(10000);
                    return pollStatus(result, attempts);
                }
                throw new IOException("API Request Unsuccessful. Received error code: " + response.code() + "\n");
            }
        }
    }
}
