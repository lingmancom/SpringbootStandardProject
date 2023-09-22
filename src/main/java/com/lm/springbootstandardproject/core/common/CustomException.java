package com.lm.springbootstandardproject.core.common;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}