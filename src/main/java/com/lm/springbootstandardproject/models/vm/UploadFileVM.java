package com.lm.springbootstandardproject.models.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UploadFileVM {
    private String fileName;
    @Schema(description = "文件url链接")
    private String fileUrl;
    @Schema(description = "文件后缀名")
    private String ext;
}
