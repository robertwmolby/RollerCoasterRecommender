package app.molby.rcrecommender.domain.country;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "country_access")
@Data
/**
 * CountryAccessEntity JPA entity mapped to the database. Represents
 * countries that can be easily accessed for each other for the purpose
 * of making a coaster trip
 * @author Bob Molby
 */
public class CountryAccessEntity {

    /**
     * Synthetic primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Source country (home country of the user)
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_country_id", nullable = false)
    private CountryEntity sourceCountry;

    /**
     * Countries accessible for coaster consideration
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accessible_country_id", nullable = false)
    private CountryEntity accessibleCountry;
}
