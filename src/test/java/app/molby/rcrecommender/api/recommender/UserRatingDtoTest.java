package app.molby.rcrecommender.api.recommender;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

public class UserRatingDtoTest {
    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(UserRatingDto.class);
    }
}