package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Exception thrown when a country access record with the specified ID
 * cannot be found in the system.
 *
 * <p>This is a domain-specific specialization of
 * {@link ResourceNotFoundException} used by the country access API layer
 * to signal 404 Not Found errors.</p>
 */
@Schema(
        name = "CountryAccessNotFoundException",
        title = "Country Access Not Found Exception",
        description = "Exception raised when a country access record with the given ID cannot be found."
)
public class CountryAccessNotFoundException extends ResourceNotFoundException {

    /**
     * Creates a new {@code CountryAccessNotFoundException} for the given country access ID.
     *
     * @param id the unique identifier of the country access record that could not be found
     */
    public CountryAccessNotFoundException(Long id) {
        super("Country access", id);
    }
}
