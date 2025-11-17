package app.molby.rcrecommender.domain.country;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "country")
@Data
/**
 * CountryEntity JPA entity mapped to the database.
 *
 * <p>Represents a country used in the coaster recommender system for determining
 * coaster locations, accessibility mappings, and user travel planning.</p>
 * @author Bob Molby
 */
public class CountryEntity {

    /**
     * Synthetic primary key for the country record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Unique name of the country. This value is required and must not duplicate
     * any existing country record.
     */
    @Column(name = "country_name", nullable = false, unique = true, length = 100)
    private String countryName;
}
