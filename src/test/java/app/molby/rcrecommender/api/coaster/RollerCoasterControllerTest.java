package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RollerCoasterController.class)
@AutoConfigureMockMvc(addFilters = false)
class RollerCoasterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RollerCoasterService coasterService;

    @MockBean
    private RollerCoasterMapper coasterMapper;

    @Test
    @DisplayName("POST /coasters creates a new coaster and returns 201")
    void createCoaster_returnsCreated() throws Exception {
        // Arrange
        String requestJson = """
            {
              "name": "Millennium Force",
              "amusementPark": "Cedar Point",
              "type": "Steel",
              "design": "Sitdown",
              "status": "Operating",
              "manufacturer": "Intamin",
              "model": "Giga Coaster",
              "length": 6595,
              "height": 310,
              "drop": 300,
              "inversionCount": 0,
              "speed": 93,
              "verticalAngle": 80,
              "restraints": "Lap Bar",
              "gForce": 4.5,
              "intensity": "Thrill",
              "duration": 150,
              "country": "United States",
              "averageRating": 4.8
            }
            """;

        RollerCoasterEntity savedEntity = new RollerCoasterEntity();
        savedEntity.setId(101L);

        RollerCoasterDto responseDto = new RollerCoasterDto();
        responseDto.setId(101L);
        responseDto.setName("Millennium Force");

        // mapper: dto -> entity
        given(coasterMapper.toRollerCoasterEntity(any(RollerCoasterDto.class))).willReturn(new RollerCoasterEntity());
        // service: create(entity) -> savedEntity
        given(coasterService.create(any(RollerCoasterEntity.class))).willReturn(savedEntity);
        // mapper: entity -> dto
        given(coasterMapper.toRollerCoasterDto(savedEntity)).willReturn(responseDto);

        // Act & Assert
        mockMvc.perform(
                        post("/coasters")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(101)))
                .andExpect(jsonPath("$.name", is("Millennium Force")));

        // Verify interactions
        verify(coasterMapper).toRollerCoasterEntity(any(RollerCoasterDto.class));
        verify(coasterService).create(any(RollerCoasterEntity.class));
        verify(coasterMapper).toRollerCoasterDto(savedEntity);
    }

    @Test
    @DisplayName("GET /coasters/{id} returns a coaster when found")
    void findById_returnsCoaster() throws Exception {
        // Arrange
        long id = 101L;

        RollerCoasterEntity entity = new RollerCoasterEntity();
        entity.setId(id);

        RollerCoasterDto dto = new RollerCoasterDto();
        dto.setId(id);
        dto.setName("Millennium Force");

        given(coasterService.findById(id)).willReturn(entity);
        given(coasterMapper.toRollerCoasterDto(entity)).willReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/coasters/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.name", is("Millennium Force")));

        verify(coasterService).findById(id);
        verify(coasterMapper).toRollerCoasterDto(entity);
    }

    @Test
    @DisplayName("GET /coasters returns list of coasters")
    void findAll_returnsListOfCoasters() throws Exception {
        // Arrange
        RollerCoasterEntity entity1 = new RollerCoasterEntity();
        entity1.setId(101L);
        RollerCoasterEntity entity2 = new RollerCoasterEntity();
        entity2.setId(102L);

        RollerCoasterDto dto1 = new RollerCoasterDto();
        dto1.setId(101L);
        dto1.setName("Millennium Force");

        RollerCoasterDto dto2 = new RollerCoasterDto();
        dto2.setId(102L);
        dto2.setName("GateKeeper");

        given(coasterService.findAll()).willReturn(List.of(entity1, entity2));
        given(coasterMapper.toRollerCoasterDto(entity1)).willReturn(dto1);
        given(coasterMapper.toRollerCoasterDto(entity2)).willReturn(dto2);

        // Act & Assert
        mockMvc.perform(get("/coasters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(101)))
                .andExpect(jsonPath("$[0].name", is("Millennium Force")))
                .andExpect(jsonPath("$[1].id", is(102)))
                .andExpect(jsonPath("$[1].name", is("GateKeeper")));

        verify(coasterService).findAll();
        verify(coasterMapper).toRollerCoasterDto(entity1);
        verify(coasterMapper).toRollerCoasterDto(entity2);
    }

    @Test
    @DisplayName("DELETE /coasters/{id} deletes a coaster and returns 204")
    void deleteCoaster_returnsNoContent() throws Exception {
        long id = 101L;

        mockMvc.perform(delete("/coasters/{id}", id))
                .andExpect(status().isNoContent());

        verify(coasterService).delete(id);
    }
}
