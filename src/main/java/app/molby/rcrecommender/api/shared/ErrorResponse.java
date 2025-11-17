package app.molby.rcrecommender.api.shared;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * Standard API error response structure.
 *
 * <p>Returned by the application's global exception handler whenever an
 * error occurs. Provides consistent error details to API consumers.</p>
 * @author Bob Molby
 */
@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        title = "Error Response",
        description = "Standard error payload returned when an API request fails."
)
public class ErrorResponse {

    /**
     * Timestamp indicating when the error occurred, represented in UTC.
     */
    @Schema(
            description = "Timestamp of when the error occurred (UTC).",
            example = "2025-11-16T18:24:37.123Z"
    )
    private Instant timestamp;

    /**
     * HTTP status code of the error (e.g., 400, 404, 500).
     */
    @Schema(
            description = "HTTP status code associated with the error.",
            example = "404"
    )
    private int status;

    /**
     * Short error name or reason phrase.
     */
    @Schema(
            description = "Short error label describing the error type.",
            example = "Not Found"
    )
    private String error;

    /**
     * Detailed message describing the cause of the error.
     */
    @Schema(
            description = "Descriptive message explaining what went wrong.",
            example = "Coaster rating with ID 123 was not found."
    )
    private String message;

    /**
     * Request path where the error originated.
     */
    @Schema(
            description = "The request path that triggered the error.",
            example = "/api/ratings/123"
    )
    private String path;
}
