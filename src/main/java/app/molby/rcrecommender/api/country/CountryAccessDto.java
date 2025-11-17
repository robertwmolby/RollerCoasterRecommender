package app.molby.rcrecommender.api.country;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * CountryAccessDto part of the roller coaster recommender application.
 *
 * <p>Represents a relationship between two countries indicating that one
 * country can be easily accessed from another for the purpose of coaster
 * trip planning and multi-country coaster recommendations.</p>
 */
@Schema(
        name = "CountryAccessDto",
        title = "Country Access Mapping",
        description = "DTO representing a directional access relationship between two countries, " +
                "used to determine coaster trip feasibility across borders."
)
public class CountryAccessDto {

    /**
     * Synthetic primary key.
     */
    @Schema(
            name = "id",
            title = "Country Access ID",
            description = "Synthetic primary key for the country access record.",
            example = "42"
    )
    private Long id;

    /**
     * Source country (home country of the user).
     */
    @Schema(
            name = "sourceCountry",
            title = "Source Country",
            description = "The originating country from which accessibility to another country is evaluated.",
            implementation = CountryDto.class
    )
    private CountryDto sourceCountry;

    /**
     * Countries accessible for coaster consideration.
     */
    @Schema(
            name = "accessibleCountry",
            title = "Accessible Country",
            description = "A country considered geographically or logistically accessible from the source country.",
            implementation = CountryDto.class
    )
    private CountryDto accessibleCountry;
}
