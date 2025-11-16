package app.molby.rcrecommender.domain.country;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryAccessRepository extends JpaRepository<CountryAccessEntity, Long> {
}
