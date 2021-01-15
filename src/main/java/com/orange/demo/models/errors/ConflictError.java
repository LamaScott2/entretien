package com.orange.demo.models.errors;

public class ConflictError {
    private String message = "Conflict";
    private Integer code = 409;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
