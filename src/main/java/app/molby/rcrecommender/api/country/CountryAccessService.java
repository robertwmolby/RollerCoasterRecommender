package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryAccessEntity;
import app.molby.rcrecommender.domain.country.CountryAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryAccessService {

    private final CountryAccessRepository countryAccessRepository;

    public CountryAccessEntity create(CountryAccessEntity mapping) {
        return countryAccessRepository.save(mapping);
    }

    public CountryAccessEntity getById(Long id) {
        return countryAccessRepository.findById(id)
                .orElseThrow(() -> new CountryAccessNotFoundException(id));
    }

    public List<CountryAccessEntity> getAll() {
        return countryAccessRepository.findAll();
    }

    public CountryAccessEntity update(Long id, CountryAccessEntity updated) {
        if (!countryAccessRepository.existsById(id)) {
            throw new CountryAccessNotFoundException(id);
        }
        updated.setId(id);
        return countryAccessRepository.save(updated);
    }

    public void delete(Long id) {
        if (!countryAccessRepository.existsById(id)) {
            throw new CountryAccessNotFoundException(id);
        }
        countryAccessRepository.deleteById(id);
    }
}
