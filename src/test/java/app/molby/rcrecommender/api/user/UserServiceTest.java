package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.api.user.UserNotFoundException;
import app.molby.rcrecommender.domain.user.UserEntity;
import app.molby.rcrecommender.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService subject;

    @Test
    void subjectIsCreated() {
        assertNotNull(subject);
    }

    @Test
    void create_shouldDelegateToRepositorySave() {
        UserEntity user = new UserEntity();
        UserEntity saved = new UserEntity();
        saved.setId("user-1");

        given(userRepository.save(user)).willReturn(saved);

        UserEntity result = subject.create(user);

        assertSame(saved, result);
        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findById_shouldReturnUser_whenFound() {
        String id = "jean_luc_picard";
        UserEntity user = new UserEntity();
        user.setId(id);

        given(userRepository.findById(id)).willReturn(Optional.of(user));

        UserEntity result = subject.findById(id);

        assertSame(user, result);
        verify(userRepository).findById(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findById_shouldThrowUserNotFoundException_whenNotFound() {
        String id = "missing-user";
        given(userRepository.findById(id)).willReturn(Optional.empty());

        UserNotFoundException ex =
                assertThrows(UserNotFoundException.class, () -> subject.findById(id));

        assertTrue(ex.getMessage().contains(id));
        verify(userRepository).findById(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findAll_shouldReturnAllUsersFromRepository() {
        UserEntity u1 = new UserEntity();
        UserEntity u2 = new UserEntity();
        List<UserEntity> users = List.of(u1, u2);

        given(userRepository.findAll()).willReturn(users);

        List<UserEntity> result = subject.findAll();

        assertEquals(users, result);
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void update_shouldMergeFieldsAndSave_whenUserExists() {
        String id = "user-1";

        UserEntity existing = new UserEntity();
        existing.setId(id);
        existing.setEmailAddress("old@example.com");
        existing.setFirstName("Old");
        existing.setLastName("Name");
        existing.setCountry("US");

        UserEntity updated = new UserEntity();
        updated.setEmailAddress("new@example.com");
        updated.setFirstName("New");
        updated.setLastName("User");
        updated.setCountry("CA");

        given(userRepository.findById(id)).willReturn(Optional.of(existing));
        given(userRepository.save(existing)).willReturn(existing);

        UserEntity result = subject.update(id, updated);

        assertSame(existing, result);
        assertEquals("new@example.com", existing.getEmailAddress());
        assertEquals("New", existing.getFirstName());
        assertEquals("User", existing.getLastName());
        assertEquals("CA", existing.getCountry());

        verify(userRepository).findById(id);
        verify(userRepository).save(existing);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void update_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        String id = "missing-user";
        UserEntity updated = new UserEntity();
        given(userRepository.findById(id)).willReturn(Optional.empty());

        UserNotFoundException ex =
                assertThrows(UserNotFoundException.class, () -> subject.update(id, updated));

        assertTrue(ex.getMessage().contains(id));
        verify(userRepository).findById(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void delete_shouldDelete_whenUserExists() {
        String id = "user-1";

        given(userRepository.existsById(id)).willReturn(true);

        subject.delete(id);

        verify(userRepository).existsById(id);
        verify(userRepository).deleteById(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void delete_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        String id = "missing-user";

        given(userRepository.existsById(id)).willReturn(false);

        UserNotFoundException ex =
                assertThrows(UserNotFoundException.class, () -> subject.delete(id));

        assertTrue(ex.getMessage().contains(id));
        verify(userRepository).existsById(id);
        verifyNoMoreInteractions(userRepository);
    }
}