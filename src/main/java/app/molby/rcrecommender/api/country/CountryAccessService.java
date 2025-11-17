package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryAccessEntity;
import app.molby.rcrecommender.domain.country.CountryAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/**
 * CountryAccessService service component in the roller coaster recommender application.
 */
public class CountryAccessService {

    private final CountryAccessRepository countryAccessRepository;
/**
 * create TODO: describe purpose.
 *
 * @param mapping TODO: describe parameter
 * @return TODO: describe return value
 */
/**
 * create TODO: describe purpose.
 *
 * @param mapping TODO: describe parameter
 * @return TODO: describe return value
 */

    public CountryAccessEntity create(CountryAccessEntity mapping) {
        return countryAccessRepository.save(mapping);
    }
/**
 * getById TODO: describe purpose.
 *
 * @param id TODO: describe parameter
 * @return TODO: describe return value
 */
/**
 * getById TODO: describe purpose.
 *
 * @param id TODO: describe parameter
 * @return TODO: describe return value
 */

    public CountryAccessEntity getById(Long id) {
        return countryAccessRepository.findById(id)
                .orElseThrow(() -> new CountryAccessNotFoundException(id));
    }
/**
 * getAll TODO: describe purpose.
 *
 * @return TODO: describe return value
 */
/**
 * getAll TODO: describe purpose.
 *
 * @return TODO: describe return value
 */

    public List<CountryAccessEntity> getAll() {
        return countryAccessRepository.findAll();
    }
/**
 * update TODO: describe purpose.
 *
 * @param id TODO: describe parameter
 * @param updated TODO: describe parameter
 * @return TODO: describe return value
 */
/**
 * update TODO: describe purpose.
 *
 * @param id TODO: describe parameter
 * @param updated TODO: describe parameter
 * @return TODO: describe return value
 */

    public CountryAccessEntity update(Long id, CountryAccessEntity updated) {
        if (!countryAccessRepository.existsById(id)) {
            throw new CountryAccessNotFoundException(id);
        }
        updated.setId(id);
        return countryAccessRepository.save(updated);
    }
/**
 * delete TODO: describe purpose.
 *
 * @param id TODO: describe parameter
 */
/**
 * delete TODO: describe purpose.
 *
 * @param id TODO: describe parameter
 */

    public void delete(Long id) {
        if (!countryAccessRepository.existsById(id)) {
            throw new CountryAccessNotFoundException(id);
        }
        countryAccessRepository.deleteById(id);
    }
}
