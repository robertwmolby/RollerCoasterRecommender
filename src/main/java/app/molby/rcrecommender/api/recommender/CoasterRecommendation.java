package app.molby.rcrecommender.api.recommender;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single coaster recommendation produced by the recommendation engine.
 *
 * <p>The result always includes a {@code coasterId}, and may also include a
 * variable set of additional fields (such as name, park, country, score, or
 * model-specific metadata). These extra fields are captured dynamically and
 * stored in {@code additionalProperties}.</p>
 * @author Bob Molby
 */
@Data
@NoArgsConstructor
public class CoasterRecommendation {

    /**
     * The unique identifier of the recommended coaster.
     */
    @JsonProperty("coaster_id")
    private Integer coasterId;

    /**
     * Flexible container for any additional metadata the engine returns.
     *
     * <p>Keys depend on the specific engine implementation and may include
     * attributes such as coaster name, park, statistics, or ranking scores.</p>
     */
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     * Adds a dynamic property to this recommendation during JSON deserialization.
     *
     * <p>If the property key is {@code coaster_id}, it is ignored because that
     * field is already mapped explicitly to {@link #coasterId}.</p>
     *
     * @param key   the JSON property name
     * @param value the value associated with the key
     *
     * TODO Update with specifically defined set of properties.
     */

    @JsonAnySetter
    public void addAdditionalProperty(String key, Object value) {
        if ("coaster_id".equals(key)) {
            return;
        }
        additionalProperties.put(key, value);
    }

    /**
     * Returns all dynamic properties included in the recommendation.
     *
     * @return a map of additional metadata fields
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }
}
