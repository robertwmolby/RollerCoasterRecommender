package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryEntity;
import app.molby.rcrecommender.domain.country.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryEntity create(CountryEntity country) {
        return countryRepository.save(country);
    }

    public CountryEntity getById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException(id));
    }

    public List<CountryEntity> getAll() {
        return countryRepository.findAll();
    }

    public CountryEntity update(Long id, CountryEntity updated) {
        if (!countryRepository.existsById(id)) {
            throw new CountryNotFoundException(id);
        }
        updated.setId(id);
        return countryRepository.save(updated);
    }

    public void delete(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new CountryNotFoundException(id);
        }
        countryRepository.deleteById(id);
    }
}
