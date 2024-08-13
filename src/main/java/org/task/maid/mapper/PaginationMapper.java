package org.task.maid.mapper;

import org.springframework.stereotype.Component;
import org.task.maid.dto.pagination.PaginationDetails;
import org.task.maid.dto.pagination.PaginationDto;

import java.util.List;

@Component
public class PaginationMapper {

    public <T> PaginationDto<T> toPaginationDto(List<T> content, PaginationDetails paginationDetails) {
        PaginationDto<T> paginationDto = new PaginationDto<>();
        paginationDto.setPaginationDetails(paginationDetails);
        paginationDto.setData(content);
        return paginationDto;
    }
}
