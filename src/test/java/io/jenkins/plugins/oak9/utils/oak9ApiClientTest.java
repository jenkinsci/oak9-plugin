package io.jenkins.plugins.oak9.utils;

import hudson.model.TaskListener;
import io.jenkins.plugins.oak9.model.ApiResponse;
import io.jenkins.plugins.oak9.model.DesignGap;
import io.jenkins.plugins.oak9.model.ResultRef;
import io.jenkins.plugins.oak9.model.ValidationResult;
import io.jenkins.plugins.oak9.model.Violation;
import org.apache.commons.io.output.BrokenOutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class oak9ApiClientTest {

    @Mock
    private TaskListener mockJenkinsTaskListener;

    private oak9ApiClient oak9ApiClientUnderTest;

    private AutoCloseable mockitoCloseable;

    @BeforeEach
    void setUp() {
        mockitoCloseable = openMocks(this);
        oak9ApiClientUnderTest = mock(oak9ApiClient.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockitoCloseable.close();
    }

    @Test
    void testPostFileValidation1() throws Exception {
        // Setup
        final File file = new File("filename.txt");

        // Configure TaskListener.getLogger(...).
        final PrintStream spyPrintStream = spy(new PrintStream(new ByteArrayOutputStream(), false, "utf-8"));
        when(mockJenkinsTaskListener.getLogger()).thenReturn(spyPrintStream);

        // Run the test
        final ValidationResult result = oak9ApiClientUnderTest.postFileValidation(file);

        // Verify the results
        verify(spyPrintStream).close();
    }

    @Test
    void testPostFileValidation1_TaskListenerReturnsBrokenIo() throws Exception {
        // Setup
        final File file = new File("filename.txt");

        // Configure TaskListener.getLogger(...).
        final PrintStream spyPrintStream = spy(new PrintStream(new BrokenOutputStream(), false, "utf-8"));
        when(mockJenkinsTaskListener.getLogger()).thenReturn(spyPrintStream);

        // Run the test
        assertThrows(IOException.class, () -> oak9ApiClientUnderTest.postFileValidation(file));
        verify(spyPrintStream).close();
    }

    @Test
    void testPostFileValidation2() throws Exception {
        // Setup
        final File file = new File("filename.txt");

        // Configure TaskListener.getLogger(...).
        final PrintStream spyPrintStream = spy(new PrintStream(new ByteArrayOutputStream(), false, "utf-8"));
        when(mockJenkinsTaskListener.getLogger()).thenReturn(spyPrintStream);

        // Run the test
        final ValidationResult result = oak9ApiClientUnderTest.postFileValidation(file, 0);

        // Verify the results
        verify(spyPrintStream).close();
    }

    @Test
    void testPostFileValidation2_TaskListenerReturnsBrokenIo() throws Exception {
        // Setup
        final File file = new File("filename.txt");

        // Configure TaskListener.getLogger(...).
        final PrintStream spyPrintStream = spy(new PrintStream(new BrokenOutputStream(), false, "utf-8"));
        when(mockJenkinsTaskListener.getLogger()).thenReturn(spyPrintStream);

        // Run the test
        assertThrows(IOException.class, () -> oak9ApiClientUnderTest.postFileValidation(file, 0));
        verify(spyPrintStream).close();
    }

    @Test
    void testPollStatus1() throws Exception {
        // Setup
        final ValidationResult result = new ValidationResult("requestId", "repositoryName", "timeStamp", Arrays.asList("value"), "status", "errorMessage", "source", Arrays.asList(new DesignGap("requirementId", "requirementName", "source", "resourceName", "resourceId", "resourceType", "oak9Guidance", Arrays.asList(new Violation("policyId", "requirementId", "requirementName", "attributeKey", "attributeValue", "oak9RecommendedValue", "errorMessage", "severity", "oak9Severity", "documentation")))), new ResultRef("entityType", "refType", "s3RefKey", "docRefPrimaryKey", "docRefEntityKey"), Arrays.asList("value"), "orgId", "projectId", "version", "entityType");

        // Configure TaskListener.getLogger(...).
        final PrintStream spyPrintStream = spy(new PrintStream(new ByteArrayOutputStream(), false, "utf-8"));
        when(mockJenkinsTaskListener.getLogger()).thenReturn(spyPrintStream);

        // Run the test
        final ApiResponse result2 = oak9ApiClientUnderTest.pollStatus(result);

        // Verify the results
        verify(spyPrintStream).close();
    }

    @Test
    void testPollStatus1_TaskListenerReturnsBrokenIo() throws Exception {
        // Setup
        final ValidationResult result = new ValidationResult("requestId", "repositoryName", "timeStamp", Arrays.asList("value"), "status", "errorMessage", "source", Arrays.asList(new DesignGap("requirementId", "requirementName", "source", "resourceName", "resourceId", "resourceType", "oak9Guidance", Arrays.asList(new Violation("policyId", "requirementId", "requirementName", "attributeKey", "attributeValue", "oak9RecommendedValue", "errorMessage", "severity", "oak9Severity", "documentation")))), new ResultRef("entityType", "refType", "s3RefKey", "docRefPrimaryKey", "docRefEntityKey"), Arrays.asList("value"), "orgId", "projectId", "version", "entityType");

        // Configure TaskListener.getLogger(...).
        final PrintStream spyPrintStream = spy(new PrintStream(new BrokenOutputStream(), false, "utf-8"));
        when(mockJenkinsTaskListener.getLogger()).thenReturn(spyPrintStream);

        // Run the test
        assertThrows(IOException.class, () -> oak9ApiClientUnderTest.pollStatus(result));
        verify(spyPrintStream).close();
    }

    @Test
    void testPollStatus2() throws Exception {
        // Setup
        final ValidationResult result = new ValidationResult("requestId", "repositoryName", "timeStamp", Arrays.asList("value"), "status", "errorMessage", "source", Arrays.asList(new DesignGap("requirementId", "requirementName", "source", "resourceName", "resourceId", "resourceType", "oak9Guidance", Arrays.asList(new Violation("policyId", "requirementId", "requirementName", "attributeKey", "attributeValue", "oak9RecommendedValue", "errorMessage", "severity", "oak9Severity", "documentation")))), new ResultRef("entityType", "refType", "s3RefKey", "docRefPrimaryKey", "docRefEntityKey"), Arrays.asList("value"), "orgId", "projectId", "version", "entityType");

        // Configure TaskListener.getLogger(...).
        final PrintStream spyPrintStream = spy(new PrintStream(new ByteArrayOutputStream(), false, "utf-8"));
        when(mockJenkinsTaskListener.getLogger()).thenReturn(spyPrintStream);

        // Run the test
        final ApiResponse result2 = oak9ApiClientUnderTest.pollStatus(result, 0);

        // Verify the results
        verify(spyPrintStream).close();
    }

    @Test
    void testPollStatus2_TaskListenerReturnsBrokenIo() throws Exception {
        // Setup
        final ValidationResult result = new ValidationResult("requestId", "repositoryName", "timeStamp", Arrays.asList("value"), "status", "errorMessage", "source", Arrays.asList(new DesignGap("requirementId", "requirementName", "source", "resourceName", "resourceId", "resourceType", "oak9Guidance", Arrays.asList(new Violation("policyId", "requirementId", "requirementName", "attributeKey", "attributeValue", "oak9RecommendedValue", "errorMessage", "severity", "oak9Severity", "documentation")))), new ResultRef("entityType", "refType", "s3RefKey", "docRefPrimaryKey", "docRefEntityKey"), Arrays.asList("value"), "orgId", "projectId", "version", "entityType");

        // Configure TaskListener.getLogger(...).
        final PrintStream spyPrintStream = spy(new PrintStream(new BrokenOutputStream(), false, "utf-8"));
        when(mockJenkinsTaskListener.getLogger()).thenReturn(spyPrintStream);

        // Run the test
        assertThrows(IOException.class, () -> oak9ApiClientUnderTest.pollStatus(result, 0));
        verify(spyPrintStream).close();
    }
}
