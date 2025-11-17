package app.molby.rcrecommender.domain.country;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

class CountryAccessEntityTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(CountryAccessEntity.class);
    }}
