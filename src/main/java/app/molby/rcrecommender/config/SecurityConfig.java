package app.molby.rcrecommender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
/**
 * SecurityConfig security-related component.
 * @author Bob Molby
 */
public class SecurityConfig {


    /**
     * devFilterChain Security filter for development profile.  Wide open.
     * Not to be used in production.
     *
     * @param http security requests
     * @return Filterchain for next actions in processing request
     */
    @Bean
    @Profile("dev")
    public SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // allow Swagger/OpenAPI without auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // in dev everything is open...
                        .anyRequest().permitAll()
                ); // or formLogin()

        return http.build();
    }

    /**
     * devFilterChain Security filter for other requests
     *
     * !!! RIGHT NOW THIS IS COMPLETELY OPEN AS IT IS BEIN USED SIMPLY FOR DEMONSTRATION PURPOSES !!!
     * In a real environment we would likely implement oauth or some other jwt based security mechanism.
     *
     * @param http security requests
     * @return Filterchain for next actions in processing request
     */
    @Bean
    @Profile("!dev")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // allow Swagger/OpenAPI without auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // everything else still needs auth
                        .anyRequest().permitAll()
                ); // or formLogin()

        return http.build();
    }
}
