package app.molby.rcrecommender.domain.coaster;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

class RollerCoasterEntityTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(RollerCoasterEntity.class);
    }
}