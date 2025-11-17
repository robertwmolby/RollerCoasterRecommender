package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.api.rating.CoasterRatingDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

/**
 * UserDto part of the roller coaster recommender application.
 *
 * <p>Represents a user of the system, including identity details,
 * contact information, location, and any coaster ratings they have
 * submitted.</p>
 * @author Bob Molby
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "UserDto",
        title = "User",
        description = "DTO representing a system user, including profile information and their associated roller coaster ratings.",
        example = """
        {
          "id": "jean_luc_picard",
          "emailAddress": "jean.luc.picard@uss.enterprise.org",
          "firstName": "Jean-Luc",
          "lastName": "Picard",
          "country": "United States",
          "coasterRatings": [
            {
              "id": 2001,
              "userId": "jean_luc_picard",
              "coasterId": 101,
              "rating": 4.5
            }
          ]
        }
        """
)
public class UserDto {

    /**
     * Unique identifier for the user.
     */
    @Schema(
            title = "User ID",
            description = "Unique identifier used to reference the user.",
            example = "jean_luc_picard"
    )
    private String id;

    /**
     * User's email address.
     */
    @Schema(
            title = "Email Address",
            description = "Email address used to identify or contact the user.",
            example = "alice@example.com"
    )
    private String emailAddress;

    /**
     * User's first (given) name.
     */
    @Schema(
            title = "First Name",
            description = "User's given name.",
            example = "Alice"
    )
    private String firstName;

    /**
     * User's last (family) name.
     */
    @Schema(
            title = "Last Name",
            description = "User's family name.",
            example = "Anderson"
    )
    private String lastName;

    /**
     * ISO 3166-1 alpha-2 country code representing the user's country.
     */
    @Schema(
            title = "Country Code",
            description = "ISO 3166-1 alpha-2 country code representing the user's country of residence.",
            example = "US"
    )
    private String country;

    /**
     * All roller coaster ratings submitted by the user.
     */
    @Schema(
            title = "Coaster Ratings",
            description = "Set of roller coaster ratings that the user has submitted. May be empty.",
            example = """
            [
              {
                "id": 2001,
                "userId": "jean_luc_picard",
                "coasterId": 101,
                "rating": 4.5
              }
            ]
            """
    )
    private Set<CoasterRatingDto> coasterRatings;
}
