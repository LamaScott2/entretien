package com.orange.demo.models.errors;

public class ForbiddenError {
    private String message = "Forbidden";
    private Integer code = 403;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
