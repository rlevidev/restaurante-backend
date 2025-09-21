package com.rlevi.restaurante_backend.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resource, String field, String value) {
        super(String.format("%s com %s '%s' já existe", resource, field, value));
    }
}