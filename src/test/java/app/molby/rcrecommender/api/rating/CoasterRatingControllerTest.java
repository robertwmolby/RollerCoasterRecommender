package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
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

@WebMvcTest(CoasterRatingController.class)
@AutoConfigureMockMvc(addFilters = false)
class CoasterRatingControllerTest {

    static final String userId1 = "fred_flinstone";
    static final String userId2 = "barney_rubble";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoasterRatingService ratingService;

    @MockBean
    private CoasterRatingMapper mapper;

    //TODO FIX TEST
    //@Test
    @DisplayName("POST /ratings creates a new rating and returns 201")
    void createRating_returnsCreated() throws Exception {
        String requestJson = """
            {
              "userId": "fred_flinstone",
              "coasterId": 102,
              "rating": 4.5
            }
            """;

        CoasterRatingEntity toSave = new CoasterRatingEntity();
        CoasterRatingEntity saved = new CoasterRatingEntity();
        saved.setId(2001L);
        saved.setUserId(userId1);
        saved.setCoasterId(102L);
        saved.setRating(BigDecimal.valueOf(4.5));

        CoasterRatingDto responseDto = new CoasterRatingDto();
        responseDto.setId(2001L);
        responseDto.setUserId(userId1);
        responseDto.setCoasterId(102L);
        responseDto.setRating(BigDecimal.valueOf(4.5));

        given(mapper.toEntity(any(CoasterRatingDto.class))).willReturn(toSave);
        given(ratingService.create(toSave)).willReturn(saved);
        given(mapper.toDto(saved)).willReturn(responseDto);

        mockMvc.perform(
                        post("/ratings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2001)))
                .andExpect(jsonPath("$.userId", is(userId1)))
                .andExpect(jsonPath("$.coasterId", is(102)))
                .andExpect(jsonPath("$.rating", is(4.5)));

        verify(mapper).toEntity(any(CoasterRatingDto.class));
        verify(ratingService).create(toSave);
        verify(mapper).toDto(saved);
    }

    @Test
    @DisplayName("GET /ratings/{id} returns a rating when found")
    void getById_returnsRating() throws Exception {
        long id = 2001L;

        CoasterRatingEntity entity = new CoasterRatingEntity();
        entity.setId(id);
        entity.setUserId(userId1);
        entity.setCoasterId(102L);
        entity.setRating(BigDecimal.valueOf(4.5));

        CoasterRatingDto dto = new CoasterRatingDto();
        dto.setId(id);
        dto.setUserId(userId1);
        dto.setCoasterId(102L);
        dto.setRating(BigDecimal.valueOf(4.5));

        given(ratingService.findById(id)).willReturn(entity);
        given(mapper.toDto(entity)).willReturn(dto);

        mockMvc.perform(get("/ratings/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.userId", is(userId1)))
                .andExpect(jsonPath("$.coasterId", is(102)))
                .andExpect(jsonPath("$.rating", is(4.5)));

        verify(ratingService).findById(id);
        verify(mapper).toDto(entity);
    }

    @Test
    @DisplayName("GET /ratings returns list of ratings")
    void getAll_returnsListOfRatings() throws Exception {
        CoasterRatingEntity entity1 = new CoasterRatingEntity();
        entity1.setId(2001L);
        entity1.setUserId(userId1);
        entity1.setCoasterId(102L);
        entity1.setRating(BigDecimal.valueOf(4.5));

        CoasterRatingEntity entity2 = new CoasterRatingEntity();
        entity2.setId(2002L);
        entity2.setUserId(userId2);
        entity2.setCoasterId(102L);
        entity2.setRating(BigDecimal.valueOf(3.5));

        CoasterRatingDto dto1 = new CoasterRatingDto();
        dto1.setId(2001L);
        dto1.setUserId(userId1);
        dto1.setCoasterId(102L);
        dto1.setRating(BigDecimal.valueOf(4.5));

        CoasterRatingDto dto2 = new CoasterRatingDto();
        dto2.setId(2002L);
        dto2.setUserId(userId2);
        dto2.setCoasterId(102L);
        dto2.setRating(BigDecimal.valueOf(3.5));

        // @TODO FIX THIS TEST.
//        given(ratingService.findAll(any())).willReturn(List.of(entity1, entity2));
//        given(mapper.toDto(entity1)).willReturn(dto1);
//        given(mapper.toDto(entity2)).willReturn(dto2);
//
//        mockMvc.perform(get("/ratings"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(2001)))
//                .andExpect(jsonPath("$[0].userId", is(userId1)))
//                .andExpect(jsonPath("$[0].rating", is(4.5)))
//                .andExpect(jsonPath("$[1].id", is(2002)))
//                .andExpect(jsonPath("$[1].userId", is(userId2)))
//                .andExpect(jsonPath("$[1].rating", is(3.5)));
//
//        verify(ratingService).findAll();
//        verify(mapper).toDto(entity1);
//        verify(mapper).toDto(entity2);
    }

    //TODO FIX TEST
    //@Test
    @DisplayName("PUT /ratings/{id} updates a rating and returns 200")
    void updateRating_returnsUpdated() throws Exception {
        long id = 2001L;

        String requestJson = """
            {
              "userId": 501,
              "coasterId": 102,
              "rating": 4.0
            }
            """;

        CoasterRatingEntity newState = new CoasterRatingEntity();
        CoasterRatingEntity updated = new CoasterRatingEntity();
        updated.setId(id);
        updated.setUserId(userId1);
        updated.setCoasterId(102L);
        updated.setRating(BigDecimal.valueOf(4.0));

        CoasterRatingDto responseDto = new CoasterRatingDto();
        responseDto.setId(id);
        responseDto.setUserId(userId1);
        responseDto.setCoasterId(102L);
        responseDto.setRating(BigDecimal.valueOf(4.0));

        given(mapper.toEntity(any(CoasterRatingDto.class))).willReturn(newState);
        given(ratingService.update(eq(id), eq(newState))).willReturn(updated);
        given(mapper.toDto(updated)).willReturn(responseDto);

        mockMvc.perform(
                        put("/ratings/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.userId", is(userId1)))
                .andExpect(jsonPath("$.coasterId", is(102)))
                .andExpect(jsonPath("$.rating", is(4.0)));

        verify(mapper).toEntity(any(CoasterRatingDto.class));
        verify(ratingService).update(eq(id), eq(newState));
        verify(mapper).toDto(updated);
    }

    @Test
    @DisplayName("DELETE /ratings/{id} deletes a rating and returns 204")
    void deleteRating_returnsNoContent() throws Exception {
        long id = 2001L;

        mockMvc.perform(delete("/ratings/{id}", id))
                .andExpect(status().isNoContent());

        verify(ratingService).delete(id);
    }
}
