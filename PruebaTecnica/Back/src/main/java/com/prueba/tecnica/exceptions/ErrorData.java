package com.prueba.tecnica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorData extends Exception{

    public ErrorData(String message) {
        super(message);
    }
}
