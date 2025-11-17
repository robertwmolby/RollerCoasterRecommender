package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Exception thrown when a roller coaster with the specified ID
 * cannot be found in the system.
 *
 * <p>This is a domain-specific specialization of
 * {@link ResourceNotFoundException} used by the roller coaster
 * API layer to signal 404 Not Found errors.</p>
 */
@Schema(
        name = "RollerCoasterNotFoundException",
        title = "Roller Coaster Not Found Exception",
        description = "Exception raised when a roller coaster with the given ID cannot be found."
)
public class RollerCoasterNotFoundException extends ResourceNotFoundException {

    /**
     * Creates a new {@code RollerCoasterNotFoundException} for the given roller coaster ID.
     *
     * @param id the unique identifier of the roller coaster that could not be found
     */
    public RollerCoasterNotFoundException(Long id) {
        super("Roller coaster", id);
    }
}
