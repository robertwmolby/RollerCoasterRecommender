package app.molby.rcrecommender.api.recommender;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RecommendedCoasterDto}.
 */
class RecommendedCoasterDtoTest {
    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(RecommendedCoasterDto.class);
    }
}
