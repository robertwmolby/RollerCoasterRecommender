package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoasterRatingNotFoundExceptionTest {

    @Test
    void constructor_shouldBuildMessageWithCoasterRatingAndId() {
        Long id = 123L;

        CoasterRatingNotFoundException ex = new CoasterRatingNotFoundException(id);

        assertTrue(ex instanceof ResourceNotFoundException);
        assertEquals("Coaster rating with id 123 not found", ex.getMessage());
    }

    @Test
    void constructor_shouldHandleNullIdInMessage() {
        CoasterRatingNotFoundException ex = new CoasterRatingNotFoundException(null);

        // ResourceNotFoundException builds message as: resourceName + " with id " + id + " not found"
        assertEquals("Coaster rating with id null not found", ex.getMessage());
    }
}