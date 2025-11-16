package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import app.molby.rcrecommender.domain.rating.CoasterRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoasterRatingService {

    private final CoasterRatingRepository coasterRatingRepository;

    public CoasterRatingEntity create(CoasterRatingEntity rating) {
        return coasterRatingRepository.save(rating);
    }

    public CoasterRatingEntity getById(Long id) {
        return coasterRatingRepository.findById(id)
                .orElseThrow(() -> new CoasterRatingNotFoundException(id));
    }

    public List<CoasterRatingEntity> getAll() {
        return coasterRatingRepository.findAll();
    }

    public CoasterRatingEntity update(Long id, CoasterRatingEntity updated) {
        if (!coasterRatingRepository.existsById(id)) {
            throw new CoasterRatingNotFoundException(id);
        }
        updated.setId(id);
        return coasterRatingRepository.save(updated);
    }

    public void delete(Long id) {
        if (!coasterRatingRepository.existsById(id)) {
            throw new CoasterRatingNotFoundException(id);
        }
        coasterRatingRepository.deleteById(id);
    }
}
