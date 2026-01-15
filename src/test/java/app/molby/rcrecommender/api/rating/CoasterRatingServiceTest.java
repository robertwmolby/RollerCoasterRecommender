package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import app.molby.rcrecommender.domain.rating.CoasterRatingRepository;
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
 * Unit tests for {@link CoasterRatingService}.
 */
@ExtendWith(MockitoExtension.class)
class CoasterRatingServiceTest {

    @Mock
    private CoasterRatingRepository coasterRatingRepository;

    @InjectMocks
    private CoasterRatingService subject;

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------

    @Test
    void create_ShouldSaveAndReturnEntity() {
        CoasterRatingEntity input = new CoasterRatingEntity();
        CoasterRatingEntity saved = new CoasterRatingEntity();
        saved.setId(1L);

        when(coasterRatingRepository.save(input)).thenReturn(saved);

        CoasterRatingEntity result = subject.create(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(coasterRatingRepository).save(input);
    }

    // -------------------------------------------------------------------------
    // GET BY ID
    // -------------------------------------------------------------------------

    @Test
    void getById_WhenExists_ShouldReturnEntity() {
        Long id = 10L;
        CoasterRatingEntity entity = new CoasterRatingEntity();
        entity.setId(id);

        when(coasterRatingRepository.findById(id)).thenReturn(Optional.of(entity));

        CoasterRatingEntity result = subject.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(coasterRatingRepository).findById(id);
    }

    @Test
    void getById_WhenMissing_ShouldThrowCoasterRatingNotFoundException() {
        Long id = 99L;

        when(coasterRatingRepository.findById(id)).thenReturn(Optional.empty());

        CoasterRatingNotFoundException ex =
                assertThrows(CoasterRatingNotFoundException.class, () -> subject.findById(id));

        assertTrue(ex.getMessage().contains("Coaster rating"));
        assertTrue(ex.getMessage().contains(id.toString()));
        verify(coasterRatingRepository).findById(id);
    }

    // -------------------------------------------------------------------------
    // GET ALL
    // -------------------------------------------------------------------------

    @Test
    void getAll_ShouldReturnListOfRatings() {
        CoasterRatingEntity r1 = new CoasterRatingEntity();
        r1.setId(1L);
        CoasterRatingEntity r2 = new CoasterRatingEntity();
        r2.setId(2L);

        // TODO FIX THIS TEST
//        when(coasterRatingRepository.findAll()).thenReturn(List.of(r1, r2));

//        List<CoasterRatingEntity> result = subject.findAll(any());
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(1L, result.get(0).getId());
//        assertEquals(2L, result.get(1).getId());
//        verify(coasterRatingRepository).findAll();
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @Test
    void update_WhenExists_ShouldSaveAndReturnUpdatedEntity() {
        Long id = 5L;
        CoasterRatingEntity updated = new CoasterRatingEntity();

        when(coasterRatingRepository.existsById(id)).thenReturn(true);
        when(coasterRatingRepository.save(updated)).thenAnswer(invocation -> {
            CoasterRatingEntity arg = invocation.getArgument(0);
            arg.setId(id);
            return arg;
        });

        CoasterRatingEntity result = subject.update(id, updated);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(coasterRatingRepository).existsById(id);
        verify(coasterRatingRepository).save(updated);
    }

    @Test
    void update_WhenMissing_ShouldThrowCoasterRatingNotFoundException() {
        Long id = 100L;
        CoasterRatingEntity updated = new CoasterRatingEntity();

        when(coasterRatingRepository.existsById(id)).thenReturn(false);

        CoasterRatingNotFoundException ex =
                assertThrows(CoasterRatingNotFoundException.class, () -> subject.update(id, updated));

        assertTrue(ex.getMessage().contains(id.toString()));
        verify(coasterRatingRepository).existsById(id);
        verify(coasterRatingRepository, never()).save(any());
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    @Test
    void delete_WhenExists_ShouldDeleteEntity() {
        Long id = 8L;

        when(coasterRatingRepository.existsById(id)).thenReturn(true);

        subject.delete(id);

        verify(coasterRatingRepository).existsById(id);
        verify(coasterRatingRepository).deleteById(id);
    }

    @Test
    void delete_WhenMissing_ShouldThrowCoasterRatingNotFoundException() {
        Long id = 999L;

        when(coasterRatingRepository.existsById(id)).thenReturn(false);

        CoasterRatingNotFoundException ex =
                assertThrows(CoasterRatingNotFoundException.class, () -> subject.delete(id));

        assertTrue(ex.getMessage().contains(id.toString()));
        verify(coasterRatingRepository).existsById(id);
        verify(coasterRatingRepository, never()).deleteById(any());
    }
}
