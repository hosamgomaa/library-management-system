package org.task.maid.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.task.maid.dto.common.BaseDto;


@Data
public class PatronDto extends BaseDto {

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "contactInformation is mandatory")
    private String contactInformation;
}