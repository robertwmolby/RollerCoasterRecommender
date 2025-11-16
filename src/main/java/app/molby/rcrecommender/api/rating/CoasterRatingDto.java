package app.molby.rcrecommender.api.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoasterRatingDto {
    private Long id;
    private Long userId;
    private Long coasterId;
    private BigDecimal rating;
}
