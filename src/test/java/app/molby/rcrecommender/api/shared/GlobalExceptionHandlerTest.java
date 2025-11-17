package app.molby.rcrecommender.api.shared;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    // ---------------------------------------------------------
    // 404 - ResourceNotFoundException
    // ---------------------------------------------------------
    @Test
    void handleNotFound_ShouldReturnErrorResponseWith404() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        when(request.getRequestURI()).thenReturn("/api/test/123");

        ResourceNotFoundException ex =
                new ResourceNotFoundException("TestResource", 123);

        ErrorResponse response = handler.handleNotFound(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Not Found", response.getError());
        assertEquals(ex.getMessage(), response.getMessage());
        assertEquals("/api/test/123", response.getPath());
        assertNotNull(response.getTimestamp());
    }

    // ---------------------------------------------------------
    // 400 - MethodArgumentNotValidException
    // ---------------------------------------------------------
    @Test
    void handleValidation_ShouldReturnValidationErrorResponse() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        when(request.getRequestURI()).thenReturn("/api/validate");

        // Mock binding result with two field errors
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        FieldError err1 = new FieldError("dto", "rating", "must be between 1 and 5");
        FieldError err2 = new FieldError("dto", "coasterId", "must not be null");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(err1, err2));

        ValidationErrorResponse response = handler.handleValidation(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Validation Failed", response.getError());
        assertEquals("/api/validate", response.getPath());
        assertEquals(2, response.getMessages().size());
        assertTrue(response.getMessages().contains("rating: must be between 1 and 5"));
        assertTrue(response.getMessages().contains("coasterId: must not be null"));
        assertNotNull(response.getTimestamp());
    }

    // ---------------------------------------------------------
    // 500 - Generic Exception
    // ---------------------------------------------------------
    @Test
    void handleGeneric_ShouldReturnErrorResponseWith500() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        when(request.getRequestURI()).thenReturn("/api/break");

        Exception ex = new RuntimeException("Something exploded");

        ErrorResponse response = handler.handleGeneric(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("Internal Server Error", response.getError());
        assertEquals("Something exploded", response.getMessage());
        assertEquals("/api/break", response.getPath());
        assertNotNull(response.getTimestamp());
    }
}
