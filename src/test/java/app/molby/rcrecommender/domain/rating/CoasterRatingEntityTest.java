package app.molby.rcrecommender.domain.rating;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

class CoasterRatingEntityTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(CoasterRatingEntity.class);
    }
}
