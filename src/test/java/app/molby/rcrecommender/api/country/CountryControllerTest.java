package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryEntity;
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

@WebMvcTest(CountryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService service;

    @MockBean
    private CountryMapper mapper;

    // ---------- Helpers ----------

    private CountryEntity buildEntity(long id, String name) {
        CountryEntity e = new CountryEntity();
        e.setId(id);
        e.setCountryName(name);
        return e;
    }

    private CountryDto buildDto(long id, String name) {
        CountryDto dto = new CountryDto();
        dto.setId(id);
        dto.setCountryName(name);
        return dto;
    }

    // ---------- Tests ----------

    @Test
    void create_returnsCreatedCountry() throws Exception {
        String requestJson = """
            {
              "name": "United States"
            }
            """;

        CountryEntity toSave = buildEntity(0L, "United States");
        CountryEntity saved = buildEntity(1L, "United States");
        CountryDto responseDto = buildDto(1L, "United States");

        when(mapper.toCountryEntity(ArgumentMatchers.any(CountryDto.class))).thenReturn(toSave);
        when(service.create(toSave)).thenReturn(saved);
        when(mapper.toCountryDto(saved)).thenReturn(responseDto);

        mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.countryName").value("United States"));

        verify(mapper).toCountryEntity(any(CountryDto.class));
        verify(service).create(toSave);
        verify(mapper).toCountryDto(saved);
    }

    @Test
    void getById_returnsCountry() throws Exception {
        long id = 1L;

        CountryEntity entity = buildEntity(id, "United States");
        CountryDto dto = buildDto(id, "United States");

        when(service.getById(id)).thenReturn(entity);
        when(mapper.toCountryDto(entity)).thenReturn(dto);

        mockMvc.perform(get("/countries/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.countryName").value("United States"));

        verify(service).getById(id);
        verify(mapper).toCountryDto(entity);
    }

    @Test
    void getAll_returnsListOfCountries() throws Exception {
        CountryEntity e1 = buildEntity(1L, "United States");
        CountryEntity e2 = buildEntity(2L, "Canada");

        CountryDto d1 = buildDto(1L, "United States");
        CountryDto d2 = buildDto(2L, "Canada");

        when(service.getAll()).thenReturn(List.of(e1, e2));
        when(mapper.toCountryDto(e1)).thenReturn(d1);
        when(mapper.toCountryDto(e2)).thenReturn(d2);

        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].countryName").value("United States"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].countryName").value("Canada"));

        verify(service).getAll();
        verify(mapper).toCountryDto(e1);
        verify(mapper).toCountryDto(e2);
    }

    @Test
    void update_returnsUpdatedCountry() throws Exception {
        long id = 1L;

        String requestJson = """
            {
              "code": "US",
              "name": "United States of America"
            }
            """;

        CountryEntity newState = buildEntity(0L, "United States of America");
        CountryEntity updatedEntity = buildEntity(id, "United States of America");
        CountryDto responseDto = buildDto(id, "United States");

        when(mapper.toCountryEntity(any(CountryDto.class))).thenReturn(newState);
        when(service.update(id, newState)).thenReturn(updatedEntity);
        when(mapper.toCountryDto(updatedEntity)).thenReturn(responseDto);

        mockMvc.perform(put("/countries/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.countryName").value("United States"));

        verify(mapper).toCountryEntity(any(CountryDto.class));
        verify(service).update(id, newState);
        verify(mapper).toCountryDto(updatedEntity);
    }

    @Test
    void delete_deletesCountryAndReturnsNoContent() throws Exception {
        long id = 1L;

        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/countries/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).delete(id);
    }
}
