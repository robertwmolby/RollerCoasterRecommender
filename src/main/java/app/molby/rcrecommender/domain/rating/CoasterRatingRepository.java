package app.molby.rcrecommender.domain.rating;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoasterRatingRepository extends JpaRepository<CoasterRatingEntity, Long> {
}
