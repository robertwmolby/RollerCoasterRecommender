package app.molby.rcrecommender.api.recommender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * RecommendedCoasterDto part of the roller coaster recommender application.
 *
 * <p>Represents a recommendation result for a single roller coaster,
 * including the coaster identifier and its computed recommendation score.
 * Scores are typically derived from similarity metrics, user preference models,
 * or ML algorithms used in the recommender engine.</p>
 *
 * <p>This DTO is returned from recommendation endpoints such as
 * {@code /recommendations/user} or {@code /recommendations/coaster/{id}},
 * and is intended to provide lightweight, easily serializable output
 * without exposing internal domain entities.</p>
 *
 * <p><strong>Example:</strong></p>
 * <pre>
 * {
 *   "coasterId": 102,
 *   "score": 0.8732
 * }
 * </pre>
 *
 * @author Bob Molby
 */
@Schema(
        name = "RecommendedCoasterDto",
        title = "Recommended Coaster",
        description = "Data transfer object representing a recommended roller coaster " +
                "and its associated recommendation score."
)
public class RecommendedCoasterDto {

    /**
     * Identifier of the recommended roller coaster.
     *
     * <p>Typically corresponds to the ID of a {@code RollerCoasterEntity}
     * record in the system. Recommendations are ordered by descending score,
     * so higher-scored coasters are considered stronger matches.</p>
     */
    @Schema(
            name = "coasterId",
            title = "Coaster ID",
            description = "Unique identifier for the recommended roller coaster.",
            example = "102"
    )
    private Long coasterId;

    /**
     * Recommendation score assigned to the coaster.
     *
     * <p>Scores are usually normalized to the range [0, 1], where higher values
     * indicate a stronger recommendation. The exact scoring mechanism depends
     * on the recommender algorithm (similarity, ML model output, etc.).</p>
     */
    @Schema(
            name = "score",
            title = "Recommendation Score",
            description = "Numeric score representing the strength of the recommendation.",
            example = "0.8732"
    )
    private Double score;
}
