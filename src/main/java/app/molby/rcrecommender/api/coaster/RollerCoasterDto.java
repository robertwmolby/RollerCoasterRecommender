package app.molby.rcrecommender.api.coaster;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * RollerCoasterDto part of the roller coaster recommender application.
 */
@Schema(
        name = "RollerCoasterDto",
        title = "Roller Coaster",
        description = "Data transfer object representing a roller coaster and its metadata, "
                + "including both source attributes and imputed statistical values."
)
public class RollerCoasterDto {

    /**
     * Primary key identifying the roller coaster record.
     */
    @Schema(
            name = "id",
            title = "Roller Coaster ID",
            description = "Primary key identifying the roller coaster record.",
            example = "101"
    )
    private Long id;

    /**
     * Name of the roller coaster.
     */
    @Schema(
            name = "name",
            title = "Coaster Name",
            description = "Name of the roller coaster.",
            example = "Millennium Force"
    )
    private String name;

    /**
     * Name of the amusement park where the coaster is located.
     */
    @Schema(
            name = "amusementPark",
            title = "Amusement Park",
            description = "Name of the amusement park where the coaster is located.",
            example = "Cedar Point"
    )
    private String amusementPark;

    /**
     * General type of coaster (e.g., steel, wooden).
     */
    @Schema(
            name = "type",
            title = "Coaster Type",
            description = "General type of coaster (e.g., steel, wooden).",
            example = "Steel"
    )
    private String type;

    /**
     * Design classification (e.g., sitdown, wing).
     */
    @Schema(
            name = "design",
            title = "Design Class",
            description = "Design classification (e.g., Sitdown, Wing).",
            example = "Wing"
    )
    private String design;

    /**
     * Current operational status (e.g., operating, closed).
     */
    @Schema(
            name = "status",
            title = "Operational Status",
            description = "Current operational status (e.g., operating, closed).",
            example = "Operating"
    )
    private String status;

    /**
     * Manufacturer of the roller coaster.
     */
    @Schema(
            name = "manufacturer",
            title = "Manufacturer",
            description = "Manufacturer of the roller coaster.",
            example = "Intamin"
    )
    private String manufacturer;

    /**
     * Model or production variant of the coaster.
     */
    @Schema(
            name = "model",
            title = "Model",
            description = "Model or production variant of the coaster.",
            example = "Giga Coaster Model"
    )
    private String model;

    /**
     * Length of coaster, derived via averaging from other similar coasters if not explicitly available.
     */
    @Schema(
            name = "length",
            title = "Imputed Length",
            description = "Track length of the coaster in feet. Derived if not explicitly available.",
            example = "3000"
    )
    private BigDecimal length;

    /**
     * Maximum height of coaster, derived if not explicitly available.
     */
    @Schema(
            name = "height",
            title = "Imputed Height",
            description = "Maximum height in feet. Derived if not explicitly available.",
            example = "310"
    )
    private BigDecimal height;

    /**
     * Maximum drop in feet, derived if not explicitly available.
     */
    @Schema(
            name = "drop",
            title = "Imputed Drop",
            description = "Maximum drop in feet. Derived if not explicitly available.",
            example = "300"
    )
    private BigDecimal drop;

    /**
     * Number of inversions, derived if not explicitly available.
     */
    @Schema(
            name = "inversionCount",
            title = "Inversion Count",
            description = "Number of inversions. Derived if not explicitly available.",
            example = "0"
    )
    private BigDecimal inversionCount;

    /**
     * Top speed, derived if not explicitly available.
     */
    @Schema(
            name = "speed",
            title = "Top Speed",
            description = "Top speed in mph. Derived if not explicitly available.",
            example = "93"
    )
    private BigDecimal speed;

    /**
     * Vertical angle after lift, derived if not explicitly available.
     */
    @Schema(
            name = "verticalAngle",
            title = "Vertical Angle",
            description = "Vertical descent angle. Derived if not explicitly available.",
            example = "80"
    )
    private BigDecimal verticalAngle;

    /**
     * Type of user restraints., derived if not explicitly available.
     */
    @Schema(
            name = "restraints",
            title = "Restraint Type",
            description = "Type of rider restraints. Derived if not explicitly available.",
            example = "Lap Bar"
    )
    private String restraints;

    /**
     * Maximum g-Force, derived if not explicitly available.
     */
    @Schema(
            name = "gForce",
            title = "Maximum G-Force",
            description = "Maximum G-force experienced. Derived if not explicitly available.",
            example = "2.1"
    )
    private BigDecimal gForce;

    /**
     * Generalized intensity (family, thrill, extreme). Derived if not explicitly available.
     */
    @Schema(
            name = "intensity",
            title = "Ride Intensity",
            description = "Generalized intensity category (e.g., Family, Thrill, Extreme).",
            example = "Thrill"
    )
    private String intensity;

    /**
     * Length of ride. Derived if not explicitly available.
     */
    @Schema(
            name = "duration",
            title = "Ride Duration",
            description = "Ride duration in seconds. Derived if not explicitly available.",
            example = "150.0"
    )
    private BigDecimal duration;

    /**
     * Country where the coaster is located.
     */
    @Schema(
            name = "country",
            title = "Country",
            description = "Country where the coaster is located.",
            example = "United States"
    )
    private String country;

    /**
     * Average user rating of the coaster, derived using fallback metadata.
     */
    @Schema(
            name = "averageRating",
            title = "Average Rating",
            description = "Average user rating, using fallback data if coaster-specific ratings are unavailable.",
            example = "4.75"
    )
    private BigDecimal averageRating;
}
