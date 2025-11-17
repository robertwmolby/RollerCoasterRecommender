package app.molby.rcrecommender.api.shared;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorResponseTest {

    @Test
    void allArgsConstructor_setsAllFields() {
        Instant now = Instant.now();
        int status = 400;
        String error = "Validation Failed";
        List<String> messages = List.of("field1: must not be null", "field2: size must be between 1 and 10");
        String path = "/api/test";

        ValidationErrorResponse response =
                new ValidationErrorResponse(now, status, error, messages, path);

        assertEquals(now, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(messages, response.getMessages());
        assertEquals(path, response.getPath());
    }

    @Test
    void settersAndGetters_workAsExpected() {
        ValidationErrorResponse response = new ValidationErrorResponse(
                null, 0, null, null, null
        );

        Instant timestamp = Instant.parse("2025-01-01T00:00:00Z");
        int status = 422;
        String error = "Invalid Input";
        List<String> messages = List.of("rating: must be >= 0.5");
        String path = "/api/ratings";

        response.setTimestamp(timestamp);
        response.setStatus(status);
        response.setError(error);
        response.setMessages(messages);
        response.setPath(path);

        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(messages, response.getMessages());
        assertEquals(path, response.getPath());
    }

    @Test
    void equalsAndHashCode_basedOnAllFields() {
        Instant ts = Instant.parse("2025-01-01T10:00:00Z");
        List<String> messages = List.of("field: error");

        ValidationErrorResponse r1 =
                new ValidationErrorResponse(ts, 400, "Validation Failed", messages, "/path");
        ValidationErrorResponse r2 =
                new ValidationErrorResponse(ts, 400, "Validation Failed", messages, "/path");
        ValidationErrorResponse r3 =
                new ValidationErrorResponse(ts, 400, "Validation Failed", messages, "/other");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        assertNotEquals(r1, r3);
        assertNotEquals(r1.hashCode(), r3.hashCode());
    }

    @Test
    void toString_containsKeyFieldValues() {
        Instant ts = Instant.parse("2025-01-01T10:00:00Z");
        List<String> messages = List.of("field: error");

        ValidationErrorResponse response =
                new ValidationErrorResponse(ts, 400, "Validation Failed", messages, "/path");

        String s = response.toString();

        assertTrue(s.contains("Validation Failed"));
        assertTrue(s.contains("400"));
        assertTrue(s.contains("/path"));
        assertTrue(s.contains("field: error"));
    }
}