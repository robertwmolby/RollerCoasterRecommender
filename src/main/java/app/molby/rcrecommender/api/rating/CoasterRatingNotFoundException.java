package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;

/**
 * Exception thrown when a requested {@code CoasterRating} cannot be found.
 *
 * <p>This is a specialization of {@link ResourceNotFoundException} used by
 * the roller coaster recommender application whenever a lookup for a rating
 * by its identifier fails.</p>
 * @author Bob Molby
 */
public class CoasterRatingNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new {@code CoasterRatingNotFoundException}.
     *
     * @param id the identifier of the coaster rating that was not found
     */
    public CoasterRatingNotFoundException(Long id) {
        super("Coaster rating", id);
    }
}
