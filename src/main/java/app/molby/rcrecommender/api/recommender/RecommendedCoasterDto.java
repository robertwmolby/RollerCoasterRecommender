package app.molby.rcrecommender.api.recommender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedCoasterDto {
    private Long coasterId;
    private Double score;
}
