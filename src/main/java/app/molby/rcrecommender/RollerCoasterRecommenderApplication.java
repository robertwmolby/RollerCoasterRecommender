package app.molby.rcrecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * Application with restful services to allow a user to set their own ratings on roller coasters and
 * then retrieve information about other coasters they might like based on those ratings.
 * @author Bob Molby
 */
public class RollerCoasterRecommenderApplication {
/**
 * Application startup.
 * @param args Necessary arguments.  Nothing specifically used for this app.
 */

    public static void main(String[] args) {
        SpringApplication.run(RollerCoasterRecommenderApplication.class, args);
    }

}
