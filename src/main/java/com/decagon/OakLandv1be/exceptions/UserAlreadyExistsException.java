package com.decagon.OakLandv1be.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExistsException extends RuntimeException{
    private String debugMessage;

    public UserAlreadyExistsException(String message) {

        super(message);
    }

    public UserAlreadyExistsException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
