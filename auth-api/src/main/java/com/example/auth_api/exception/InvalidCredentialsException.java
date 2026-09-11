package com.example.auth_api.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException()
    {
        super("Invalid email or password");
    }
}