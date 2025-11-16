package app.molby.rcrecommender.domain.rating;

import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(
        name = "user_roller_coaster_ratings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_coaster_rating_user_coaster",
                        columnNames = {"user_id", "coaster_id"}
                )
        }
)
@Data
public class CoasterRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "roller_coaster_id")
    private Long coasterId;
    @Column(name = "rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal rating;
}
