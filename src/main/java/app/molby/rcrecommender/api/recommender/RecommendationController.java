package app.molby.rcrecommender.api.recommender;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
@Tag(
        name = "Recommendations",
        description = "Endpoints for generating roller coaster recommendations for users."
)
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    @Operation(
            summary = "Get coaster recommendations for a user",
            description = """
                    Returns a list of recommended roller coasters for the specified user.
                    The recommendation engine may use the user's historical ratings and country access
                    (provided separately) to generate the list.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of recommendations for the user",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CoasterRecommendation.class),
                    examples = {
                            @ExampleObject(
                                    name = "RecommendationsExample",
                                    summary = "Typical recommendation result",
                                    value = """
                                        [
                                          {
                                            "userId": "user-123",
                                            "coaster": {
                                              "id": 42,
                                              "name": "Thunderbolt",
                                              "park": "Adventure World",
                                              "country": "United States",
                                              "heightMeters": 60.5,
                                              "maxSpeedKph": 120,
                                              "inversions": 3,
                                              "launch": true
                                            },
                                            "score": 0.94,
                                            "reason": "Matches your preference for tall, fast coasters with inversions."
                                          },
                                          {
                                            "userId": "user-123",
                                            "coaster": {
                                              "id": 37,
                                              "name": "Sky Serpent",
                                              "park": "Coaster Kingdom",
                                              "country": "Canada",
                                              "heightMeters": 49.0,
                                              "maxSpeedKph": 95,
                                              "inversions": 1,
                                              "launch": false
                                            },
                                            "score": 0.88,
                                            "reason": "Similar riders to you loved this ride."
                                          }
                                        ]
                                        """
                            )
                    }
            )
    )
    public List<CoasterRecommendation> getRecommendations(
            @Parameter(
                    description = "User identifier for which recommendations are requested",
                    example = "user-123",
                    required = true
            )
            @PathVariable String userId
    ) {
        return recommendationService.getRecommendationsForUser(userId);
    }
}