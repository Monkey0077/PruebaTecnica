package com.prueba.tecnica.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DTO para request de guardar un nuevo usuario.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestUserDTO {

    private Long idUsuario;

    @NotBlank(message = "El primer nombre es obligatorio")
    @NotNull(message = "el valorAPagar es obligatorio")
    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    private String correo;

    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
}
