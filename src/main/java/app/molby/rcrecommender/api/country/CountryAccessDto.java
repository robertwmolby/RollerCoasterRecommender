package app.molby.rcrecommender.api.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryAccessDto {
    private Long id;
    private CountryDto sourceCountry;
    private CountryDto accessibleCountry;
}
