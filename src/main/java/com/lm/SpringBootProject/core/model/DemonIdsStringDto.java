package com.lm.SpringBootProject.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "String 数组")
public class DemonIdsStringDto {
    private String[] ids;
}

