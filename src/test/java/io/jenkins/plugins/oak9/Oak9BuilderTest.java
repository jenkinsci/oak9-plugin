package io.jenkins.plugins.oak9;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import io.jenkins.plugins.oak9.utils.FileArchiver;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class Oak9BuilderTest {

    private Oak9Builder oak9BuilderUnderTest;

    @BeforeEach
    void setUp() {
        oak9BuilderUnderTest = new Oak9Builder("acorncorp", "proj-acorncorp-15", "oak9-api-key", 2, "1234");
    }

    @Test
    void testPerform() throws Exception {
        // Setup
        final Run<?, ?> run = mock(Run.class);
        final FilePath workspace = new FilePath(new File("filename.txt"));
        final EnvVars env = new EnvVars(new HashMap<>());
        final Launcher launcher = null;
        final TaskListener mockTaskListener = mock(TaskListener.class);

        // Run the test
        oak9BuilderUnderTest.perform(run, workspace, env, launcher, mockTaskListener);

        // Verify the results
        assertEquals(run.getResult(), Result.SUCCESS);
    }

    @Test
    void testPerform_ThrowsIOException() {
        // Setup
        final Run<?, ?> run = mock(Run.class);
        final FilePath workspace = new FilePath(new File("filename.txt"));
        final EnvVars env = new EnvVars(new HashMap<>());
        final Launcher launcher = null;
        final TaskListener mockTaskListener = mock(TaskListener.class);

        // Run the test
        assertThrows(IOException.class, () -> oak9BuilderUnderTest.perform(run, workspace, env, launcher, mockTaskListener));
    }
}
