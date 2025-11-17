package app.molby.rcrecommender.domain.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * CountryAccessRepository Spring Data repository interface for persistence operations.
 * @author Bob Molby
 */
public interface CountryAccessRepository extends JpaRepository<CountryAccessEntity, Long> {

    /**
     * Find all accessible countries for a given source country
     * @param sourceCountry Country that we will determine associated countries for.
     * @return List of countries with access.
     */
    @Query("SELECT ca FROM CountryAccessEntity ca WHERE ca.sourceCountry.countryName = ?1")
    public List<CountryAccessEntity> findBySourceCountry(String sourceCountry);
}
