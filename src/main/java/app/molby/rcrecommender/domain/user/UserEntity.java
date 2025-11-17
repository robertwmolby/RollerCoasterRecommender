package app.molby.rcrecommender.domain.user;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Data
/**
 * JPA entity representing a user in the roller coaster recommender system.
 *
 * <p>Holds core identity and profile information along with the set of coaster
 * ratings submitted by the user. This entity is persisted in the
 * <strong>app_user</strong> table. Coaster ratings are lazily loaded and
 * filtered to include only valid rating rows.</p>
 * @author Bob Molby
 */
public class UserEntity {

    /**
     * Unique identifier for the user.
     *
     * <p>This value is used as the primary key for the {@code app_user} table.
     * It may be an externally generated value such as a UUID.</p>
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * User’s email address.
     *
     * <p>Must be unique across all users and is stored in the
     * {@code email_address} column.</p>
     */
    @Column(name = "email_address", nullable = false, unique = true, length = 200)
    private String emailAddress;

    /**
     * User’s first (given) name.
     */
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    /**
     * User’s last (family) name.
     */
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    /**
     * ISO 3166-1 alpha-2 country code indicating the user’s country.
     */
    @Column(name = "country", nullable = false, length = 50)
    private String country;

    /**
     * Set of coaster ratings submitted by this user.
     *
     * <p>This is a one-to-many relationship mapped via the foreign key on the
     * {@code user_id} column in the coaster rating table. Ratings are:</p>
     * <ul>
     *   <li><strong>Lazy-loaded</strong> to avoid unnecessary queries</li>
     *   <li><strong>Cascaded</strong> so changes propagate to the child entities</li>
     *   <li><strong>Orphan-removal enabled</strong>, so removed ratings are deleted</li>
     *   <li><strong>Filtered</strong> with a {@code @Where} clause ensuring only
     *       rows with a non-null {@code roller_coaster_id} are included</li>
     * </ul>
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Where(clause = "roller_coaster_id is not null")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CoasterRatingEntity> coasterRatings = new HashSet<>();
}
