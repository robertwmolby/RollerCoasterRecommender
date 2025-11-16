package app.molby.rcrecommender.api.country;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CountryAccessServiceTest {

    @Mock
    private CountryAccessRepository countryAccessRepository;

    @InjectMocks
    private CountryAccessService subject;

    @Test
    void subjectIsCreated() {
        assertNotNull(subject);
    }

}
