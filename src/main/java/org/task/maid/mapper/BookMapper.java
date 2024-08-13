package org.task.maid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.task.maid.dto.BookDto;
import org.task.maid.entity.Book;
import org.task.maid.mapper.common.EntityMapper;

@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<Book, BookDto> {

    @Override
    @Mapping(target = "id", ignore = true)
    Book toEntity(BookDto dto);

    @Override
    @Mapping(target = "id", ignore = true)
    Book updateEntityFromDto(BookDto dto, @MappingTarget Book entity);

}
