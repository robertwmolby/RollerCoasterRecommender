package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.domain.country.CountryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto toDto(CountryEntity entity);

    List<CountryDto> toDtoList(List<CountryEntity> entities);

    CountryEntity toEntity(CountryDto dto);
}
