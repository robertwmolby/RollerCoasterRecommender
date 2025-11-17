package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
import app.molby.rcrecommender.domain.coaster.RollerCoasterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RollerCoasterService}.
 */
@ExtendWith(MockitoExtension.class)
class RollerCoasterServiceTest {

    @Mock
    private RollerCoasterRepository rollerCoasterRepository;

    @InjectMocks
    private RollerCoasterService service;

    @Test
    void create_shouldDelegateToRepositorySave_andReturnSavedEntity() {
        RollerCoasterEntity toSave = buildCoaster(null, "Millennium Force");
        RollerCoasterEntity saved = buildCoaster(101L, "Millennium Force");

        given(rollerCoasterRepository.save(toSave)).willReturn(saved);

        RollerCoasterEntity result = service.create(toSave);

        assertThat(result).isEqualTo(saved);
        verify(rollerCoasterRepository).save(toSave);
        verifyNoMoreInteractions(rollerCoasterRepository);
    }

    @Test
    void findById_shouldReturnEntity_whenFound() {
        Long id = 101L;
        RollerCoasterEntity entity = buildCoaster(id, "Millennium Force");
        given(rollerCoasterRepository.findById(id)).willReturn(Optional.of(entity));

        RollerCoasterEntity result = service.findById(id);

        assertThat(result).isEqualTo(entity);
        verify(rollerCoasterRepository).findById(id);
        verifyNoMoreInteractions(rollerCoasterRepository);
    }

    @Test
    void findById_shouldThrowRollerCoasterNotFoundException_whenNotFound() {
        Long id = 999L;
        given(rollerCoasterRepository.findById(id)).willReturn(Optional.empty());

        RollerCoasterNotFoundException ex =
                assertThrows(RollerCoasterNotFoundException.class, () -> service.findById(id));

        assertThat(ex.getMessage()).contains("Roller coaster", String.valueOf(id));
        verify(rollerCoasterRepository).findById(id);
        verifyNoMoreInteractions(rollerCoasterRepository);
    }

    @Test
    void findAll_shouldReturnAllEntitiesFromRepository() {
        RollerCoasterEntity c1 = buildCoaster(101L, "Millennium Force");
        RollerCoasterEntity c2 = buildCoaster(102L, "GateKeeper");

        given(rollerCoasterRepository.findAll()).willReturn(List.of(c1, c2));

        List<RollerCoasterEntity> result = service.findAll();

        assertThat(result).containsExactly(c1, c2);
        verify(rollerCoasterRepository).findAll();
        verifyNoMoreInteractions(rollerCoasterRepository);
    }

    @Test
    void delete_shouldCallDeleteById_whenCoasterExists() {
        Long id = 101L;
        given(rollerCoasterRepository.existsById(id)).willReturn(true);

        service.delete(id);

        verify(rollerCoasterRepository).existsById(id);
        verify(rollerCoasterRepository).deleteById(id);
        verifyNoMoreInteractions(rollerCoasterRepository);
    }

    @Test
    void delete_shouldThrowRollerCoasterNotFoundException_whenCoasterDoesNotExist() {
        Long id = 999L;
        given(rollerCoasterRepository.existsById(id)).willReturn(false);

        RollerCoasterNotFoundException ex =
                assertThrows(RollerCoasterNotFoundException.class, () -> service.delete(id));

        assertThat(ex.getMessage()).contains("Roller coaster", String.valueOf(id));

        verify(rollerCoasterRepository).existsById(id);
        verifyNoMoreInteractions(rollerCoasterRepository);
    }

    // --- helper --------------------------------------------------------------

    private RollerCoasterEntity buildCoaster(Long id, String name) {
        RollerCoasterEntity entity = new RollerCoasterEntity();
        entity.setId(id);
        entity.setName(name);
        return entity;
    }
}
