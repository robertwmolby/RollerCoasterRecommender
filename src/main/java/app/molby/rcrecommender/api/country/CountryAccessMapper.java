package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryAccessEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
/**
 * CountryAccessMapper part of the roller coaster recommender application.
 */
public interface CountryAccessMapper {

    CountryAccessDto toCountryAccessDto(CountryAccessEntity countryAccessEntity);

    List<CountryAccessDto> toCountryAccessDtos(List<CountryAccessEntity> countryAccessEntityList);

    CountryAccessEntity toCountryAccessEntity(CountryAccessDto countryAccessDto);
}
