package app.molby.rcrecommender.api.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void constructor_buildsMessageWithResourceNameAndId() {
        ResourceNotFoundException ex =
                new ResourceNotFoundException("User", 42L);

        assertTrue(ex instanceof RuntimeException);
        assertEquals("User with id 42 not found", ex.getMessage());
    }

    @Test
    void constructor_handlesNullIdInMessage() {
        ResourceNotFoundException ex =
                new ResourceNotFoundException("User", null);

        assertEquals("User with id null not found", ex.getMessage());
    }

    @Test
    void constructor_acceptsNonLongIdTypes() {
        ResourceNotFoundException ex =
                new ResourceNotFoundException("Coaster rating", "ABC-123");

        assertEquals("Coaster rating with id ABC-123 not found", ex.getMessage());
    }
}