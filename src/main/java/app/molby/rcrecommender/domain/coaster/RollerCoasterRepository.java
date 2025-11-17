package app.molby.rcrecommender.domain.coaster;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RollerCoasterRepository Spring Data repository interface for persistence operations.
 * @author Bob Molby
 */
public interface RollerCoasterRepository extends JpaRepository<RollerCoasterEntity, Long> {
}
