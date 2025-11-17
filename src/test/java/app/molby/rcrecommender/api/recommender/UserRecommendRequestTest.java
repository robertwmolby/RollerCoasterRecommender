package app.molby.rcrecommender.api.recommender;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRecommendRequestTest {

    @Test
    void noArgsConstructor_createsObjectWithNullFields() {
        UserRecommendRequest request = new UserRecommendRequest();

        assertNull(request.getCountries());
        assertNull(request.getRatings());
        assertNull(request.getTopK());
    }

    @Test
    void allArgsConstructor_setsAllFields() {
        List<String> countries = List.of("United States", "Canada");
        List<UserRatingDto> ratings = List.of(
                new UserRatingDto(101, 5.0),
                new UserRatingDto(202, 3.5)
        );
        Integer topK = 10;

        UserRecommendRequest request = new UserRecommendRequest(countries, ratings, topK);

        assertEquals(countries, request.getCountries());
        assertEquals(ratings, request.getRatings());
        assertEquals(topK, request.getTopK());
    }

    @Test
    void settersAndGetters_workAsExpected() {
        UserRecommendRequest request = new UserRecommendRequest();

        List<String> countries = List.of("Germany");
        List<UserRatingDto> ratings = List.of(new UserRatingDto(1, 4.0));
        Integer topK = 5;

        request.setCountries(countries);
        request.setRatings(ratings);
        request.setTopK(topK);

        assertEquals(countries, request.getCountries());
        assertEquals(ratings, request.getRatings());
        assertEquals(topK, request.getTopK());
    }

    @Test
    void equalsAndHashCode_basedOnAllFields() {
        List<String> countries = List.of("US");
        List<UserRatingDto> ratings = List.of(new UserRatingDto(10, 3.0));
        Integer topK = 3;

        UserRecommendRequest r1 = new UserRecommendRequest(countries, ratings, topK);
        UserRecommendRequest r2 = new UserRecommendRequest(countries, ratings, topK);
        UserRecommendRequest r3 = new UserRecommendRequest(List.of("CA"), ratings, topK);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        assertNotEquals(r1, r3);
        assertNotEquals(r1.hashCode(), r3.hashCode());
    }

    @Test
    void toString_containsFieldValues() {
        List<String> countries = List.of("Japan");
        List<UserRatingDto> ratings = List.of(new UserRatingDto(99, 4.5));
        Integer topK = 7;

        UserRecommendRequest request = new UserRecommendRequest(countries, ratings, topK);

        String s = request.toString();

        assertTrue(s.contains("Japan"));
        assertTrue(s.contains("99"));
        assertTrue(s.contains("4.5"));
        assertTrue(s.contains("7"));
    }
}