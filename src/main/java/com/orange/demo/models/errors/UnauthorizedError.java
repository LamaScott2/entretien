package com.orange.demo.models.errors;

public class UnauthorizedError {
    private String message = "Unauthorized";
    private Integer code = 401;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
