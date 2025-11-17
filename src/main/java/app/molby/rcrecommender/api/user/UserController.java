package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.api.shared.ErrorResponse;
import app.molby.rcrecommender.api.shared.ValidationErrorResponse;
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
/**
 * UserController REST controller in the roller coaster recommender application.
 *
 * <p>Exposes endpoints for managing {@link UserDto} resources, including
 * listing, retrieving, creating, updating, and deleting users. Users
 * represent end-consumers of the recommendation system and hold basic
 * profile information and their associated coaster ratings.</p>
 * @author Bob Molby
 */
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Spring environment, available for future use (e.g. debugging or
     * environment-specific behavior).
     */
    private final ConfigurableEnvironment env;

    /**
     * Retrieve all users.
     *
     * @return a list of all users as {@link UserDto} instances
     */
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
                                    "id": "jean_luc_picard",
                                    "emailAddress": "alice@example.com",
                                    "firstName": "Alice",
                                    "lastName": "Anderson",
                                    "country": "US",
                                    "coasterRatings": [
                                      {
                                        "id": 2001,
                                        "userId": "jean_luc_picard",
                                        "coasterId": 101,
                                        "rating": 4.5
                                      }
                                    ]
                                  },
                                  {
                                    "id": "user-456",
                                    "emailAddress": "bob@example.com",
                                    "firstName": "Bob",
                                    "lastName": "Baker",
                                    "country": "CA",
                                    "coasterRatings": []
                                  }
                                ]
                                """
                    )
            )
    )
    public List<UserDto> findAll() {
        return userService.findAll().stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    /**
     * Retrieve a single user by its identifier.
     *
     * @param id the unique identifier of the user to retrieve
     * @return the matching {@link UserDto}
     */
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
                                          "id": "jean_luc_picard",
                                          "emailAddress": "alice@example.com",
                                          "firstName": "Alice",
                                          "lastName": "Anderson",
                                          "country": "US",
                                          "coasterRatings": [
                                            {
                                              "id": 2001,
                                              "userId": "jean_luc_picard",
                                              "coasterId": 101,
                                              "rating": 4.5
                                            }
                                          ]
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
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    public UserDto findByUserId(
            @Parameter(
                    description = "ID of the user",
                    example = "jean_luc_picard",
                    required = true
            )
            @PathVariable String id
    ) {
        UserEntity entity = userService.findByUserId(id);
        return userMapper.toUserDto(entity);
    }

    /**
     * Create a new user.
     *
     * @param userDto the user details to persist
     * @return the created {@link UserDto}, including its generated identifier
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the supplied details.  If the user provided already " +
                    "exists this will be treated as an upsert and perform an update on the existing record " +
                    "rather than fail."
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
                                          "id": "jean_luc_picard",
                                          "emailAddress": "alice@example.com",
                                          "firstName": "Jean-Luc",
                                          "lastName": "Picard",
                                          "country": "United States",
                                          "coasterRatings": []
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
                                    implementation = ValidationErrorResponse.class
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
                                          "emailAddress": "jean.luc.picard@uss.enterprise.org",
                                          "firstName": "Jean-Luc",
                                          "lastName": "Picard",
                                          "country": "United States",
                                          "coasterRatings": []
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid UserDto userDto
    ) {
        UserEntity userEntity = userMapper.toUserEntity(userDto);
        userEntity = userService.create(userEntity);
        return userMapper.toUserDto(userEntity);
    }

    /**
     * Update an existing user with new data.
     *
     * @param id  the identifier of the user to update
     * @param userDto the new state for the user
     * @return the updated {@link UserDto}
     */
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
                                      "id": "jean_luc_picard",
                                      "emailAddress": "jean.luc.picard@uss.enterprise.org",
                                      "firstName": "Jean-Luc",
                                      "lastName": "Picard",
                                      "country": "United States",
                                      "coasterRatings": [
                                        {
                                          "id": 2001,
                                          "userId": "jean_luc_picard",
                                          "coasterId": 101,
                                          "rating": 4.5
                                        },
                                        {
                                          "id": 2002,
                                          "userId": "jean_luc_picard",
                                          "coasterId": 102,
                                          "rating": 3.5
                                        }
                                      ]
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
                                    implementation = ValidationErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    public UserDto update(
            @Parameter(
                    description = "ID of the user to update",
                    example = "jean_luc_picard",
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
                                          "emailAddress": "alice@example.com",
                                          "firstName": "Alice",
                                          "lastName": "Andrews",
                                          "country": "US",
                                          "coasterRatings": [
                                            {
                                              "id": 2001,
                                              "userId": "jean_luc_picard",
                                              "coasterId": 101,
                                              "rating": 4.5
                                            }
                                          ]
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid UserDto userDto
    ) {
        // Service owns merge logic; mapper builds a “new-state” entity
        UserEntity userEntity = userMapper.toUserEntity(userDto);
        userEntity = userService.update(id, userEntity);
        return userMapper.toUserDto(userEntity);
    }

    /**
     * Delete a user by its identifier.
     *
     * @param id the unique identifier of the user to delete
     */
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
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    public void delete(
            @Parameter(
                    description = "ID of the user to delete",
                    example = "jean_luc_picard",
                    required = true
            )
            @PathVariable String id
    ) {
        userService.delete(id);
    }
}
