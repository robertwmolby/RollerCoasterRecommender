package app.molby.rcrecommender.domain.rating;

import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(
        name = "user_roller_coaster_ratings"
)
@Data
/**
 * CoasterRatingEntity JPA entity mapped to the database.
 *
 * <p>Represents a user's numeric rating for a specific roller coaster.
 * Each user may rate a given coaster at most once, enforced by a unique
 * constraint on the {@code user_id} and {@code coaster_id} columns.</p>
 * @author Bob Molby
 */
public class CoasterRatingEntity {

    /**
     * Synthetic primary key for the roller coaster rating record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;


    /**
     * Identifier of the user who submitted the rating.
     *
     * <p>Corresponds to the ID of a user in the system. Ratings are grouped
     * and queried by this value when generating user-specific analytics
     * or recommendations.</p>
     */
    @Column(name = "user_id", updatable = false)
    private String userId;

    /**
     * Identifier of the coaster that is being rated.
     *
     * <p>Maps to a {@link RollerCoasterEntity} via the coaster ID, enabling
     * association between ratings and coaster metadata.</p>
     */
    @Column(name = "roller_coaster_id", updatable = false)
    private Long coasterId;

    /**
     * Numeric rating assigned by the user for the given coaster.
     *
     * <p>The rating uses a precision of 3 with a scale of 2 (e.g., 4.75), and
     * must always contain a value. Typically ranges are validated at the DTO
     * or service layer (e.g., 0.00 to 5.00).</p>
     */
    @Column(name = "rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal rating;
}
