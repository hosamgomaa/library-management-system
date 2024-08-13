package org.task.maid.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.task.maid.dto.common.BaseDto;

import java.time.LocalDate;


@Data
public class BorrowingRecordDto extends BaseDto {
    @ReadOnlyProperty
    private BookDto book;

    @ReadOnlyProperty
    private PatronDto patron;

    private LocalDate borrowDate;

    @NotNull(message = "returnDate is mandatory")
    private LocalDate returnDate;

    private LocalDate returnedAt;

    @ReadOnlyProperty
    private Boolean returned;


}