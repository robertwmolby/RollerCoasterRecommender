package app.molby.rcrecommender.api.recommender;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import app.molby.rcrecommender.domain.user.UserEntity;
import app.molby.rcrecommender.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendationService subject;

    @Test
    void subjectIsCreated() {
        assertNotNull(subject);
    }

    @Test
    void getRecommendationsForUser_returnsEmptyList_whenUserHasNoRatings() {
        String userId = "user-1";
        UserEntity user = mock(UserEntity.class);

        when(user.getCoasterRatings()).thenReturn(Collections.emptySet());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        List<CoasterRecommendation> result = subject.getRecommendationsForUser(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository).findById(userId);
        verifyNoInteractions(restTemplate);
    }

    @Test
    void getRecommendationsForUser_buildsRequest_callsRecommenderAndReturnsResults() {
        String userId = "jean_luc_picard";

        UserEntity user = mock(UserEntity.class);
        CoasterRatingEntity rating1 = mock(CoasterRatingEntity.class);
        CoasterRatingEntity rating2 = mock(CoasterRatingEntity.class);

        when(rating1.getCoasterId()).thenReturn(42L);
        when(rating1.getRating()).thenReturn(new BigDecimal("4.5"));
        when(rating2.getCoasterId()).thenReturn(7L);
        when(rating2.getRating()).thenReturn(new BigDecimal("3.0"));

        Set<CoasterRatingEntity> ratings = new HashSet<>(Arrays.asList(rating1, rating2));
        when(user.getCoasterRatings()).thenReturn(ratings);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        CoasterRecommendation rec1 = new CoasterRecommendation();
        rec1.setCoasterId(42);
        CoasterRecommendation rec2 = new CoasterRecommendation();
        rec2.setCoasterId(7);

        CoasterRecommendation[] body = new CoasterRecommendation[]{rec1, rec2};
        ResponseEntity<CoasterRecommendation[]> response =
                new ResponseEntity<>(body, HttpStatus.OK);

        ArgumentCaptor<UserRecommendRequest> requestCaptor =
                ArgumentCaptor.forClass(UserRecommendRequest.class);

        when(restTemplate.postForEntity(
                anyString(),
                any(UserRecommendRequest.class),
                eq(CoasterRecommendation[].class))
        ).thenReturn(response);

        // Ensure defaultTopK has a deterministic value
        ReflectionTestUtils.setField(subject, "defaultTopK", 5);

        List<CoasterRecommendation> result = subject.getRecommendationsForUser(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(42, result.get(0).getCoasterId());
        assertEquals(7, result.get(1).getCoasterId());

        verify(userRepository).findById(userId);
        verify(restTemplate).postForEntity(
                anyString(),
                requestCaptor.capture(),
                eq(CoasterRecommendation[].class)
        );
        verifyNoMoreInteractions(userRepository, restTemplate);

        UserRecommendRequest sentRequest = requestCaptor.getValue();
        assertNotNull(sentRequest);
        assertEquals(1, sentRequest.getCountries().size());
        assertEquals("United States", sentRequest.getCountries().getFirst());
        assertEquals(2, sentRequest.getRatings().size());
        assertEquals(5, sentRequest.getTopK());
    }

    @Test
    void getRecommendationsForUser_returnsEmptyList_whenRecommenderResponseBodyIsNull() {
        String userId = "user-2";

        UserEntity user = mock(UserEntity.class);
        CoasterRatingEntity rating = mock(CoasterRatingEntity.class);

        when(rating.getCoasterId()).thenReturn(10L);
        when(rating.getRating()).thenReturn(new BigDecimal("4.0"));

        Set<CoasterRatingEntity> ratings = Set.of(rating);
        when(user.getCoasterRatings()).thenReturn(ratings);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<CoasterRecommendation[]> response =
                new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.postForEntity(
                anyString(),
                any(UserRecommendRequest.class),
                eq(CoasterRecommendation[].class))
        ).thenReturn(response);

        List<CoasterRecommendation> result = subject.getRecommendationsForUser(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository).findById(userId);
        verify(restTemplate).postForEntity(
                anyString(),
                any(UserRecommendRequest.class),
                eq(CoasterRecommendation[].class)
        );
        verifyNoMoreInteractions(userRepository, restTemplate);
    }
}
