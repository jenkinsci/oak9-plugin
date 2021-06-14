package io.jenkins.plugins.oak9.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jenkins.plugins.oak9.model.ApiResponse;
import io.jenkins.plugins.oak9.model.ValidationResult;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class oak9ApiClient<Int> {

    private final String baseUrl;
    private final String key;
    private final String orgId;
    private final String projectId;
    private final OkHttpClient client;
    private final int maxAttempts = 30;
    private static final MediaType MEDIA_TYPE_ZIP = MediaType.parse("application/zip");

    public oak9ApiClient(String baseUrl, String key, String orgId, String projectId) {
        this.baseUrl = baseUrl;
        this.key = key;
        this.orgId = orgId;
        this.projectId = projectId;
        this.client = new OkHttpClient();
    }

    public ValidationResult postFileValidation(File file) throws IOException, InterruptedException {
        return this.postFileValidation(file, 0);
    }

    public ValidationResult postFileValidation(File file, int attempts) throws IOException, InterruptedException {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", file.getName(),
                        RequestBody.create(file, MEDIA_TYPE_ZIP))
                .build();

        String credentials = Credentials.basic(this.orgId, this.key);
        Request request = new Request.Builder()
                .header("Authorization", credentials)
                .url(this.baseUrl + "/" + this.orgId + "/validations/" + this.projectId + "/queue?iac=files")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            switch (response.code()) {
                case 200:
                    ObjectMapper mapper = new ObjectMapper();
                    ApiResponse apiResponse = mapper.readValue(response.body().charStream(), ApiResponse.class);
                    ValidationResult validationResult = apiResponse.getResult();
                    return validationResult;
                case 503:
                    if (attempts > this.maxAttempts) {
                        throw new InterruptedException("Communication with oak9 API has timed out.");
                    }

                    attempts++;
                    Thread.sleep(1000);
                    postFileValidation(file , attempts);
                    break;
                default:
                    throw new InterruptedException("Communication with oak9 API unsuccessful.");
            }
        }
        throw new InterruptedException("Unable to complete API request.");
    }
}
