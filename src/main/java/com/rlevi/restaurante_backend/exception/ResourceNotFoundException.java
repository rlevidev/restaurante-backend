package com.rlevi.restaurante_backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s com ID %d não foi encontrado", resource, id));
    }

    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s com %s '%s' não foi encontrado", resource, field, value));
    }
}