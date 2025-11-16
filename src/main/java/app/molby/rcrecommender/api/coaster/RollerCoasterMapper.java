package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.domain.coaster.RollerCoasterEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RollerCoasterMapper {

    RollerCoasterDto toDto(RollerCoasterEntity entity);

    List<RollerCoasterDto> toDtoList(List<RollerCoasterEntity> entities);

    RollerCoasterEntity toEntity(RollerCoasterDto dto);
}
