package app.molby.rcrecommender.domain.country;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "country")
@Data
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // synthetic PK
    @Column(name = "id")
    private Long id;

    @Column(name = "country_name", nullable = false, unique = true, length = 100)
    private String countryName;
}