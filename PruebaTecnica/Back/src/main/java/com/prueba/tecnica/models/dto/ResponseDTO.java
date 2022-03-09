package com.prueba.tecnica.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * DTO para el response de los servicios.
 * @param <T> Indica el tipo de dato que recibe el DTO
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseDTO<T> {
    private int codeStatus;
    private String mensajeError;
    private T data;
    private List<T> listData;
}
