package app.molby.rcrecommender.domain.rating;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CoasterRatingRepository Spring Data repository interface for persistence operations.
 * @author Bob Molby
 */
public interface CoasterRatingRepository extends JpaRepository<CoasterRatingEntity, Long> {
}
