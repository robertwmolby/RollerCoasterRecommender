package app.molby.rcrecommender.api.recommender;

import app.molby.rcrecommender.api.user.UserNotFoundException;
import app.molby.rcrecommender.domain.country.CountryAccessEntity;
import app.molby.rcrecommender.domain.country.CountryAccessRepository;
import app.molby.rcrecommender.domain.country.CountryEntity;
import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import app.molby.rcrecommender.domain.user.UserEntity;
import app.molby.rcrecommender.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
/**
 * Service responsible for orchestrating user-specific coaster recommendations.
 *
 * <p>This service loads user ratings from the database, formats them into the
 * request structure required by the external Python recommendation engine,
 * invokes the engine, and returns the resulting list of recommendations.</p>
 * @author Bob Molby
 */
public class RecommendationService {

    private final UserRepository userRepository;
    private final CountryAccessRepository countryAccessRepository;
    private final RestTemplate restTemplate;

    @Value("${recommender.api.url}")
    private String recommenderApiUrl;

    @Value("${recommender.api.default-top-k:20}")
    private int defaultTopK;

    /**
     * Generates a ranked list of roller coaster recommendations for a user.
     *
     * <p>The process performs the following steps:</p>
     * <ol>
     *   <li>Loads the user's coaster ratings from the database.</li>
     *   <li>If the user has no ratings, returns an empty list.</li>
     *   <li>Builds a recommendation request for the Python engine.</li>
     *   <li>Sends the request to the external recommender API.</li>
     *   <li>Returns the engine's results or an empty list if the response is empty.</li>
     * </ol>
     *
     * @param userId the identifier of the user whose recommendations are requested
     * @return a list of {@link CoasterRecommendation} objects ranked by relevance
     */
    public List<CoasterRecommendation> getRecommendationsForUser(String userId) {
        // 1) Load user ratings from DB
        Optional<UserEntity> optUserEntity = userRepository.findById(userId);
        if (!optUserEntity.isPresent()) {
            throw new UserNotFoundException(userId);
        }
        UserEntity user = optUserEntity.get();
        Set<CoasterRatingEntity> ratingEntities = user.getCoasterRatings();
        if (ratingEntities.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) Build request body for Python API
        UserRecommendRequest request = buildUserRecommendRequest(user, ratingEntities);

        // 3) Call Python recommender
        ResponseEntity<CoasterRecommendation[]> response =
                restTemplate.postForEntity(
                        recommenderApiUrl,
                        request,
                        CoasterRecommendation[].class
                );

        CoasterRecommendation[] body = response.getBody();
        if (body == null || body.length == 0) {
            return Collections.emptyList();
        }

        return Arrays.asList(body);
    }

    /**
     * Builds a request to be sent to the Python recommendation engine.
     *
     * <p>Converts user rating entities into the simplified structure the
     * external service expects, and includes a placeholder list of allowed
     * countries associated to the users country.</p>
     *
     * @param user User object for country information
     * @param ratingEntities the set of rating records associated with the user
     *
     * @return a fully populated {@link UserRecommendRequest}
     */
    private UserRecommendRequest buildUserRecommendRequest(UserEntity user, Set<CoasterRatingEntity> ratingEntities) {

        List<String> countries = new ArrayList<>(List.of(user.getCountry()));
        List<CountryAccessEntity> associatedCountries = countryAccessRepository.findBySourceCountry(user.getCountry());
        countries.addAll(associatedCountries.stream().map(CountryAccessEntity::getAccessibleCountry).map(CountryEntity::getCountryName).toList());
        List<UserRatingDto> ratings = ratingEntities.stream()
                .map(e -> new UserRatingDto(
                        e.getCoasterId().intValue(),      // coaster_id
                        e.getRating().doubleValue()       // rating (0–5)
                ))
                .collect(Collectors.toList());

        UserRecommendRequest request = new UserRecommendRequest();
        request.setCountries(countries);
        request.setRatings(ratings);
        request.setTopK(defaultTopK);

        return request;
    }
}
