package io.jenkins.plugins.oak9.utils;

import static org.mockito.Mockito.mock;

import hudson.model.TaskListener;
import io.jenkins.plugins.oak9.model.ValidationResult;

import java.io.IOException;

import okhttp3.OkHttpClient;
import org.junit.Test;

public class oak9ApiClientTest {
    private String orgId = "testorg";
    private String projectId = "testorg-1";
    private String apiKey = "42d4a62c53350993ea41069e9f2cfdefb0df097d";
    private String baseUrl = "https://oak9.io";

    @Test
    public void testConstructor() {
        TaskListener jenkinsTaskListener = mock(TaskListener.class);
        new Oak9ApiClient(this.baseUrl, this.apiKey, this.orgId, this.projectId, jenkinsTaskListener, mock(OkHttpClient.class));
    }

//    @Test
//    public void testPollStatus() throws IOException {
//        TaskListener jenkinsTaskListener = mock(TaskListener.class);
//        oak9ApiClient oak9ApiClient = new oak9ApiClient(this.baseUrl, this.apiKey, this.orgId, this.projectId,
//                jenkinsTaskListener, mock(OkHttpClient.class));
//        oak9ApiClient.pollStatus(new ValidationResult());
//    }


}