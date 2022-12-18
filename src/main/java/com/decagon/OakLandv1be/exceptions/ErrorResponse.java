package com.decagon.OakLandv1be.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Data
public class ErrorResponse {
    private String message;
    private String debugMessage;
    private HttpStatus status;
}
