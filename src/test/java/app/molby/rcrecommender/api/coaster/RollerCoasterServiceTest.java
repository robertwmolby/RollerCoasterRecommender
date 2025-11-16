package app.molby.rcrecommender.api.coaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RollerCoasterServiceTest {

    @Mock
    private RollerCoasterRepository rollerCoasterRepository;

    @InjectMocks
    private RollerCoasterService subject;

    @Test
    void subjectIsCreated() {
        assertNotNull(subject);
    }

}
