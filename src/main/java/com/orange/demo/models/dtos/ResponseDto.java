package com.orange.demo.models.dtos;

public class ResponseDto {

    private String response;

    public ResponseDto(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String username) {
        this.response = response;
    }

    @Override
    public String toString() {
        return response;
    }
}
