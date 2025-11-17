package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryAccessNotFoundExceptionTest {

    @Test
    void constructor_shouldBuildMessageWithCountryAccessAndId() {
        Long id = 42L;

        CountryAccessNotFoundException ex = new CountryAccessNotFoundException(id);

        assertTrue(ex instanceof ResourceNotFoundException);
        assertEquals("Country access with id 42 not found", ex.getMessage());
    }

    @Test
    void constructor_shouldHandleNullIdInMessage() {
        CountryAccessNotFoundException ex = new CountryAccessNotFoundException(null);

        // ResourceNotFoundException formats message as: resourceName + " with id " + id + " not found"
        assertEquals("Country access with id null not found", ex.getMessage());
    }
}
