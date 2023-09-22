package com.lm.springbootstandardproject.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class DemonIdStringDto {
    @Schema(description = "String")
    private String id;
}
