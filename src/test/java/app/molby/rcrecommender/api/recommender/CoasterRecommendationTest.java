package app.molby.rcrecommender.api.recommender;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoasterRecommendationTest {

    @Test
    void noArgsConstructor_initializesWithNullCoasterIdAndEmptyAdditionalProperties() {
        CoasterRecommendation recommendation = new CoasterRecommendation();

        assertNull(recommendation.getCoasterId());
        assertNotNull(recommendation.getAdditionalProperties());
        assertTrue(recommendation.getAdditionalProperties().isEmpty());
    }

    @Test
    void setterAndGetter_forCoasterId_workAsExpected() {
        CoasterRecommendation recommendation = new CoasterRecommendation();

        recommendation.setCoasterId(42);

        assertEquals(42, recommendation.getCoasterId());
    }

    @Test
    void addAdditionalProperty_addsEntryToAdditionalProperties() {
        CoasterRecommendation recommendation = new CoasterRecommendation();

        recommendation.addAdditionalProperty("name", "Blue Fire");
        recommendation.addAdditionalProperty("park", "Europa-Park");

        Map<String, Object> props = recommendation.getAdditionalProperties();
        assertEquals(2, props.size());
        assertEquals("Blue Fire", props.get("name"));
        assertEquals("Europa-Park", props.get("park"));
    }

    @Test
    void addAdditionalProperty_ignoresCoasterIdKey() {
        CoasterRecommendation recommendation = new CoasterRecommendation();

        recommendation.setCoasterId(7);
        recommendation.addAdditionalProperty("coaster_id", 999);

        assertEquals(7, recommendation.getCoasterId());
        assertTrue(recommendation.getAdditionalProperties().isEmpty());
    }

    @Test
    void getAdditionalProperties_returnsLiveMap() {
        CoasterRecommendation recommendation = new CoasterRecommendation();

        Map<String, Object> props = recommendation.getAdditionalProperties();
        props.put("score", 9.5);

        assertEquals(1, recommendation.getAdditionalProperties().size());
        assertEquals(9.5, recommendation.getAdditionalProperties().get("score"));
    }
}
