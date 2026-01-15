package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.domain.user.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ConfigurableEnvironment env;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAll_returnsListOfUsers() throws Exception {
        // Given
        UserEntity user1 = new UserEntity();
        user1.setId("jean_luc_picard");
        user1.setEmailAddress("alice@example.com");
        user1.setFirstName("Alice");
        user1.setLastName("Anderson");
        user1.setCountry("US");

        UserEntity user2 = new UserEntity();
        user2.setId("user-456");
        user2.setEmailAddress("bob@example.com");
        user2.setFirstName("Bob");
        user2.setLastName("Baker");
        user2.setCountry("CA");

        UserDto userDto1 = new UserDto();
        userDto1.setId("jean_luc_picard");
        userDto1.setEmailAddress("alice@example.com");
        userDto1.setFirstName("Alice");
        userDto1.setLastName("Anderson");
        userDto1.setCountry("US");

        UserDto userDto2 = new UserDto();
        userDto2.setId("user-456");
        userDto2.setEmailAddress("bob@example.com");
        userDto2.setFirstName("Bob");
        userDto2.setLastName("Baker");
        userDto2.setCountry("CA");

        when(userService.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toUserDto(user1)).thenReturn(userDto1);
        when(userMapper.toUserDto(user2)).thenReturn(userDto2);

        // When / Then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("jean_luc_picard"))
                .andExpect(jsonPath("$[0].emailAddress").value("alice@example.com"))
                .andExpect(jsonPath("$[1].id").value("user-456"))
                .andExpect(jsonPath("$[1].emailAddress").value("bob@example.com"));

        verify(userService, times(1)).findAll();
    }

    @Test
    void findById_returnsUser() throws Exception {
        // Given
        String userId = "jean_luc_picard";

        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setEmailAddress("alice@example.com");
        entity.setFirstName("Alice");
        entity.setLastName("Anderson");
        entity.setCountry("US");

        UserDto dto = new UserDto();
        dto.setId(userId);
        dto.setEmailAddress("alice@example.com");
        dto.setFirstName("Alice");
        dto.setLastName("Anderson");
        dto.setCountry("US");

        when(userService.findByUserId(userId)).thenReturn(entity);
        when(userMapper.toUserDto(entity)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.emailAddress").value("alice@example.com"))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Anderson"))
                .andExpect(jsonPath("$.country").value("US"));

        verify(userService, times(1)).findByUserId(userId);
    }

    @Test
    void create_createsUserAndReturnsCreated() throws Exception {
        // Given (request DTO without id)
        UserDto requestDto = new UserDto();
        requestDto.setEmailAddress("alice@example.com");
        requestDto.setFirstName("Alice");
        requestDto.setLastName("Anderson");
        requestDto.setCountry("US");

        UserEntity entityToSave = new UserEntity();
        entityToSave.setEmailAddress("alice@example.com");
        entityToSave.setFirstName("Alice");
        entityToSave.setLastName("Anderson");
        entityToSave.setCountry("US");

        UserEntity savedEntity = new UserEntity();
        savedEntity.setId("jean_luc_picard");
        savedEntity.setEmailAddress("alice@example.com");
        savedEntity.setFirstName("Alice");
        savedEntity.setLastName("Anderson");
        savedEntity.setCountry("US");

        UserDto responseDto = new UserDto();
        responseDto.setId("jean_luc_picard");
        responseDto.setEmailAddress("alice@example.com");
        responseDto.setFirstName("Alice");
        responseDto.setLastName("Anderson");
        responseDto.setCountry("US");

        when(userMapper.toUserEntity(any(UserDto.class))).thenReturn(entityToSave);
        when(userService.create(entityToSave)).thenReturn(savedEntity);
        when(userMapper.toUserDto(savedEntity)).thenReturn(responseDto);

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        // When / Then
        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("jean_luc_picard"))
                .andExpect(jsonPath("$.emailAddress").value("alice@example.com"))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Anderson"))
                .andExpect(jsonPath("$.country").value("US"));

        verify(userMapper, times(1)).toUserEntity(any(UserDto.class));
        verify(userService, times(1)).create(entityToSave);
        verify(userMapper, times(1)).toUserDto(savedEntity);
    }

    @Test
    void update_updatesUserAndReturnsUpdated() throws Exception {
        // Given
        String userId = "jean_luc_picard";

        UserDto requestDto = new UserDto();
        requestDto.setEmailAddress("alice@example.com");
        requestDto.setFirstName("Alice");
        requestDto.setLastName("Andrews");
        requestDto.setCountry("US");

        UserEntity updatedState = new UserEntity();
        updatedState.setEmailAddress("alice@example.com");
        updatedState.setFirstName("Alice");
        updatedState.setLastName("Andrews");
        updatedState.setCountry("US");

        UserEntity updatedEntity = new UserEntity();
        updatedEntity.setId(userId);
        updatedEntity.setEmailAddress("alice@example.com");
        updatedEntity.setFirstName("Alice");
        updatedEntity.setLastName("Andrews");
        updatedEntity.setCountry("US");

        UserDto responseDto = new UserDto();
        responseDto.setId(userId);
        responseDto.setEmailAddress("alice@example.com");
        responseDto.setFirstName("Alice");
        responseDto.setLastName("Andrews");
        responseDto.setCountry("US");

        when(userMapper.toUserEntity(any(UserDto.class))).thenReturn(updatedState);
        when(userService.update(userId, updatedState)).thenReturn(updatedEntity);
        when(userMapper.toUserDto(updatedEntity)).thenReturn(responseDto);

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        // When / Then
        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.lastName").value("Andrews"));

        verify(userMapper, times(1)).toUserEntity(any(UserDto.class));
        verify(userService, times(1)).update(userId, updatedState);
        verify(userMapper, times(1)).toUserDto(updatedEntity);
    }

    @Test
    void delete_deletesUserAndReturnsNoContent() throws Exception {
        // Given
        String userId = "jean_luc_picard";

        doNothing().when(userService).delete(userId);

        // When / Then
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete(userId);
    }
}
