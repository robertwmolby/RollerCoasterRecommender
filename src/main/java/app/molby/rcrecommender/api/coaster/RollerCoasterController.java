package app.molby.rcrecommender.api.coaster;

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
public class RollerCoasterController {

    private final RollerCoasterService coasterService;
    private final RollerCoasterMapper coasterMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new roller coaster",
            description = "Creates a new roller coaster with the provided attributes."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Roller coaster created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RollerCoasterDto.class),
                            examples = @ExampleObject(
                                    name = "CreatedCoaster",
                                    summary = "Example created coaster",
                                    value = """
                                        {
                                          "id": 42,
                                          "name": "Thunderbolt",
                                          "park": "Adventure World",
                                          "country": "United States",
                                          "heightMeters": 60.5,
                                          "maxSpeedKph": 120,
                                          "inversions": 3,
                                          "launch": true
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided roller coaster data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            ),
                            examples = @ExampleObject(
                                    name = "CoasterValidationError",
                                    summary = "Coaster validation error example",
                                    value = """
                                        {
                                          "timestamp": "2024-01-01T12:00:00Z",
                                          "status": 400,
                                          "error": "Bad Request",
                                          "messages": [
                                            "name must not be blank",
                                            "heightMeters must be greater than 0"
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
                    description = "Roller coaster data to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RollerCoasterDto.class),
                            examples = @ExampleObject(
                                    name = "CreateCoasterRequest",
                                    summary = "Example coaster creation request",
                                    value = """
                                        {
                                          "name": "Thunderbolt",
                                          "park": "Adventure World",
                                          "country": "United States",
                                          "heightMeters": 60.5,
                                          "maxSpeedKph": 120,
                                          "inversions": 3,
                                          "launch": true
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid RollerCoasterDto rollerCoaster
    ) {
        RollerCoasterEntity rollerCoasterEntity = coasterMapper.toEntity(rollerCoaster);
        rollerCoasterEntity = coasterService.create(rollerCoasterEntity);
        return coasterMapper.toDto(rollerCoasterEntity);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get roller coaster by ID",
            description = "Retrieves a single roller coaster by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Roller coaster found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RollerCoasterDto.class),
                            examples = @ExampleObject(
                                    name = "CoasterById",
                                    summary = "Example coaster by ID",
                                    value = """
                                        {
                                          "id": 42,
                                          "name": "Thunderbolt",
                                          "park": "Adventure World",
                                          "country": "United States",
                                          "heightMeters": 60.5,
                                          "maxSpeedKph": 120,
                                          "inversions": 3,
                                          "launch": true
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Roller coaster not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            ),
                            examples = @ExampleObject(
                                    name = "CoasterNotFound",
                                    summary = "Coaster not found error",
                                    value = """
                                        {
                                          "timestamp": "2024-01-01T12:00:00Z",
                                          "status": 404,
                                          "error": "Not Found",
                                          "message": "Roller coaster with ID 42 was not found.",
                                          "path": "/coasters/42"
                                        }
                                        """
                            )
                    )
            )
    })
    public RollerCoasterDto getById(
            @Parameter(
                    description = "Unique ID of the roller coaster",
                    example = "42",
                    required = true
            )
            @PathVariable Long id
    ) {
        RollerCoasterEntity entity = coasterService.getById(id);
        return coasterMapper.toDto(entity);
    }

    @GetMapping
    @Operation(
            summary = "List all roller coasters",
            description = "Returns all roller coasters currently stored."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of roller coasters",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RollerCoasterDto.class),
                    examples = @ExampleObject(
                            name = "CoasterList",
                            summary = "List of coasters example",
                            value = """
                                [
                                  {
                                    "id": 42,
                                    "name": "Thunderbolt",
                                    "park": "Adventure World",
                                    "country": "United States",
                                    "heightMeters": 60.5,
                                    "maxSpeedKph": 120,
                                    "inversions": 3,
                                    "launch": true
                                  },
                                  {
                                    "id": 43,
                                    "name": "Sky Serpent",
                                    "park": "Coaster Kingdom",
                                    "country": "Canada",
                                    "heightMeters": 49.0,
                                    "maxSpeedKph": 95,
                                    "inversions": 1,
                                    "launch": false
                                  }
                                ]
                                """
                    )
            )
    )
    public List<RollerCoasterDto> getAll() {
        return coasterService.getAll().stream()
                .map(coasterMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing roller coaster",
            description = "Updates all fields of an existing roller coaster with the provided data."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Roller coaster updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RollerCoasterDto.class),
                            examples = @ExampleObject(
                                    name = "UpdatedCoaster",
                                    summary = "Updated coaster example",
                                    value = """
                                        {
                                          "id": 42,
                                          "name": "Thunderbolt (Updated)",
                                          "park": "Adventure World",
                                          "country": "United States",
                                          "heightMeters": 62.0,
                                          "maxSpeedKph": 122,
                                          "inversions": 3,
                                          "launch": true
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided roller coaster data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Roller coaster not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public RollerCoasterDto update(
            @Parameter(
                    description = "ID of the roller coaster to update",
                    example = "42",
                    required = true
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New state of the roller coaster",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RollerCoasterDto.class),
                            examples = @ExampleObject(
                                    name = "UpdateCoasterRequest",
                                    summary = "Example coaster update request",
                                    value = """
                                        {
                                          "name": "Thunderbolt (Updated)",
                                          "park": "Adventure World",
                                          "country": "United States",
                                          "heightMeters": 62.0,
                                          "maxSpeedKph": 122,
                                          "inversions": 3,
                                          "launch": true
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid RollerCoasterDto dto
    ) {
        RollerCoasterEntity updatedRollerCoaster = coasterMapper.toEntity(dto);
        updatedRollerCoaster = coasterService.update(id, updatedRollerCoaster);
        return coasterMapper.toDto(updatedRollerCoaster);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a roller coaster",
            description = "Deletes the roller coaster with the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Roller coaster deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Roller coaster not found",
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
                    description = "ID of the roller coaster to delete",
                    example = "42",
                    required = true
            )
            @PathVariable Long id
    ) {
        coasterService.delete(id);
    }
}