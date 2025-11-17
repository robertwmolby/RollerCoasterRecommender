package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.domain.user.UserEntity;
import app.molby.rcrecommender.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link UserEntity} instances.
 *
 * <p>Provides CRUD operations and centralizes user-related business rules,
 * including existence checks and update-merge logic. Controllers should
 * delegate all user persistence behavior to this service.</p>
 * @author Bob Molby
 */
@Service
@RequiredArgsConstructor
public class UserService {

    public static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    /**
     * Creates and persists a new user.  This is handled as an "upsert".  If the user identified
     * exists inside of the datbase already, the existing user record will be treated as an update.
     *
     * @param user the user entity to create
     * @return the saved {@link UserEntity}, including any generated fields
     */
    public UserEntity create(UserEntity user) {
        Optional<UserEntity> optExistingUser = userRepository.findById(user.getId());
        if (optExistingUser.isPresent()) {
            LOGGER.info("Call was made to create user for a user id that already exists {}. " +
                    "Will treat this as an update.", user.getId());
            return update(user.getId(), user);
        }
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by its identifier.
     *
     * @param id the user ID to look up
     * @return the matching {@link UserEntity}
     * @throws UserNotFoundException if no user exists with the given ID
     */
    public UserEntity findByUserId(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a list of all {@link UserEntity} instances
     */
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    /**
     * Updates an existing user with the provided new state.
     *
     * <p>This method performs a full-field update: all updatable fields on the
     * existing user are replaced by values from {@code updated}.</p>
     *
     * @param id      the ID of the user to update
     * @param updated the new state for the user
     * @return the updated and saved {@link UserEntity}
     * @throws UserNotFoundException if no user exists with the given ID
     */
    public UserEntity update(String id, UserEntity updated) {
        UserEntity existing = getById(id);

        existing.setEmailAddress(updated.getEmailAddress());
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setCountry(updated.getCountry());

        return userRepository.save(existing);
    }

    /**
     * Deletes the user with the given identifier.
     *
     * @param id the ID of the user to delete
     * @throws UserNotFoundException if the user does not exist
     */
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Internal helper to retrieve a user or throw an exception.
     *
     * @param id the user ID
     * @return the existing {@link UserEntity}
     * @throws UserNotFoundException if not found
     */
    private UserEntity getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
