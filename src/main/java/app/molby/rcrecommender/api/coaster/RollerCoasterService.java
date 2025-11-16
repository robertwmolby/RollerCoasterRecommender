package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
import app.molby.rcrecommender.domain.coaster.RollerCoasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RollerCoasterService {

    private final RollerCoasterRepository rollerCoasterRepository;

    public RollerCoasterEntity create(RollerCoasterEntity coaster) {
        return rollerCoasterRepository.save(coaster);
    }

    public RollerCoasterEntity getById(Long id) {
        return rollerCoasterRepository.findById(id)
                .orElseThrow(() -> new RollerCoasterNotFoundException(id));
    }

    public List<RollerCoasterEntity> getAll() {
        return rollerCoasterRepository.findAll();
    }

    public RollerCoasterEntity update(Long id, RollerCoasterEntity updated) {
        if (!rollerCoasterRepository.existsById(id)) {
            throw new RollerCoasterNotFoundException(id);
        }
        updated.setId(id);
        return rollerCoasterRepository.save(updated);
    }

    public void delete(Long id) {
        if (!rollerCoasterRepository.existsById(id)) {
            throw new RollerCoasterNotFoundException(id);
        }
        rollerCoasterRepository.deleteById(id);
    }
}
