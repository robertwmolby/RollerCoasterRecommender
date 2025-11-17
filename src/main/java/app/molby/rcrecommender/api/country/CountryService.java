package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryEntity;
import app.molby.rcrecommender.domain.country.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/**
 * Service layer providing CRUD operations for {@link CountryEntity} objects.
 *
 * <p>This service acts as the business-layer abstraction for country management
 * within the roller coaster recommender application, handling persistence and
 * existence checks while delegating data operations to the underlying repository.</p>
 */
public class CountryService {

    private final CountryRepository countryRepository;

    /**
     * Create a new country record.
     *
     * @param country the {@link CountryEntity} to persist
     * @return the saved entity with an assigned ID
     */
    public CountryEntity create(CountryEntity country) {
        return countryRepository.save(country);
    }

    /**
     * Retrieve a country by its unique ID.
     *
     * @param id the primary key of the country to retrieve
     * @return the matching {@link CountryEntity}
     * @throws CountryNotFoundException if the country does not exist
     */
    public CountryEntity getById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException(id));
    }

    /**
     * Retrieve all countries.
     *
     * @return a list of all {@link CountryEntity} records
     */
    public List<CountryEntity> getAll() {
        return countryRepository.findAll();
    }

    /**
     * Update an existing country record with new data.
     *
     * @param id      the ID of the country to update
     * @param updated the updated state of the country
     * @return the saved, updated {@link CountryEntity}
     * @throws CountryNotFoundException if no record exists for the given ID
     */
    public CountryEntity update(Long id, CountryEntity updated) {
        if (!countryRepository.existsById(id)) {
            throw new CountryNotFoundException(id);
        }
        updated.setId(id);
        return countryRepository.save(updated);
    }

    /**
     * Delete a country by its unique ID.
     *
     * @param id the ID of the country to delete
     * @throws CountryNotFoundException if the country does not exist
     */
    public void delete(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new CountryNotFoundException(id);
        }
        countryRepository.deleteById(id);
    }
}
