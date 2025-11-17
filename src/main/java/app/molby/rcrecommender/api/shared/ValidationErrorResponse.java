package app.molby.rcrecommender.api.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;
import java.util.List;

/**
 * Standard API response returned when request validation fails.
 *
 * <p>Produced by the global exception handler when a
 * {@code MethodArgumentNotValidException} occurs. Contains the timestamp,
 * HTTP status, a short error label, a list of validation failure messages,
 * and the request path that triggered the error.</p>
 * @author Bob Molby
 */
@Data
@AllArgsConstructor
public class ValidationErrorResponse {

    /**
     * Timestamp indicating when the validation error occurred (UTC).
     */
    private Instant timestamp;

    /**
     * HTTP status code associated with the validation error
     * (typically {@code 400 Bad Request}).
     */
    private int status;

    /**
     * Short error label describing the validation failure.
     *
     * <p>Typically set to a string such as "Validation Failed".</p>
     */
    private String error;

    /**
     * List of validation failure messages, each detailing a specific
     * field-level error (e.g., "rating: must not be null").
     */
    private List<String> messages;

    /**
     * The request path where validation failed.
     */
    private String path;
}
