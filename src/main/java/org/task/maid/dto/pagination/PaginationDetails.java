package org.task.maid.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationDetails {

    private int currentPage;
    private long totalElements;
    private int size;
    private int totalPages;
}
