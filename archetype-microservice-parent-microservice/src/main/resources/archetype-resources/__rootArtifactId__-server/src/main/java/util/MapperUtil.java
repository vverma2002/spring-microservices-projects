package ${package}.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ${package}.dto.${service-alias}DTO;
import ${package}.entity.${service-alias}Entity;

@Mapper(componentModel = "spring")
public interface MapperUtil {

	MapperUtil INSTANCE = Mappers.getMapper(MapperUtil.class);

	${service-alias}DTO to${service-alias}DTO(${service-alias}Entity ${resource-alias});

	List<${service-alias}DTO> to${service-alias}DTO(List<${service-alias}Entity> ${resource-alias}s);

}