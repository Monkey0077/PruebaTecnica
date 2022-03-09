package com.prueba.tecnica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Excepcion para controlar acceso a los servicios  */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Unauthorized  extends Exception {
    public Unauthorized(String message) {
        super(message);
    }
}
