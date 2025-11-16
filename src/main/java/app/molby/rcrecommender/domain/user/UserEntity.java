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
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email_address", nullable = false, unique = true, length = 200)
    private String emailAddress;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Where(clause="roller_coaster_id is not null")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CoasterRatingEntity> coasterRatings = new HashSet<>();
}
