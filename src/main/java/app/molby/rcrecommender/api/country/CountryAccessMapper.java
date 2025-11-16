package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryAccessEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryAccessMapper {

    CountryAccessDto toDto(CountryAccessEntity entity);

    List<CountryAccessDto> toDtoList(List<CountryAccessEntity> entities);

    CountryAccessEntity toEntity(CountryAccessDto dto);
}
