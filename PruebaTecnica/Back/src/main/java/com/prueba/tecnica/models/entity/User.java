package com.prueba.tecnica.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotBlank(message = "El campo nombre es obligatorio")
    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @NotBlank(message = "El campo apellido es obligatorio")
    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @NotBlank(message = "El campo correo es obligatorio")
    private String correo;

    @NotBlank(message = "El campo contraseña es obligatorio")
    private String password;

    @NotBlank(message = "El campo usuario es obligatorio")
    @Column(name = "user_name")
    private String userName;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

}
