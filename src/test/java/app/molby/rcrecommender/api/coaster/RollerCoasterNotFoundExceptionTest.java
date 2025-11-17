package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RollerCoasterNotFoundExceptionTest {

    @Test
    void constructor_shouldBuildMessageWithCoasterAndId() {
        Long id = 123L;

        RollerCoasterNotFoundException ex = new RollerCoasterNotFoundException(id);

        assertTrue(ex instanceof ResourceNotFoundException);
        assertEquals("Roller coaster with id 123 not found", ex.getMessage());
    }

    @Test
    void constructor_shouldHandleNullIdInMessage() {
        RollerCoasterNotFoundException ex = new RollerCoasterNotFoundException(null);

        // ResourceNotFoundException builds message as: resourceName + " with id " + id + " not found"
        assertEquals("Roller coaster with id null not found", ex.getMessage());
    }
}
