package app.molby.rcrecommender.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository Spring Data repository interface for persistence operations.
 * @author Bob Molby
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
