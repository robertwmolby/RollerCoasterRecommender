package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void constructor_shouldBuildMessageWithUserAndId() {
        String id = "jean_luc_picard";

        UserNotFoundException ex = new UserNotFoundException(id);

        assertNotNull(ex);
        assertTrue(ex instanceof ResourceNotFoundException);
        assertEquals("User with id jean_luc_picard not found", ex.getMessage());
    }

    @Test
    void constructor_shouldHandleNullIdInMessage() {
        UserNotFoundException ex = new UserNotFoundException(null);

        // ResourceNotFoundException formats message as: resourceName + " with id " + id + " not found"
        assertEquals("User with id null not found", ex.getMessage());
    }

    @Test
    void message_shouldContainProvidedId() {
        String id = "abc-999";

        UserNotFoundException ex = new UserNotFoundException(id);

        assertTrue(ex.getMessage().contains("User"));
        assertTrue(ex.getMessage().contains("abc-999"));
    }
}