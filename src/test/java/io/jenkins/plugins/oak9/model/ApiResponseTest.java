package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ApiResponseTest {
    @Test
    public void testConstructor() {
        ApiResponse actualApiResponse = new ApiResponse();
        actualApiResponse.setErrorMessage("Error Message");
        actualApiResponse.setRequestId("42");
        ValidationResult validationResult = new ValidationResult();
        actualApiResponse.setResult(validationResult);
        actualApiResponse.setResultsUrl("Results Url");
        actualApiResponse.setStatus("Status");
        actualApiResponse.setSummary("Summary");
        assertEquals("42", actualApiResponse.getRequestId());
        assertSame(validationResult, actualApiResponse.getResult());
        assertEquals("Status", actualApiResponse.getStatus());
    }

    @Test
    public void testConstructor2() {
        ApiResponse actualApiResponse = new ApiResponse("42", "Status", "Summary", "Error Message", new ValidationResult(),
                "Results Url");
        actualApiResponse.setErrorMessage("Error Message");
        actualApiResponse.setRequestId("42");
        ValidationResult validationResult = new ValidationResult();
        actualApiResponse.setResult(validationResult);
        actualApiResponse.setResultsUrl("Results Url");
        actualApiResponse.setStatus("Status");
        actualApiResponse.setSummary("Summary");
        assertEquals("42", actualApiResponse.getRequestId());
        assertSame(validationResult, actualApiResponse.getResult());
        assertEquals("Status", actualApiResponse.getStatus());
    }

    @Test
    public void testSetAdditionalProperty() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setAdditionalProperty("Name", "Value");
        assertEquals(1, apiResponse.getAdditionalProperties().size());
    }
}

