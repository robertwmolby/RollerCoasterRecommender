package app.molby.rcrecommender.domain.user;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

class UserEntityTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(UserEntity.class);
    }

}
