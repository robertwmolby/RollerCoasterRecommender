package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Maps between {@link RollerCoasterEntity} domain entities and {@link RollerCoasterDto}
 * objects used by the API layer.
 *
 * <p>This mapper is implemented by MapStruct at build time and exposed as a Spring
 * bean so it can be injected into services and controllers.</p>
 */
@Mapper(componentModel = "spring")
public interface RollerCoasterMapper {

    /**
     * Converts a {@link RollerCoasterEntity} domain entity to its API
     * representation.
     *
     * @param rollerCoasterEntity the entity to convert; may be {@code null}
     * @return the corresponding {@link RollerCoasterDto}, or {@code null} if the input is {@code null}
     */
    RollerCoasterDto toRollerCoasterDto(RollerCoasterEntity rollerCoasterEntity);

    /**
     * Converts a list of {@link RollerCoasterEntity} instances to a list of
     * {@link RollerCoasterDto} objects.
     *
     * @param rollerCoasterEntities the entities to convert; may be {@code null} or empty
     * @return a list of corresponding DTOs; never {@code null}, but may be empty
     */
    List<RollerCoasterDto> toRollerCoasterDtoList(List<RollerCoasterEntity> rollerCoasterEntities);

    /**
     * Converts a {@link RollerCoasterDto} received from the API layer into a
     * {@link RollerCoasterEntity} suitable for persistence and domain logic.
     *
     * @param rollerCoasterDto the DTO to convert; may be {@code null}
     * @return the corresponding {@link RollerCoasterEntity}, or {@code null} if the input is {@code null}
     */
    RollerCoasterEntity toRollerCoasterEntity(RollerCoasterDto rollerCoasterDto);
}