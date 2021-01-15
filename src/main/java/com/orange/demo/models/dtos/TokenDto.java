package com.orange.demo.models.dtos;

public class TokenDto {
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String username) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }
}
