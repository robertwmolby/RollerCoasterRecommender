package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
import app.molby.rcrecommender.domain.coaster.RollerCoasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing {@link RollerCoasterEntity} objects.
 * Provides CRUD operations used by controllers in the roller coaster recommender application.
 */
@Service
@RequiredArgsConstructor
public class RollerCoasterService {

    private final RollerCoasterRepository rollerCoasterRepository;

    /**
     * Create a new roller coaster record.
     *
     * @param coaster the coaster entity to persist
     * @return the saved {@link RollerCoasterEntity} with an assigned ID
     */
    public RollerCoasterEntity create(RollerCoasterEntity coaster) {
        return rollerCoasterRepository.save(coaster);
    }

    /**
     * Retrieve a roller coaster by its unique ID.
     *
     * @param id the primary key of the coaster
     * @return the matching {@link RollerCoasterEntity}
     * @throws RollerCoasterNotFoundException if the coaster does not exist
     */
    public RollerCoasterEntity findById(Long id) {
        return rollerCoasterRepository.findById(id)
                .orElseThrow(() -> new RollerCoasterNotFoundException(id));
    }

    /**
     * Retrieve all roller coaster records.
     *
     * @return a list of all {@link RollerCoasterEntity} objects
     */
    public List<RollerCoasterEntity> findAll() {
        return rollerCoasterRepository.findAll();
    }

    /**
     * Delete an existing roller coaster record.
     *
     * @param id the ID of the coaster to delete
     * @throws RollerCoasterNotFoundException if the coaster does not exist
     */
    public void delete(Long id) {
        if (!rollerCoasterRepository.existsById(id)) {
            throw new RollerCoasterNotFoundException(id);
        }
        rollerCoasterRepository.deleteById(id);
    }
}
