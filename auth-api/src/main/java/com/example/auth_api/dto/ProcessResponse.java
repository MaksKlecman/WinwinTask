package com.example.auth_api.dto;

public class ProcessResponse {
    private String result;

    public ProcessResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}