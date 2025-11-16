package app.molby.rcrecommender.api.country;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CountryAccessControllerTest {

    @Mock
    private CountryAccessService service;

    @Mock
    private CountryAccessMapper mapper;

    @InjectMocks
    private CountryAccessController subject;

    @Test
    void subjectIsCreated() {
        assertNotNull(subject);
    }

}
