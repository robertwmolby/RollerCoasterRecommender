package app.molby.rcrecommender.domain.country;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

class CountryEntityTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(CountryEntity.class);
    }

}