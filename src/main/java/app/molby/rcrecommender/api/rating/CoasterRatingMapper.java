package app.molby.rcrecommender.api.rating;

import app.molby.rcrecommender.domain.rating.CoasterRatingEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoasterRatingMapper {

    CoasterRatingDto toDto(CoasterRatingEntity entity);

    List<CoasterRatingDto> toDtoList(List<CoasterRatingEntity> entities);

    CoasterRatingEntity toEntity(CoasterRatingDto dto);
}
