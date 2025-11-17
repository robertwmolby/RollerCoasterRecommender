package app.molby.rcrecommender.api.rating;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * CoasterRatingDto part of the roller coaster recommender application.
 *
 * <p>Represents a user's numeric rating for a specific roller coaster.
 * Each user may submit at most one rating per coaster, and ratings are
 * used to build user preference profiles for personalized recommendations.</p>
 */
@Schema(
        name = "CoasterRatingDto",
        title = "Roller Coaster Rating",
        description = "DTO representing a user's numeric rating for a specific roller coaster."
)
public class CoasterRatingDto {

    /**
     * Synthetic primary key for the roller coaster rating record.
     */
    @Schema(
            name = "id",
            title = "Rating ID",
            description = "Synthetic primary key identifying the rating record.",
            example = "2001"
    )
    private Long id;

    /**
     * Identifier of the user who submitted the rating.
     */
    @Schema(
            name = "userId",
            title = "User ID",
            description = "Identifier of the user who created the rating.",
            example = "501"
    )
    private String userId;

    /**
     * Identifier of the coaster that is being rated.
     */
    @Schema(
            name = "coasterId",
            title = "Roller Coaster ID",
            description = "Identifier of the roller coaster being rated.",
            example = "102"
    )
    private Long coasterId;

    /**
     * Numeric rating assigned by the user for the given coaster.
     *
     * <p>Rage of 0.5-5 in 0.5 increments</p>
     */
    @Schema(
            name = "rating",
            title = "Rating Value",
            description = "Numeric rating submitted by the user. Possible values range from " +
                    "0.5-5 in 0.5 increments",
            example = "4"
    )
    private BigDecimal rating;
}
