package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;

public class CoasterRatingNotFoundException extends ResourceNotFoundException {
    public CoasterRatingNotFoundException(Long id) {
        super("Coaster rating",id);
    }
}
