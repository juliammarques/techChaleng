package com.tech.challenge.domain.controller.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
public class StandardError {
    private Instant timeStamp;
    private Integer status;
    private String message;
    private String path;
    private String error;
}
