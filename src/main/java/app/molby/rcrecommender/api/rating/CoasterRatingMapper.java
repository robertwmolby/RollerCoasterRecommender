package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Maps between {@link CoasterRatingEntity} domain entities and
 * {@link CoasterRatingDto} objects used by the API layer.
 *
 * <p>This mapper is implemented by MapStruct at build time and exposed as a
 * Spring bean so it can be injected into services and controllers.</p>
 */
@Mapper(componentModel = "spring")
public interface CoasterRatingMapper {

    /**
     * Converts a {@link CoasterRatingEntity} domain entity to its API
     * representation.
     *
     * @param entity the entity to convert; may be {@code null}
     * @return the corresponding {@link CoasterRatingDto}, or {@code null}
     *         if the input is {@code null}
     */
    CoasterRatingDto toDto(CoasterRatingEntity entity);

    /**
     * Converts a list of {@link CoasterRatingEntity} instances to a list of
     * {@link CoasterRatingDto} objects.
     *
     * @param entities the entities to convert; may be {@code null} or empty
     * @return a list of corresponding DTOs; never {@code null}, but may be empty
     */
    List<CoasterRatingDto> toDtoList(List<CoasterRatingEntity> entities);

    /**
     * Converts a {@link CoasterRatingDto} received from the API layer into a
     * {@link CoasterRatingEntity} suitable for persistence and domain logic.
     *
     * @param dto the DTO to convert; may be {@code null}
     * @return the corresponding {@link CoasterRatingEntity}, or {@code null}
     *         if the input is {@code null}
     */
    CoasterRatingEntity toEntity(CoasterRatingDto dto);
}
