package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.api.rating.CoasterRatingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String country;
    private Set<CoasterRatingDto> coasterRatings;
}
