package com.lm.SpringBootProject.core.common;

import lombok.Data;

@Data
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

    public ResponseResult(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult ok(){
        return new ResponseResult(200,"ok",null);
    }
    public static ResponseResult success(int code,String message){
        return new ResponseResult(200,message,null);
    }
    public  ResponseResult success(int code,String message,T data){
        return new ResponseResult(200,message,data);
    }
    public static ResponseResult failed(){
        return new ResponseResult(500,"failed",null);
    }
    public static ResponseResult error(){
        return new ResponseResult(500,"error",null);
    }


}
