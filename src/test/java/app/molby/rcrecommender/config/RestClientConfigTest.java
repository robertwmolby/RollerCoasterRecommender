package app.molby.rcrecommender.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestClientConfigTest {

    @Mock
    private RestTemplateBuilder builder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClientConfig config;

    @Test
    void restTemplate_createsRestTemplateFromBuilder() {
        // Given
        when(builder.build()).thenReturn(restTemplate);

        // When
        RestTemplate result = config.restTemplate(builder);

        // Then
        verify(builder, times(1)).build();
        assertNotNull(result);
        assertSame(restTemplate, result);
    }
}
