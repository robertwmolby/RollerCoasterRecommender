package app.molby.rcrecommender.api.coaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RollerCoasterDto {
    private Long id;
    private String name;
    private String amusementPark;
    private String country;
}
