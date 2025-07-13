package com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}