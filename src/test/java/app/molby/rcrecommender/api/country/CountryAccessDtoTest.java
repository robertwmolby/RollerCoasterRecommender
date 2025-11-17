package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

class CountryAccessDtoTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(CountryAccessDtoTest.class);
    }
}
