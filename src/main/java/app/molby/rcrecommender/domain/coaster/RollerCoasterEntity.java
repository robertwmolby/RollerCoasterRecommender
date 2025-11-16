package app.molby.rcrecommender.domain.coaster;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "roller_coasters")
@Data
public class RollerCoasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // If DB handles it; remove if not auto
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "amusement_park", length = 100)
    private String amusementPark;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "design", length = 100)
    private String design;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "imputed_length")
    private BigDecimal length;

    @Column(name = "imputed_height")
    private BigDecimal height;

    @Column(name = "imputed_drop")
    private BigDecimal drop;

    @Column(name = "imputed_inversion_count")
    private BigDecimal inversionCount;

    @Column(name = "imputed_speed")
    private BigDecimal speed;

    @Column(name = "imputed_vertical_angle")
    private BigDecimal verticalAngle;

    @Column(name = "imputed_restraints", length = 100)
    private String restraints;

    @Column(name = "imputed_g_force")
    private BigDecimal gForce;

    @Column(name = "imputed_intensity", length = 100)
    private String intensity;

    @Column(name = "imputed_duration")
    private BigDecimal duraction;

    @Column(name = "country", length = 400)
    private String country;

    @Column(name = "average_rating", precision = 5, scale = 2)
    private BigDecimal averageRating;

}
