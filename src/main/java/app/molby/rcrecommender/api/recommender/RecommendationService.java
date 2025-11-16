package app.molby.rcrecommender.api.recommender;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import app.molby.rcrecommender.domain.rating.CoasterRatingRepository;
import app.molby.rcrecommender.domain.user.UserEntity;
import app.molby.rcrecommender.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

//    @Value("${recommender.api.url}")
    private String recommenderApiUrl = "http://ac3a45cc0862c4debaeed73d6650680d-1292001656.us-east-2.elb.amazonaws.com/recommendations/user";  // e.g. https://.../recommendations/user

    @Value("${recommender.api.default-top-k:20}")
    private int defaultTopK;

    public List<CoasterRecommendation> getRecommendationsForUser(String userId) {
        // 1) Load user ratings from DB
        UserEntity user = userRepository.findById(userId).get();
        Set<CoasterRatingEntity> ratingEntities = user.getCoasterRatings();
        if (ratingEntities.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) Build request body for Python API
        UserRecommendRequest request = buildUserRecommendRequest(ratingEntities);

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

    private UserRecommendRequest buildUserRecommendRequest(Set<CoasterRatingEntity> ratingEntities) {
        // TODO later: derive this from CountryAccess / User, etc.
        List<String> countries = List.of("United States");

        List<UserRating> ratings = ratingEntities.stream()
                .map(e -> new UserRating(
                        e.getCoasterId().intValue(),   // coaster_id
                        e.getRating().doubleValue()                // rating (0–5)
                ))
                .collect(Collectors.toList());

        UserRecommendRequest request = new UserRecommendRequest();
        request.setCountries(countries);
        request.setRatings(ratings);
        request.setTopK(defaultTopK);

        return request;
    }
}
