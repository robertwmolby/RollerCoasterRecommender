package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

public class CoasterRatingDtoTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(CoasterRatingDto.class);
    }
}
