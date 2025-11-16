package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryAccessEntity;
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
@RequestMapping("/country-access")
@RequiredArgsConstructor
@Tag(
        name = "Country Access",
        description = "CRUD operations for mapping users to countries they can access."
)
public class CountryAccessController {

    private final CountryAccessService service;
    private final CountryAccessMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new country access mapping",
            description = "Creates a new mapping indicating which country a user has access to."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Country access mapping created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryAccessDto.class),
                            examples = @ExampleObject(
                                    name = "CreatedCountryAccess",
                                    summary = "Created mapping example",
                                    value = """
                                        {
                                          "id": 10,
                                          "userId": "user-123",
                                          "countryCode": "US"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided mapping data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            )
    })
    public CountryAccessDto create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Country access mapping to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryAccessDto.class),
                            examples = @ExampleObject(
                                    name = "CreateCountryAccessRequest",
                                    summary = "Country access creation request example",
                                    value = """
                                        {
                                          "userId": "user-123",
                                          "countryCode": "US"
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid CountryAccessDto dto
    ) {
        CountryAccessEntity toSave = mapper.toEntity(dto);
        CountryAccessEntity saved = service.create(toSave);
        return mapper.toDto(saved);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get country access mapping by ID",
            description = "Retrieves a single country access mapping by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mapping found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryAccessDto.class),
                            examples = @ExampleObject(
                                    name = "CountryAccessById",
                                    summary = "Mapping by ID example",
                                    value = """
                                        {
                                          "id": 10,
                                          "userId": "user-123",
                                          "countryCode": "US"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mapping not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public CountryAccessDto getById(
            @Parameter(
                    description = "ID of the mapping",
                    example = "10",
                    required = true
            )
            @PathVariable Long id
    ) {
        CountryAccessEntity entity = service.getById(id);
        return mapper.toDto(entity);
    }

    @GetMapping
    @Operation(
            summary = "List all country access mappings",
            description = "Returns all user-to-country access mappings."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of mappings",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CountryAccessDto.class),
                    examples = @ExampleObject(
                            name = "CountryAccessList",
                            summary = "List of mappings example",
                            value = """
                                [
                                  {
                                    "id": 10,
                                    "userId": "user-123",
                                    "countryCode": "US"
                                  },
                                  {
                                    "id": 11,
                                    "userId": "user-123",
                                    "countryCode": "CA"
                                  }
                                ]
                                """
                    )
            )
    )
    public List<CountryAccessDto> getAll() {
        return service.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing country access mapping",
            description = "Updates an existing mapping with the provided data."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mapping updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryAccessDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided mapping data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mapping not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public CountryAccessDto update(
            @Parameter(
                    description = "ID of the mapping to update",
                    example = "10",
                    required = true
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New state of the mapping",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryAccessDto.class),
                            examples = @ExampleObject(
                                    name = "UpdateCountryAccessRequest",
                                    summary = "Country access update request example",
                                    value = """
                                        {
                                          "userId": "user-123",
                                          "countryCode": "CA"
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid CountryAccessDto dto
    ) {
        CountryAccessEntity newState = mapper.toEntity(dto);
        CountryAccessEntity updated = service.update(id, newState);
        return mapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a country access mapping",
            description = "Deletes the mapping with the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Mapping deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mapping not found",
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
                    description = "ID of the mapping to delete",
                    example = "10",
                    required = true
            )
            @PathVariable Long id
    ) {
        service.delete(id);
    }
}