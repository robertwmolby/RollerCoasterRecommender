package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

public class CountryDtoTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(CountryDto.class);
    }

}
