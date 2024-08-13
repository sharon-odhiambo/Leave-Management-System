package com.example.leave_manager.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
        log.error("ResourceAlreadyExistsException thrown: " + message);

    }
}
