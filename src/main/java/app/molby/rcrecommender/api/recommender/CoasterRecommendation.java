package app.molby.rcrecommender.api.recommender;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic coaster recommendation result.
 *
 * At minimum has coasterId, but may contain extra dynamic
 * fields like name, park, country, score, etc.
 */
@Data
@NoArgsConstructor
public class CoasterRecommendation {

    @JsonProperty("coaster_id")
    private Integer coasterId;

    /**
     * Any additional metadata columns returned by the engine.
     */
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void addAdditionalProperty(String key, Object value) {
        if ("coaster_id".equals(key)) {
            // coaster_id already mapped explicitly
            return;
        }
        additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }
}
