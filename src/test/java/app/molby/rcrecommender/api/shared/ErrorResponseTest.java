package app.molby.rcrecommender.api.shared;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ErrorResponse}.
 */
class ErrorResponseTest {

    @Test
    void allArgsConstructor_ShouldSetAllFields() {
        Instant timestamp = Instant.now();
        int status = 404;
        String error = "Not Found";
        String message = "Coaster not found";
        String path = "/api/ratings/123";

        ErrorResponse response = new ErrorResponse(timestamp, status, error, message, path);

        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void setters_ShouldModifyFields() {
        ErrorResponse response = new ErrorResponse(
                Instant.now(), 400, "Bad Request", "Original message", "/test"
        );

        Instant newTimestamp = Instant.now();
        response.setTimestamp(newTimestamp);
        response.setStatus(500);
        response.setError("Server Error");
        response.setMessage("Failure occurred");
        response.setPath("/api/failure");

        assertEquals(newTimestamp, response.getTimestamp());
        assertEquals(500, response.getStatus());
        assertEquals("Server Error", response.getError());
        assertEquals("Failure occurred", response.getMessage());
        assertEquals("/api/failure", response.getPath());
    }

    @Test
    void equalsAndHashCode_ShouldMatchForSameValues() {
        Instant timestamp = Instant.now();

        ErrorResponse r1 = new ErrorResponse(timestamp, 500, "Error", "Failure", "/path");
        ErrorResponse r2 = new ErrorResponse(timestamp, 500, "Error", "Failure", "/path");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void toString_ShouldContainFieldValues() {
        Instant timestamp = Instant.parse("2025-11-16T18:24:37.123Z");
        ErrorResponse response = new ErrorResponse(timestamp, 404, "Not Found", "Missing", "/x");

        String text = response.toString();

        assertTrue(text.contains("404"));
        assertTrue(text.contains("Not Found"));
        assertTrue(text.contains("Missing"));
        assertTrue(text.contains("/x"));
        assertTrue(text.contains("2025-11-16T18:24:37.123Z"));
    }
}
