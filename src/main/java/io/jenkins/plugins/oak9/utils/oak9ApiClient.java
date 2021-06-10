package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.rmi.server.RemoteRef;

public class oak9ApiClient<Int> {

    private final String baseUrl;
    private final String key;
    private final String orgId;
    private final String projectId;
    private final OkHttpClient client;
    private final int maxAttempts = 30;

    public oak9ApiClient(String baseUrl, String key, String orgId, String projectId) {
        this.baseUrl = baseUrl;
        this.key = key;
        this.orgId = orgId;
        this.projectId = projectId;
        this.client = new OkHttpClient();
    }

    public void postFileValidation(FilePath file) throws IOException, InterruptedException {
        this.postFileValidation(file, 0);
    }

    public void postFileValidation(FilePath file, int attempts) throws IOException, InterruptedException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"files\""),
                        RequestBody.create(
                                MediaType.parse(file.getName()),
                                new File(file.getName())))
                .build();

        Request request = new Request.Builder()
                .url(this.baseUrl + "/queue/iac?files")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        switch (response.code()) {
            case 200:
                return;
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
}
