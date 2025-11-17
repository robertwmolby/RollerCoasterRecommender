package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.api.shared.ErrorResponse;
import app.molby.rcrecommender.api.shared.ValidationErrorResponse;
import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coasters")
@RequiredArgsConstructor
@Tag(
        name = "Roller Coasters",
        description = "CRUD operations for roller coasters."
)
/**
 * REST controller providing CRUD endpoints for managing roller coasters.
 */
public class RollerCoasterController {

    /** Service handling business logic for roller coasters. */
    private final RollerCoasterService coasterService;

    /** Mapper converting between RollerCoasterEntity and RollerCoasterDto. */
    private final RollerCoasterMapper coasterMapper;

    /**
     * Creates a new roller coaster.
     *
     * @param rollerCoaster DTO representing the roller coaster to create
     * @return the created roller coaster as a DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new roller coaster",
            description = "Creates a new roller coaster with the provided attributes."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Roller coaster created successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = RollerCoasterDto.class,
                                    name = "CreatedRollerCoaster",
                                    description = "Representation of the newly created roller coaster."
                            ),
                            examples = @ExampleObject(
                                    name = "CreatedCoasterExample",
                                    summary = "Created roller coaster example",
                                    description = "Example response body returned after successfully creating a roller coaster.",
                                    value = """
                                        {
                                          "id": 101,
                                          "name": "Millennium Force",
                                          "amusementPark": "Cedar Point",
                                          "type": "Steel",
                                          "design": "Sitdown",
                                          "status": "Operating",
                                          "manufacturer": "Intamin",
                                          "model": "Giga Coaster",
                                          "length": 6595,
                                          "height": 310,
                                          "drop": 300,
                                          "inversionCount": 0,
                                          "speed": 93,
                                          "verticalAngle": 80,
                                          "restraints": "Lap Bar",
                                          "gForce": 4.5,
                                          "intensity": "Thrill",
                                          "duration": 150,
                                          "country": "United States",
                                          "averageRating": 4.8
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided roller coaster data.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ValidationErrorResponse.class,
                                    name = "RollerCoasterValidationError",
                                    description = "Validation error payload when creating or updating a roller coaster."
                            ),
                            examples = @ExampleObject(
                                    name = "CoasterValidationErrorExample",
                                    summary = "Roller coaster validation error example",
                                    description = "Example of a validation error when required fields are missing or invalid.",
                                    value = """
                                        {
                                          "timestamp": "2024-01-01T12:00:00Z",
                                          "status": 400,
                                          "error": "Bad Request",
                                          "messages": [
                                            "name must not be blank",
                                            "length must be greater than 0"
                                          ],
                                          "path": "/coasters"
                                        }
                                        """
                            )
                    )
            )
    })
    public RollerCoasterDto create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Roller coaster data to create.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = RollerCoasterDto.class,
                                    name = "CreateRollerCoasterRequest",
                                    description = "Payload describing the roller coaster to be created."
                            ),
                            examples = @ExampleObject(
                                    name = "CreateCoasterRequestExample",
                                    summary = "Create roller coaster request example",
                                    description = "Example request body used to create a new roller coaster.",
                                    value = """
                                        {
                                          "name": "Millennium Force",
                                          "amusementPark": "Cedar Point",
                                          "type": "Steel",
                                          "design": "Sitdown",
                                          "status": "Operating",
                                          "manufacturer": "Intamin",
                                          "model": "Giga Coaster",
                                          "length": 6595,
                                          "height": 310,
                                          "drop": 300,
                                          "inversionCount": 0,
                                          "speed": 93,
                                          "verticalAngle": 80,
                                          "restraints": "Lap Bar",
                                          "gForce": 4.5,
                                          "intensity": "Thrill",
                                          "duration": 150,
                                          "country": "United States",
                                          "averageRating": 4.8
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid RollerCoasterDto rollerCoaster
    ) {
        RollerCoasterEntity rollerCoasterEntity = coasterMapper.toRollerCoasterEntity(rollerCoaster);
        rollerCoasterEntity = coasterService.create(rollerCoasterEntity);
        return coasterMapper.toRollerCoasterDto(rollerCoasterEntity);
    }

    /**
     * Retrieves a roller coaster by its ID.
     *
     * @param id the ID of the roller coaster
     * @return the roller coaster as a DTO
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get roller coaster by ID",
            description = "Retrieves a single roller coaster by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Roller coaster found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = RollerCoasterDto.class,
                                    name = "RollerCoasterById",
                                    description = "Representation of a single roller coaster resource."
                            ),
                            examples = @ExampleObject(
                                    name = "CoasterByIdExample",
                                    summary = "Roller coaster by ID example",
                                    description = "Example response body when fetching a roller coaster by ID.",
                                    value = """
                                        {
                                          "id": 101,
                                          "name": "Millennium Force",
                                          "amusementPark": "Cedar Point",
                                          "type": "Steel",
                                          "design": "Sitdown",
                                          "status": "Operating",
                                          "manufacturer": "Intamin",
                                          "model": "Giga Coaster",
                                          "length": 6595,
                                          "height": 310,
                                          "drop": 300,
                                          "inversionCount": 0,
                                          "speed": 93,
                                          "verticalAngle": 80,
                                          "restraints": "Lap Bar",
                                          "gForce": 4.5,
                                          "intensity": "Thrill",
                                          "duration": 150,
                                          "country": "United States",
                                          "averageRating": 4.8
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Roller coaster not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class,
                                    name = "RollerCoasterNotFoundError",
                                    description = "Error payload returned when a roller coaster is not found."
                            ),
                            examples = @ExampleObject(
                                    name = "CoasterNotFoundExample",
                                    summary = "Roller coaster not found example",
                                    description = "Example error response when a roller coaster with the specified ID does not exist.",
                                    value = """
                                        {
                                          "timestamp": "2024-01-01T12:00:00Z",
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Roller coaster with ID 101 was not found.",
                                          "path": "/coasters/101"
                                        }
                                        """
                            )
                    )
            )
    })
    public RollerCoasterDto findById(
            @Parameter(
                    name = "id",
                    description = "Unique ID of the roller coaster.",
                    example = "101",
                    required = true
            )
            @PathVariable Long id
    ) {
        RollerCoasterEntity entity = coasterService.findById(id);
        return coasterMapper.toRollerCoasterDto(entity);
    }

    /**
     * Returns all roller coasters.
     *
     * @return a list of all roller coasters as DTOs
     */
    @GetMapping
    @Operation(
            summary = "List all roller coasters",
            description = "Returns all roller coasters currently stored."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of roller coasters.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = RollerCoasterDto.class,
                            name = "RollerCoasterList",
                            description = "List of roller coaster resources."
                    ),
                    examples = @ExampleObject(
                            name = "CoasterListExample",
                            summary = "List of roller coasters example",
                            description = "Example response body containing multiple roller coasters.",
                            value = """
                                [
                                  {
                                    "id": 101,
                                    "name": "Millennium Force",
                                    "amusementPark": "Cedar Point",
                                    "type": "Steel",
                                    "design": "Sitdown",
                                    "status": "Operating",
                                    "manufacturer": "Intamin",
                                    "model": "Giga Coaster",
                                    "length": 6595,
                                    "height": 310,
                                    "drop": 300,
                                    "inversionCount": 0,
                                    "speed": 93,
                                    "verticalAngle": 80,
                                    "restraints": "Lap Bar",
                                    "gForce": 4.5,
                                    "intensity": "Thrill",
                                    "duration": 150,
                                    "country": "United States",
                                    "averageRating": 4.8
                                  },
                                  {
                                    "id": 102,
                                    "name": "GateKeeper",
                                    "amusementPark": "Cedar Point",
                                    "type": "Steel",
                                    "design": "Wing",
                                    "status": "Operating",
                                    "manufacturer": "Bolliger & Mabillard",
                                    "model": "Wing Coaster",
                                    "length": 4164,
                                    "height": 170,
                                    "drop": 164,
                                    "inversionCount": 6,
                                    "speed": 67,
                                    "verticalAngle": 65,
                                    "restraints": "Over-the-Shoulder Harness",
                                    "gForce": 4.0,
                                    "intensity": "Thrill",
                                    "duration": 160,
                                    "country": "United States",
                                    "averageRating": 4.2
                                  }
                                ]
                                """
                    )
            )
    )
    public List<RollerCoasterDto> findAll() {
        return coasterService.findAll().stream()
                .map(coasterMapper::toRollerCoasterDto)
                .toList();
    }

    /**
     * Deletes a roller coaster by ID.
     *
     * @param id the ID of the roller coaster to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a roller coaster",
            description = "Deletes the roller coaster with the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Roller coaster deleted successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Roller coaster not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class,
                                    name = "RollerCoasterDeleteNotFoundError",
                                    description = "Error payload returned when attempting to delete a non-existent roller coaster."
                            ),
                            examples = @ExampleObject(
                                    name = "DeleteCoasterNotFoundExample",
                                    summary = "Delete target not found example",
                                    description = "Example error response when attempting to delete a roller coaster that does not exist.",
                                    value = """
                                        {
                                          "timestamp": "2024-01-01T12:00:00Z",
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Roller coaster with ID 999 was not found.",
                                          "path": "/coasters/999"
                                        }
                                        """
                            )
                    )
            )
    })
    public void delete(
            @Parameter(
                    name = "id",
                    description = "ID of the roller coaster to delete.",
                    example = "101",
                    required = true
            )
            @PathVariable Long id
    ) {
        coasterService.delete(id);
    }
}
