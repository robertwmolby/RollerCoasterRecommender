package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.domain.user.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(
        name = "Users",
        description = "CRUD operations for users of the roller coaster recommendation system."
)
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    private final ConfigurableEnvironment env;

    @GetMapping
    @Operation(
            summary = "List all users",
            description = "Returns all users currently stored."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of users",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class),
                    examples = @ExampleObject(
                            name = "UserList",
                            summary = "List of users example",
                            value = """
                                [
                                  {
                                    "id": "user-123",
                                    "email": "alice@example.com",
                                    "displayName": "Alice",
                                    "country": "US"
                                  },
                                  {
                                    "id": "user-456",
                                    "email": "bob@example.com",
                                    "displayName": "Bob",
                                    "country": "CA"
                                  }
                                ]
                                """
                    )
            )
    )
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a single user by their unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    name = "UserById",
                                    summary = "User by ID example",
                                    value = """
                                        {
                                          "id": "user-123",
                                          "email": "alice@example.com",
                                          "displayName": "Alice",
                                          "country": "US"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public UserDto getById(
            @Parameter(
                    description = "ID of the user",
                    example = "user-123",
                    required = true
            )
            @PathVariable String id
    ) {
        UserEntity entity = userService.getById(id);
        return userMapper.toDto(entity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the supplied details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    name = "CreatedUser",
                                    summary = "Created user example",
                                    value = """
                                        {
                                          "id": "user-123",
                                          "email": "alice@example.com",
                                          "displayName": "Alice",
                                          "country": "US"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided user data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            )
    })
    public UserDto create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    name = "CreateUserRequest",
                                    summary = "User creation request example",
                                    value = """
                                        {
                                          "email": "alice@example.com",
                                          "displayName": "Alice",
                                          "country": "US"
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid UserDto dto
    ) {
        UserEntity toSave = userMapper.toEntity(dto);
        UserEntity saved = userService.create(toSave);
        return userMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing user",
            description = "Updates all fields of an existing user with the provided data."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    name = "UpdatedUser",
                                    summary = "Updated user example",
                                    value = """
                                    {
                                      "id": "user-123",
                                      "email": "alice@example.com",
                                      "displayName": "Alice A.",
                                      "country": "US"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided user data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public UserDto update(
            @Parameter(
                    description = "ID of the user to update",
                    example = "user-123",
                    required = true
            )
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New state of the user",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class),
                            examples = @ExampleObject(
                                    name = "UpdateUserRequest",
                                    summary = "User update request example",
                                    value = """
                                        {
                                          "email": "alice@example.com",
                                          "displayName": "Alice A.",
                                          "country": "US"
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid UserDto dto
    ) {
        // Service owns merge logic; mapper builds a “new-state” entity
        UserEntity updatedState = userMapper.toEntity(dto);
        UserEntity updated = userService.update(id, updatedState);
        return userMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a user",
            description = "Deletes the user with the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "User deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public void delete(
            @Parameter(
                    description = "ID of the user to delete",
                    example = "user-123",
                    required = true
            )
            @PathVariable String id
    ) {
        userService.delete(id);
    }
}