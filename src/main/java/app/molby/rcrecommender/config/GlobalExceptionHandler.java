package app.molby.rcrecommender.config;

import app.molby.rcrecommender.api.shared.ErrorResponse;
import app.molby.rcrecommender.api.shared.ResourceNotFoundException;
import app.molby.rcrecommender.api.shared.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.time.Instant;
import java.util.List;


@RestControllerAdvice
/**
 * Global exception handler for the roller coaster recommender application.
 *
 * <p>Intercepts exceptions thrown by controllers and converts them into
 * standardized error response structures. Ensures consistent formatting of
 * error messages returned to API clients.</p>
 * @author Bob Molby
 */
public class GlobalExceptionHandler {

    public static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles cases where a requested resource cannot be found.
     *
     * <p>Triggered when a {@link ResourceNotFoundException} is thrown, returning
     * a 404 response with a structured {@link ErrorResponse}.</p>
     *
     * @param ex the thrown exception containing context about the missing resource
     * @param request the HTTP request that resulted in the error
     * @return standardized error response containing details of the 404 error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex,
                                        HttpServletRequest request) {
        return new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    /**
     * Handles validation failures on incoming request bodies.
     *
     * <p>Triggered when request DTO validation fails and a
     * {@link MethodArgumentNotValidException} is thrown. Extracts all
     * field-level validation messages and includes them in a
     * {@link ValidationErrorResponse}.</p>
     *
     * @param ex the exception containing validation error details
     * @param request the HTTP request that failed validation
     * @return structured response listing all validation failures
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidation(MethodArgumentNotValidException ex,
                                                    HttpServletRequest request) {

        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return new ValidationErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                messages,
                request.getRequestURI()
        );
    }

    /**
     * Handles 404 (not found exceptions) by just passing it along.
     * @param ex the exception that was thrown
     * @param request the HTTP request to an incorrect page
     * @return standardized 404 error response object
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(Exception ex, HttpServletRequest request) {
        LOGGER.info("Request was made to an invalid page {}.", request.getRequestURI());
        return new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Page Not Found",
                "You have requested a page that does not exist.",
                request.getRequestURI()
        );
    }

    /**
     * Handles all uncaught exceptions that are not covered by more specific handlers.
     *
     * <p>Acts as a fallback to prevent uncontrolled stack traces from leaking to
     * the client. Returns a generic 500 Internal Server Error with a structured
     * {@link ErrorResponse} containing basic diagnostic information.</p>
     *
     * @param ex the unexpected exception that was thrown
     * @param request the HTTP request being processed when the error occurred
     * @return standardized 500 error response object
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex, HttpServletRequest request) {
        LOGGER.error("Unhandled exception: {}", ex.getMessage());
        return new ErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
