package app.molby.rcrecommender.domain.country;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CountryRepository Spring Data repository interface for persistence operations.
 * @author Bob Molby
 */
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
}
