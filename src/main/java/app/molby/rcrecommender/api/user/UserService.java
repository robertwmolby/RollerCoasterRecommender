package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.domain.user.UserEntity;
import app.molby.rcrecommender.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // ---- CREATE ----
    public UserEntity create(UserEntity user) {
        return userRepository.save(user);
    }

    // ---- READ ----
    public UserEntity getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    // ---- UPDATE ----
    public UserEntity update(String id, UserEntity updated) {
        UserEntity existing = getById(id);

        existing.setEmailAddress(updated.getEmailAddress());
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setCountry(updated.getCountry());

        return userRepository.save(existing);
    }

    // ---- DELETE ----
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
