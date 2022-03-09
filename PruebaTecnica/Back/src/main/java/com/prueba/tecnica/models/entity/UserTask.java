package com.prueba.tecnica.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "usuario_tarea")
public class UserTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario_tarea")
    private Long idUsuarioTarea;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private User usuario;

    @JoinColumn(name = "id_tarea", referencedColumnName = "id_tarea")
    @OneToOne
    private Task tarea;
}
