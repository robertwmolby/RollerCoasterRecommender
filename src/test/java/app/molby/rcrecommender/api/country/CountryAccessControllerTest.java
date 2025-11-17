package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryAccessEntity;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryAccessController.class)
@AutoConfigureMockMvc(addFilters = false)
class CountryAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryAccessService service;

    @MockBean
    private CountryAccessMapper mapper;

    // --- helpers to build DTOs for responses ---

    private CountryAccessDto buildDto(long id, String sourceName, String accessibleName) {
        CountryDto source = new CountryDto();
        source.setCountryName(sourceName);

        CountryDto accessible = new CountryDto();
        accessible.setCountryName(accessibleName);

        CountryAccessDto dto = new CountryAccessDto();
        dto.setId(id);
        dto.setSourceCountry(source);
        dto.setAccessibleCountry(accessible);
        return dto;
    }

    private CountryAccessEntity buildEntity(long id) {
        CountryAccessEntity e = new CountryAccessEntity();
        e.setId(id);
        return e;
    }

    @Test
    void create_returnsCreatedMapping() throws Exception {
        // given
        String requestJson = """
            {
              "sourceCountry": { "countryName": "United States" },
              "accessibleCountry": { "countryName": "Canada" }
            }
            """;

        CountryAccessEntity toSave = buildEntity(0L);
        CountryAccessEntity saved = buildEntity(10L);
        CountryAccessDto responseDto = buildDto(10L, "United States", "Canada");

        when(mapper.toCountryAccessEntity(ArgumentMatchers.any(CountryAccessDto.class))).thenReturn(toSave);
        when(service.create(toSave)).thenReturn(saved);
        when(mapper.toCountryAccessDto(saved)).thenReturn(responseDto);

        // when/then
        mockMvc.perform(post("/country-access")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.sourceCountry.countryName").value("United States"))
                .andExpect(jsonPath("$.accessibleCountry.countryName").value("Canada"));

        verify(mapper).toCountryAccessEntity(any(CountryAccessDto.class));
        verify(service).create(toSave);
        verify(mapper).toCountryAccessDto(saved);
    }

    @Test
    void findById_returnsMapping() throws Exception {
        long id = 10L;

        CountryAccessEntity entity = buildEntity(id);
        CountryAccessDto dto = buildDto(id, "United States", "Canada");

        when(service.getById(id)).thenReturn(entity);
        when(mapper.toCountryAccessDto(entity)).thenReturn(dto);

        mockMvc.perform(get("/country-access/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.sourceCountry.countryName").value("United States"))
                .andExpect(jsonPath("$.accessibleCountry.countryName").value("Canada"));

        verify(service).getById(id);
        verify(mapper).toCountryAccessDto(entity);
    }

    @Test
    void findAll_returnsListOfMappings() throws Exception {
        CountryAccessEntity e1 = buildEntity(10L);
        CountryAccessEntity e2 = buildEntity(11L);

        CountryAccessDto d1 = buildDto(10L, "United States", "Canada");
        CountryAccessDto d2 = buildDto(11L, "United States", "Mexico");

        when(service.getAll()).thenReturn(List.of(e1, e2));
        when(mapper.toCountryAccessDto(e1)).thenReturn(d1);
        when(mapper.toCountryAccessDto(e2)).thenReturn(d2);

        mockMvc.perform(get("/country-access"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].accessibleCountry.countryName").value("Canada"))
                .andExpect(jsonPath("$[1].id").value(11))
                .andExpect(jsonPath("$[1].accessibleCountry.countryName").value("Mexico"));

        verify(service).getAll();
        verify(mapper).toCountryAccessDto(e1);
        verify(mapper).toCountryAccessDto(e2);
    }

    @Test
    void update_returnsUpdatedMapping() throws Exception {
        long id = 10L;

        String requestJson = """
            {
              "sourceCountry": { "countryName": "United States" },
              "accessibleCountry": { "countryName": "Canada" }
            }
            """;

        CountryAccessEntity newState = buildEntity(0L);
        CountryAccessEntity updatedEntity = buildEntity(id);
        CountryAccessDto responseDto = buildDto(id, "United States", "Canada");

        when(mapper.toCountryAccessEntity(any(CountryAccessDto.class))).thenReturn(newState);
        when(service.update(id, newState)).thenReturn(updatedEntity);
        when(mapper.toCountryAccessDto(updatedEntity)).thenReturn(responseDto);

        mockMvc.perform(put("/country-access/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.sourceCountry.countryName").value("United States"))
                .andExpect(jsonPath("$.accessibleCountry.countryName").value("Canada"));

        verify(mapper).toCountryAccessEntity(any(CountryAccessDto.class));
        verify(service).update(id, newState);
        verify(mapper).toCountryAccessDto(updatedEntity);
    }

    @Test
    void delete_deletesMappingAndReturnsNoContent() throws Exception {
        long id = 10L;

        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/country-access/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).delete(id);
    }
}
