package com.orange.demo.models.errors;

public class NotFoundError {

    private String message = "Not Found";
    private Integer code = 404;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
