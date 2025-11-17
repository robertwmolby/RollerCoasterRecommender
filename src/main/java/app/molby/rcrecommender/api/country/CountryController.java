package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryEntity;
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
@RequestMapping("/countries")
@RequiredArgsConstructor
@Tag(
        name = "Countries",
        description = "CRUD operations for countries where roller coasters are located."
)
/**
 * CountryController REST controller in the roller coaster recommender application.
 */
public class CountryController {

    private final CountryService service;
    private final CountryMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new country",
            description = "Creates a new country entry."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Country created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryDto.class),
                            examples = @ExampleObject(
                                    name = "CreatedCountry",
                                    summary = "Created country example",
                                    value = """
                                        {
                                          "id": 1,
                                          "countryName": "United States"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided country data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            )
    })
    public CountryDto create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Country data to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryDto.class),
                            examples = @ExampleObject(
                                    name = "CreateCountryRequest",
                                    summary = "Country creation request example",
                                    value = """
                                        {
                                          "countryName": "United States"
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid CountryDto dto
    ) {
        CountryEntity toSave = mapper.toCountryEntity(dto);
        CountryEntity saved = service.create(toSave);
        return mapper.toCountryDto(saved);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get country by ID",
            description = "Retrieves a single country by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Country found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryDto.class),
                            examples = @ExampleObject(
                                    name = "CountryById",
                                    summary = "Country by ID example",
                                    value = """
                                        {
                                          "id": 1,
                                          "countryName": "United States"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public CountryDto getById(
            @Parameter(
                    description = "ID of the country",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        CountryEntity entity = service.getById(id);
        return mapper.toCountryDto(entity);
    }

    @GetMapping
    @Operation(
            summary = "List all countries",
            description = "Returns all countries currently stored."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of countries",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CountryDto.class),
                    examples = @ExampleObject(
                            name = "CountryList",
                            summary = "List of countries example",
                            value = """
                                [
                                  {
                                    "id": 1,
                                    "countryName": "United States"
                                  },
                                  {
                                    "id": 2,
                                    "countryName": "Canada"
                                  }
                                ]
                                """
                    )
            )
    )
    public List<CountryDto> getAll() {
        return service.getAll().stream()
                .map(mapper::toCountryDto)
                .toList();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing country",
            description = "Updates all fields of an existing country with the provided data."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Country updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryDto.class),
                            examples = @ExampleObject(
                                    name = "UpdatedCountry",
                                    summary = "Updated country example",
                                    value = """
                                        {
                                          "id": 1,
                                          "countryName": "United States"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided country data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public CountryDto update(
            @Parameter(
                    description = "ID of the country to update",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New state of the country",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CountryDto.class),
                            examples = @ExampleObject(
                                    name = "UpdateCountryRequest",
                                    summary = "Country update request example",
                                    value = """
                                        {
                                          "id": "1",
                                          "countryName": "United States"
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid CountryDto dto
    ) {
        CountryEntity newState = mapper.toCountryEntity(dto);
        CountryEntity updated = service.update(id, newState);
        return mapper.toCountryDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a country",
            description = "Deletes the country with the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Country deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Country not found",
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
                    description = "ID of the country to delete",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        service.delete(id);
    }
}