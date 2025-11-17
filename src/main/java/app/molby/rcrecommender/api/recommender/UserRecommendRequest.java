package app.molby.rcrecommender.api.recommender;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "UserRecommendRequest",
        description = "Request body used to compute personalized roller coaster recommendations for a user.",
        example = """
            {
              "countries": ["United States", "Canada"],
              "ratings": [
                {
                  "coasterId": 101,
                  "score": 5
                },
                {
                  "coasterId": 202,
                  "score": 3
                }
              ],
              "top_k": 5
            }
            """
)
/**
 * UserRecommendRequest part of the roller coaster recommender application.
 * @author Bob Molby
 */
public class UserRecommendRequest {

    /**
     * List of country names the user has access to
     * (e.g. "United States", "Canada").
     */
    @Schema(
            description = "List of country names the user has access to.",
            example = "[\"United States\", \"Canada\"]",
            nullable = true
    )
    private List<String> countries;

    /**
     * List of coasters the user has rated.
     */
    @Schema(
            description = "User's existing ratings for coasters, used as input for the recommender.",
            example = """
                [
                  {
                    "coasterId": 101,
                    "score": 5
                  },
                  {
                    "coasterId": 202,
                    "score": 3
                  }
                ]
                """,
            nullable = true
    )
    private List<UserRatingDto> ratings;

    /**
     * Maximum number of recommendations to return.
     * Maps to JSON field "top_k".
     */
    @JsonProperty("top_k")
    @Schema(
            description = "Maximum number of recommendations to return.",
            example = "10",
            nullable = true
    )
    private Integer topK;
}
