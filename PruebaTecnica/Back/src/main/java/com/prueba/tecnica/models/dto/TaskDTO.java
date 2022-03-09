package com.prueba.tecnica.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO para gestionar los servicios de las tareas.
 */
@Data
@ToString
@NoArgsConstructor
public class TaskDTO {

    private Long idTarea;

    @NotBlank(message = "El nombre es obligatorio")
    @NotNull(message = "El nombre es obligatorio")
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatorio")
    @NotNull(message = "La descripción es obligatorio")
    @NotEmpty(message = "La descripción es obligatorio")
    private String descripcion;

    @NotNull(message = "El estado es obligatorio")
    private Short estado;

    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;
}
