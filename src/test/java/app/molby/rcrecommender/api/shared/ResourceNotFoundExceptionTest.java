package app.molby.rcrecommender.api.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ResourceNotFoundExceptionTest {

    @InjectMocks
    private ResourceNotFoundException subject;

    @Test
    void subjectIsCreated() {
        assertNotNull(subject);
    }

}
