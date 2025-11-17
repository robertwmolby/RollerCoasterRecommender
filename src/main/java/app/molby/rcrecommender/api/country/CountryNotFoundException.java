package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;

/**
 * Exception thrown when a requested {@code CountryEntity} cannot be found.
 *
 * <p>This exception is used by service and controller layers whenever a country
 * lookup by ID fails. It extends {@link ResourceNotFoundException}, providing
 * a standardized error structure for missing resources throughout the
 * roller coaster recommender application.</p>
 */
public class CountryNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new {@code CountryNotFoundException} for the given country ID.
     *
     * @param id the identifier of the country that could not be found
     */
    public CountryNotFoundException(Long id) {
        super("Country", id);
    }
}
