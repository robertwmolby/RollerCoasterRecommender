package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
/**
 * MapStruct mapper for converting between {@link CountryEntity} domain objects
 * and {@link CountryDto} API-facing data transfer objects.
 *
 * <p>This mapper is used throughout the roller coaster recommender application
 * to translate persistent country records into DTOs returned by REST controllers,
 * and vice versa when accepting requests that modify country data.</p>
 */
public interface CountryMapper {

    /**
     * Convert a {@link CountryEntity} into a {@link CountryDto}.
     *
     * @param countryEntity the country entity to convert; may be {@code null}
     * @return the corresponding DTO, or {@code null} if the input is {@code null}
     */
    CountryDto toCountryDto(CountryEntity countryEntity);

    /**
     * Convert a list of {@link CountryEntity} objects into a list of {@link CountryDto} objects.
     *
     * @param countryEntities the list of country entities to convert; may be empty or {@code null}
     * @return a list of DTOs in the same order as the input; {@code null} if input is {@code null}
     */
    List<CountryDto> toCountryDtoList(List<CountryEntity> countryEntities);

    /**
     * Convert a {@link CountryDto} into a {@link CountryEntity}.
     *
     * @param countryDto the country DTO to convert; may be {@code null}
     * @return the corresponding entity object, or {@code null} if the input is {@code null}
     */
    CountryEntity toCountryEntity(CountryDto countryDto);
}
