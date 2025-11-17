package app.molby.rcrecommender.api.country;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * CountryDto part of the roller coaster recommender application.
 *
 * <p>Represents a country within the coaster recommendation ecosystem, used
 * for identifying coaster locations and defining country accessibility
 * relationships for multi-country trip planning.</p>
 */
@Schema(
        name = "CountryDto",
        title = "Country",
        description = "DTO representing a country available in the coaster recommendation system."
)
public class CountryDto {

    /**
     * Synthetic primary key for the country record.
     */
    @Schema(
            name = "id",
            title = "Country ID",
            description = "Synthetic primary key identifying the country record.",
            example = "1"
    )
    private Long id;

    /**
     * Unique name of the country.
     */
    @Schema(
            name = "countryName",
            title = "Country Name",
            description = "Unique, human-readable name of the country.",
            example = "United States"
    )
    @NotBlank(message = "Country name cannot be blank.")
    private String countryName;
}
