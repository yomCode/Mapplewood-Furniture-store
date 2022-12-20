package com.decagon.OakLandv1be.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedUserException extends RuntimeException{

    private String debugMsg;

    public UnauthorizedUserException(String message) {
        super(message);
    }

    public UnauthorizedUserException(String message, String debugMsg) {
        super(message);
        this.debugMsg = debugMsg;
    }
}
