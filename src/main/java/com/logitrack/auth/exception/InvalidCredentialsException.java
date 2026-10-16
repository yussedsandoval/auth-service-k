package com.logitrack.auth.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
