package app.molby.rcrecommender.api.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}

@Data
@AllArgsConstructor
class ValidationErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private List<String> messages;
    private String path;
}
