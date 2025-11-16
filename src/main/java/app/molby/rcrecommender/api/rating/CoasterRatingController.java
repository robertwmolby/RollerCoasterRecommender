package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
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
@RequestMapping("/ratings")
@RequiredArgsConstructor
@Tag(
        name = "Coaster Ratings",
        description = "CRUD operations for roller coaster ratings."
)
public class CoasterRatingController {

    private final CoasterRatingService ratingService;
    private final CoasterRatingMapper mapper;

    // ---- CREATE ----
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new coaster rating",
            description = "Creates a new rating for a specific roller coaster."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Rating created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoasterRatingDto.class),
                            examples = @ExampleObject(
                                    name = "CreatedRating",
                                    summary = "Created rating example",
                                    value = """
                                        {
                                          "id": 1001,
                                          "userId": "user-123",
                                          "coasterId": 42,
                                          "score": 5,
                                          "comment": "Amazing ride with incredible airtime!"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided rating data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            )
    })
    public CoasterRatingDto create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Rating data to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoasterRatingDto.class),
                            examples = @ExampleObject(
                                    name = "CreateRatingRequest",
                                    summary = "Rating creation request example",
                                    value = """
                                        {
                                          "userId": "user-123",
                                          "coasterId": 42,
                                          "score": 5,
                                          "comment": "Amazing ride with incredible airtime!"
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid CoasterRatingDto dto
    ) {
        CoasterRatingEntity toSave = mapper.toEntity(dto);
        CoasterRatingEntity saved = ratingService.create(toSave);
        return mapper.toDto(saved);
    }

    // ---- READ ----
    @GetMapping("/{id}")
    @Operation(
            summary = "Get rating by ID",
            description = "Retrieves a single coaster rating by its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rating found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoasterRatingDto.class),
                            examples = @ExampleObject(
                                    name = "RatingById",
                                    summary = "Rating by ID example",
                                    value = """
                                        {
                                          "id": 1001,
                                          "userId": "user-123",
                                          "coasterId": 42,
                                          "score": 5,
                                          "comment": "Amazing ride with incredible airtime!"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Rating not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public CoasterRatingDto getById(
            @Parameter(
                    description = "ID of the rating",
                    example = "1001",
                    required = true
            )
            @PathVariable Long id
    ) {
        CoasterRatingEntity entity = ratingService.getById(id);
        return mapper.toDto(entity);
    }

    @GetMapping
    @Operation(
            summary = "List all coaster ratings",
            description = "Returns all coaster ratings currently stored."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of ratings",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CoasterRatingDto.class),
                    examples = @ExampleObject(
                            name = "RatingList",
                            summary = "List of ratings example",
                            value = """
                                [
                                  {
                                    "id": 1001,
                                    "userId": "user-123",
                                    "coasterId": 42,
                                    "score": 5,
                                    "comment": "Amazing ride with incredible airtime!"
                                  },
                                  {
                                    "id": 1002,
                                    "userId": "user-456",
                                    "coasterId": 42,
                                    "score": 4,
                                    "comment": "Great but a bit rough in the back row."
                                  }
                                ]
                                """
                    )
            )
    )
    public List<CoasterRatingDto> getAll() {
        return ratingService.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    // ---- UPDATE ----
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing coaster rating",
            description = "Updates all fields of an existing rating with the provided data."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rating updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoasterRatingDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for the provided rating data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ValidationErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Rating not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = app.molby.rcrecommender.api.shared.ErrorResponse.class
                            )
                    )
            )
    })
    public CoasterRatingDto update(
            @Parameter(
                    description = "ID of the rating to update",
                    example = "1001",
                    required = true
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New state of the coaster rating",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoasterRatingDto.class),
                            examples = @ExampleObject(
                                    name = "UpdateRatingRequest",
                                    summary = "Rating update request example",
                                    value = """
                                        {
                                          "userId": "user-123",
                                          "coasterId": 42,
                                          "score": 4,
                                          "comment": "Still great, but slightly rougher than I remembered."
                                        }
                                        """
                            )
                    )
            )
            @RequestBody @Valid CoasterRatingDto dto
    ) {
        CoasterRatingEntity newState = mapper.toEntity(dto);
        CoasterRatingEntity updated = ratingService.update(id, newState);
        return mapper.toDto(updated);
    }

    // ---- DELETE ----
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a coaster rating",
            description = "Deletes the rating with the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Rating deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Rating not found",
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
                    description = "ID of the rating to delete",
                    example = "1001",
                    required = true
            )
            @PathVariable Long id
    ) {
        ratingService.delete(id);
    }
}