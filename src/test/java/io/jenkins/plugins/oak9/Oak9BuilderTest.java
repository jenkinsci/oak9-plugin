package io.jenkins.plugins.oak9;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class Oak9BuilderTest {

    private Oak9Builder oak9BuilderUnderTest;

    @BeforeEach
    void setUp() {
        oak9BuilderUnderTest = new Oak9Builder("orgId", "projectId", "oak9-api-key", 2);
    }

    @Test
    void testCreateZipFile() {
        // Setup
        final FilePath workspace = new FilePath(new File("filename.txt"));
        final FilePath expectedResult = new FilePath(new File("filename.txt"));

        // Run the test
        final FilePath result = oak9BuilderUnderTest.createZipFile(workspace);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testPerform() throws Exception {
        // Setup
        final Run<?, ?> run = Run.fromExternalizableId("id");
        final FilePath workspace = new FilePath(new File("filename.txt"));
        final EnvVars env = new EnvVars(new HashMap<>());
        final Launcher launcher = null;
        final TaskListener mockTaskListener = mock(TaskListener.class);

        // Run the test
        oak9BuilderUnderTest.perform(run, workspace, env, launcher, mockTaskListener);

        // Verify the results
    }

    @Test
    void testPerform_ThrowsInterruptedException() {
        // Setup
        final Run<?, ?> run = Run.fromExternalizableId("id");
        final FilePath workspace = new FilePath(new File("filename.txt"));
        final EnvVars env = new EnvVars(new HashMap<>());
        final Launcher launcher = null;
        final TaskListener mockTaskListener = mock(TaskListener.class);

        // Run the test
        assertThrows(InterruptedException.class, () -> oak9BuilderUnderTest.perform(run, workspace, env, launcher, mockTaskListener));
    }

    @Test
    void testPerform_ThrowsIOException() {
        // Setup
        final Run<?, ?> run = Run.fromExternalizableId("id");
        final FilePath workspace = new FilePath(new File("filename.txt"));
        final EnvVars env = new EnvVars(new HashMap<>());
        final Launcher launcher = null;
        final TaskListener mockTaskListener = mock(TaskListener.class);

        // Run the test
        assertThrows(IOException.class, () -> oak9BuilderUnderTest.perform(run, workspace, env, launcher, mockTaskListener));
    }

    @Test
    void testGetCredentials() {
        // Setup
        final Run<?, ?> run = Run.fromExternalizableId("id");

        // Run the test
        final StringCredentials result = Oak9Builder.getCredentials(run, "oak9-api-key");

        // Verify the results
    }
}
