package com.lm.springbootstandardproject.core.common;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class DemonPagingModel<T> {


    @Schema(description = "总共页数")
    public int totalPage;


    @Schema(description = "当前页码")
    public int pageNumber;


    @Schema(description = "一页大小")
    public int pageSize;


    @Schema(description = "总共条目数量")
    public long totalCount;


    @Schema(description = "内容")
    public T data;

    public DemonPagingModel(int pageNumber, int pageSize, long totalCount, T data) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;
        totalPage = (int) Math.ceil(totalCount / (double) pageSize);
    }
}
