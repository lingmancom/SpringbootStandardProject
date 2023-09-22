package com.lm.springbootstandardproject.core.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "int")
public class DemonIdIntDto {
    private int id;
}
