package app.molby.rcrecommender.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for API access via restTemplate
 * @author Bob Molby
 */
@Configuration
public class RestClientConfig {

    /**
     * restTemplate Template for access to apis (specifically roller coaster recommendations).
     *
     * @param builder Builds rest templates
     * @return rest template built.
     */

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}