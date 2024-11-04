package com.project.birdSightings.errorHandling;

import lombok.Getter;

/**
 * A class representing an error response.
 */
@Getter
public class ErrorResponse {
    private final String message;

    /**
     * Constructs a new ErrorResponse with the specified message.
     *
     * @param message the error message
     */
    public ErrorResponse(String message) {
        this.message = message;
    }
}