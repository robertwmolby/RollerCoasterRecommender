package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.domain.user.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
/**
 * MapStruct mapper responsible for converting between {@link UserEntity}
 * domain objects and {@link UserDto} API data transfer objects.
 * <p>
 * This interface is implemented at build time by MapStruct and exposed
 * as a Spring bean.
 * @author Bob Molby
 */
public interface UserMapper {

    /**
     * Maps a {@link UserEntity} from the domain layer to a corresponding
     * {@link UserDto} used by the API layer.
     *
     * @param userEntity the user entity to convert; may be {@code null}
     * @return the mapped {@link UserDto}, or {@code null} if {@code entity} is {@code null}
     */
    UserDto toUserDto(UserEntity userEntity);

    /**
     * Maps a list of {@link UserEntity} instances to a list of
     * {@link UserDto} instances.
     *
     * @param userEntities the list of user entities to convert; may be {@code null}
     * @return a list of mapped {@link UserDto} objects, or {@code null} if {@code entities} is {@code null}
     */
    List<UserDto> toUserDtoList(List<UserEntity> userEntities);

    /**
     * Maps a {@link UserDto} from the API layer to a corresponding
     * {@link UserEntity} for use in the domain and persistence layers.
     *
     * @param userDto the user DTO to convert; may be {@code null}
     * @return the mapped {@link UserEntity}, or {@code null} if {@code userDto} is {@code null}
     */
    UserEntity toUserEntity(UserDto userDto);
}
