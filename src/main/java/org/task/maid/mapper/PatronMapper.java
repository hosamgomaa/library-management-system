package org.task.maid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.task.maid.dto.PatronDto;
import org.task.maid.entity.Patron;
import org.task.maid.mapper.common.EntityMapper;

@Mapper(componentModel = "spring")
public interface PatronMapper extends EntityMapper<Patron, PatronDto> {

    @Mapping(target = "id", ignore = true)
    Patron toEntity(PatronDto dto);

    @Mapping(target = "id", ignore = true)
    Patron updateEntityFromDto(PatronDto dto, @MappingTarget Patron entity);
}
