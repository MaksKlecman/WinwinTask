package com.example.auth_api.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email)
    {
        super("There is already a user with this email: " + email);
    }
}
