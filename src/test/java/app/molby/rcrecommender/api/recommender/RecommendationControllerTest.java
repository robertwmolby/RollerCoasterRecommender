package app.molby.rcrecommender.api.recommender;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    @Test
    @DisplayName("GET /api/recommendations/{userId} returns a list of recommendations")
    void findRecommendations_returnsRecommendations() throws Exception {
        // Arrange
        String userId = "jean_luc_picard";

        CoasterRecommendation rec1 = new CoasterRecommendation();
        rec1.setCoasterId(1);
        // If CoasterRecommendation has more fields (coaster, score, reason, etc.),
        // set them here as needed, e.g.:
        // rec1.setScore(0.94);
        // rec1.setReason("Matches your preference ...");

        CoasterRecommendation rec2 = new CoasterRecommendation();
        rec2.setCoasterId(2);
        // rec2.setScore(0.88);
        // rec2.setReason("Similar riders to you loved this ride.");

        List<CoasterRecommendation> recommendations = List.of(rec1, rec2);

        given(recommendationService.getRecommendationsForUser(userId))
                .willReturn(recommendations);

        // Act & Assert
        mockMvc.perform(
                        get("/api/recommendations/{userId}", userId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print())
                .andExpect(jsonPath("$[0].coaster_id", is(1)))
                .andExpect(jsonPath("$[1].coaster_id", is(2)));

        // Verify service interaction
        verify(recommendationService).getRecommendationsForUser(userId);
    }
}
