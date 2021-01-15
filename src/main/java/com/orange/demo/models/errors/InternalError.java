package com.orange.demo.models.errors;

public class InternalError {

    private String message = "Internal Error";
    private Integer code = 500;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}