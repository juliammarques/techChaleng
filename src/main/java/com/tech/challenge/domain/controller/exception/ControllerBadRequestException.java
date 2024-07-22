package com.tech.challenge.domain.controller.exception;

public class ControllerBadRequestException extends RuntimeException{
    public ControllerBadRequestException(String message){
        super(message);
    }
}
