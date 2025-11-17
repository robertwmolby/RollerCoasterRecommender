package app.molby.rcrecommender.api.recommender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * RecommendedCoasterDto part of the roller coaster recommender application.
 * @author Bob Molby
 */
public class RecommendedCoasterDto {
    private Long coasterId;
    private Double score;
}
