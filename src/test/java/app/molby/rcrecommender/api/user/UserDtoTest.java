package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(UserDto.class);
    }
}