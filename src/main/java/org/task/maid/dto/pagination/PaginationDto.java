package org.task.maid.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto<T> {
    private List<T> data;
    private PaginationDetails paginationDetails;
}
