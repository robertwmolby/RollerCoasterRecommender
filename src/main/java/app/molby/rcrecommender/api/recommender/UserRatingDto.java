package app.molby.rcrecommender.api.recommender;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a single rating that a user gives to a specific roller coaster.
 *
 * <p>This DTO is used as part of recommendation requests, where a user provides
 * their historical ratings so that personalized coaster recommendations can be
 * generated.</p>
 * @author Bob Molby
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "UserRatingDto",
        title = "User Roller Coaster Rating",
        description = "DTO representing a single user's rating for a particular roller coaster, " +
                      "used to build preference profiles for generating personalized recommendations.",
        example = """
                {
                  "coaster_id": 101,
                  "rating": 4.5
                }
                """
)
public class UserRatingDto {

    /**
     * Unique identifier of the roller coaster that was rated.
     */
    @Schema(
            name = "coaster_id",
            title = "Roller Coaster ID",
            description = "Unique identifier of the roller coaster that the user rated.",
            example = "101"
    )
    @JsonProperty("coaster_id")
    private Integer coasterId;

    /**
     * User's rating for the coaster on a 0.5–5 scale in increments of 0.5.
     */
    @Schema(
            name = "rating",
            title = "User Rating",
            description = "User's rating for the coaster. Valid values range from 0.5 to 5.0 " +
                          "in increments of 0.5.",
            example = "4.5"
    )
    private Double rating;
}
