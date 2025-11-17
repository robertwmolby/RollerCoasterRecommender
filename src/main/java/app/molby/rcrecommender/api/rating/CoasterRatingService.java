package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import app.molby.rcrecommender.domain.rating.CoasterRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/**
 * Service layer for managing {@link CoasterRatingEntity} instances.
 *
 * <p>Provides basic CRUD operations for user-submitted coaster ratings.
 * Validation and resource-existence checks are handled here before delegating
 * persistence operations to the repository.</p>
 * @author Bob Molby
 */
public class CoasterRatingService {

    private final CoasterRatingRepository coasterRatingRepository;

    /**
     * Creates a new coaster rating.
     *
     * @param rating the rating entity to persist
     * @return the saved rating entity
     */
    public CoasterRatingEntity create(CoasterRatingEntity rating) {
        return coasterRatingRepository.save(rating);
    }

    /**
     * Retrieves a coaster rating by its identifier.
     *
     * @param id the identifier of the rating to fetch
     * @return the found rating entity
     * @throws CoasterRatingNotFoundException if no rating exists with the given id
     */
    public CoasterRatingEntity getById(Long id) {
        return coasterRatingRepository.findById(id)
                .orElseThrow(() -> new CoasterRatingNotFoundException(id));
    }

    /**
     * Retrieves all coaster ratings.
     *
     * @return a list of all rating entities
     */
    public List<CoasterRatingEntity> getAll() {
        return coasterRatingRepository.findAll();
    }

    /**
     * Updates an existing coaster rating.
     *
     * @param id      the identifier of the rating to update
     * @param updated the new rating data to apply
     * @return the updated rating entity
     * @throws CoasterRatingNotFoundException if no rating exists with the given id
     */
    public CoasterRatingEntity update(Long id, CoasterRatingEntity updated) {
        if (!coasterRatingRepository.existsById(id)) {
            throw new CoasterRatingNotFoundException(id);
        }
        updated.setId(id);
        return coasterRatingRepository.save(updated);
    }

    /**
     * Deletes a coaster rating by its identifier.
     *
     * @param id the identifier of the rating to delete
     * @throws CoasterRatingNotFoundException if no rating exists with the given id
     */
    public void delete(Long id) {
        if (!coasterRatingRepository.existsById(id)) {
            throw new CoasterRatingNotFoundException(id);
        }
        coasterRatingRepository.deleteById(id);
    }
}
