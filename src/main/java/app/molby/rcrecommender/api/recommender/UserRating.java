package app.molby.rcrecommender.api.recommender;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Single user rating for a particular coaster.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {

    /**
     * Unique identifier of the roller coaster that was rated.
     */
    @JsonProperty("coaster_id")
    private Integer coasterId;

    /**
     * User's rating for the coaster on a 0–5 scale (OpenAPI: number).
     */
    private Double rating;
}
