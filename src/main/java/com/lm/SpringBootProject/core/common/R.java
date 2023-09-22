package com.lm.SpringBootProject.core.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiResponse(responseCode = "200", description = "返回模型")
public class R<T> {

    @Schema(description = "返回码")
    public int code;

    // 数据
    @Schema(description = "返回数据")
    public T data;


    @Schema(description = "自定义消息")
    public String message;

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        return ok(data, "success");
    }


    public static <T> R<T> ok(T data, String message) {
        R response = new R();
        response.code = ResponseType.Ok1;
        response.data = data;
        response.message = message;
        return response;
    }

    public static <T> R<T> error(String message, T data) {
        R response = new R();
        response.code = ResponseType.CustomDescription3;
        response.data = data;
        response.message = message;
        return response;
    }
    public static <T> R<T> error(String message) {
        return error(message, null);
    }

    public static <T> R<T> error() {
        return error("");
    }


    public static <T> R<T> systemError(String message, T data) {
        R response = new R();
        response.code = ResponseType.GeneralException2;
        response.data = data;
        response.message = message;
        return response;
    }

    public static <T> R<T> systemError(String message) {
        return systemError(message, null);
    }


    public static <T> R<T> errorCode(int code) {
        R response = new R();
        response.code = code;
        response.data = null;
        response.message = "";
        return response;
    }


    public static <T> R<T> errorNoAuth(String message) {
        R response = new R();
        response.code = ResponseType.UnAuthorized401;
        response.data = null;
        response.message = message;
        return response;
    }

    public static <T> R<T> errorNoAuth() {
        R response = new R();
        response.code = ResponseType.UnAuthorized401;
        response.data = null;
        response.message = "未授权的请求";
        return response;
    }
}
