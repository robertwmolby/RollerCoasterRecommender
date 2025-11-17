package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.util.PojoTestHandler;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RollerCoasterDto}.
 */
public class RollerCoasterDtoTest {

    @Test
    void validatePojo() throws Exception {
        PojoTestHandler.assertPojoContract(RollerCoasterDto.class);
    }

}