package app.molby.rcrecommender.domain.coaster;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "roller_coasters")
@Data
/**
 * Represents a roller coaster entry in the database, containing metadata and
 * imputed statistics used for recommendation, analytics, and display.
 *
 * <p>This entity maps directly to the {@code roller_coasters} table and
 * includes both raw attributes (e.g., name, manufacturer) and computed or
 * normalized values (e.g., imputed speed, imputed height).
 * @author Bob Molby
 */
public class RollerCoasterEntity {

    /**
     * Primary key identifying the roller coaster record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    /**
     * Name of the roller coaster.
     */
    @Column(name = "name", length = 100, updatable = false)
    private String name;

    /**
     * Name of the amusement park where the coaster is located.
     */
    @Column(name = "amusement_park", length = 100)
    private String amusementPark;

    /**
     * General type of coaster (e.g., steel, wooden).
     */
    @Column(name = "type", length = 100)
    private String type;

    /**
     * Design classification (e.g., hypercoaster, invert).
     */
    @Column(name = "design", length = 100)
    private String design;

    /**
     * Current operational status (e.g., operating, closed).
     */
    @Column(name = "status", length = 100)
    private String status;

    /**
     * Manufacturer of the roller coaster.
     */
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    /**
     * Model or production variant of the coaster.
     */
    @Column(name = "model", length = 100)
    private String model;

    /**
     * Lengthof coaster, derived via averaging from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_length")
    private BigDecimal length;

    /**
     * Maximum height of coaster, derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_height")
    private BigDecimal height;

    /**
     * Maximum drop in feet, derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_drop")
    private BigDecimal drop;

    /**
     * Number of inversions, derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_inversion_count")
    private BigDecimal inversionCount;

    /**
     * Top speed, derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_speed")
    private BigDecimal speed;

    /**
     * Vertical angle after lift, derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_vertical_angle")
    private BigDecimal verticalAngle;

    /**
     * Type of user restraints., derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_restraints", length = 100)
    private String restraints;

    /**
     * Maximum g-Force, derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_g_force")
    private BigDecimal gForce;

    /**
     * Generalized intensity (family, thrill, extreme).  Derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_intensity", length = 100)
    private String intensity;

    /**
     * Length of ride.  Derived from other similar coasters if not
     * explicitly available on source information.
     */
    @Column(name = "imputed_duration")
    private BigDecimal duration;

    /**
     * Country where the coaster is located.
     */
    @Column(name = "country", length = 400)
    private String country;

    /**
     * Average user rating of the coaster based on user rating information.
     * If specific ratings were not made for coaster, ratings are then created
     * by progressively using higher level information (e.g., manufacturer, type)
     */
    @Column(name = "average_rating", precision = 5, scale = 2, updatable = false)
    private BigDecimal averageRating;
}