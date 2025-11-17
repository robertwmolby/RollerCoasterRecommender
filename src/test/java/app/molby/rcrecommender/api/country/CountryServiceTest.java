package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryEntity;
import app.molby.rcrecommender.domain.country.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CountryService}.
 */
@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService subject;

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------

    @Test
    void create_ShouldSaveAndReturnCountry() {
        CountryEntity input = new CountryEntity();
        CountryEntity saved = new CountryEntity();
        saved.setId(1L);

        when(countryRepository.save(input)).thenReturn(saved);

        CountryEntity result = subject.create(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(countryRepository).save(input);
    }

    // -------------------------------------------------------------------------
    // GET BY ID
    // -------------------------------------------------------------------------

    @Test
    void getById_WhenExists_ShouldReturnEntity() {
        Long id = 10L;
        CountryEntity entity = new CountryEntity();
        entity.setId(id);

        when(countryRepository.findById(id)).thenReturn(Optional.of(entity));

        CountryEntity result = subject.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(countryRepository).findById(id);
    }

    @Test
    void getById_WhenMissing_ShouldThrowCountryNotFoundException() {
        Long id = 99L;

        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        CountryNotFoundException ex =
                assertThrows(CountryNotFoundException.class, () -> subject.getById(id));

        assertTrue(ex.getMessage().contains("Country"));
        assertTrue(ex.getMessage().contains(id.toString()));
        verify(countryRepository).findById(id);
    }

    // -------------------------------------------------------------------------
    // GET ALL
    // -------------------------------------------------------------------------

    @Test
    void getAll_ShouldReturnListOfCountries() {
        CountryEntity c1 = new CountryEntity();
        c1.setId(1L);
        CountryEntity c2 = new CountryEntity();
        c2.setId(2L);

        when(countryRepository.findAll()).thenReturn(List.of(c1, c2));

        List<CountryEntity> result = subject.getAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(countryRepository).findAll();
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @Test
    void update_WhenExists_ShouldSaveUpdatedEntity() {
        Long id = 5L;
        CountryEntity updated = new CountryEntity();

        when(countryRepository.existsById(id)).thenReturn(true);
        when(countryRepository.save(updated)).thenAnswer(invocation -> {
            CountryEntity entity = invocation.getArgument(0);
            entity.setId(id);
            return entity;
        });

        CountryEntity result = subject.update(id, updated);

        assertEquals(id, result.getId());
        verify(countryRepository).existsById(id);
        verify(countryRepository).save(updated);
    }

    @Test
    void update_WhenMissing_ShouldThrowCountryNotFoundException() {
        Long id = 100L;
        CountryEntity updated = new CountryEntity();

        when(countryRepository.existsById(id)).thenReturn(false);

        assertThrows(CountryNotFoundException.class, () -> subject.update(id, updated));

        verify(countryRepository).existsById(id);
        verify(countryRepository, never()).save(any());
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    @Test
    void delete_WhenExists_ShouldDeleteEntity() {
        Long id = 8L;

        when(countryRepository.existsById(id)).thenReturn(true);

        subject.delete(id);

        verify(countryRepository).existsById(id);
        verify(countryRepository).deleteById(id);
    }

    @Test
    void delete_WhenMissing_ShouldThrowCountryNotFoundException() {
        Long id = 999L;

        when(countryRepository.existsById(id)).thenReturn(false);

        assertThrows(CountryNotFoundException.class, () -> subject.delete(id));

        verify(countryRepository).existsById(id);
        verify(countryRepository, never()).deleteById(any());
    }
}
