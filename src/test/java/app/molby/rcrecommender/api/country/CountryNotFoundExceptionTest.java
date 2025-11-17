package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CountryNotFoundException}.
 */
class CountryNotFoundExceptionTest {

    @Test
    void constructor_ShouldSetProperMessage() {
        Long id = 42L;

        CountryNotFoundException ex = new CountryNotFoundException(id);

        assertNotNull(ex);
        assertTrue(ex instanceof ResourceNotFoundException);

        // Expected format from ResourceNotFoundException: "Country with id {id} not found"
        String expectedMessage = "Country with id 42 not found";
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    void exception_ShouldRetainIdInMessage() {
        Long id = 999L;

        CountryNotFoundException ex = new CountryNotFoundException(id);

        assertTrue(ex.getMessage().contains("999"));
        assertTrue(ex.getMessage().contains("Country"));
    }
}
