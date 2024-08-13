package org.task.maid.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.task.maid.dto.common.BaseDto;

import java.time.LocalDate;


@Data
public class BookDto extends BaseDto {

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "author is mandatory")
    private String author;

    @NotNull(message = "publicationYear is mandatory")
    private LocalDate publicationYear;

    @NotBlank(message = "isbn is mandatory")
    private String isbn;
}