package org.task.maid.mapper.common;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.task.maid.dto.common.BaseDto;
import org.task.maid.entity.common.BaseEntity;

import java.util.List;

public interface EntityMapper<E extends BaseEntity, D extends BaseDto> {

    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDto(List<E> entities);

    List<E> toEntity(List<D> dtoList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E updateEntityFromDto(D dto, @MappingTarget E entity);

}
