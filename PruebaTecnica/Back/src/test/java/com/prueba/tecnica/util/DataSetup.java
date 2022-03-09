package com.prueba.tecnica.util;

import com.prueba.tecnica.models.dto.TaskDTO;
import com.prueba.tecnica.models.entity.Task;
import com.prueba.tecnica.models.entity.User;

public class DataSetup {

    public User getUsuario() {
        User usuario = new User();
        usuario.setPrimerNombre("Test");
        usuario.setPrimerApellido("Testing");
        usuario.setCorreo("test@test.com");
        usuario.setUserName("test");
        usuario.setPassword("test");
        return usuario;
    }

    public TaskDTO getTarea() {
        TaskDTO tarea = new TaskDTO();
        tarea.setNombre("Tarea test");
        tarea.setDescripcion("Tarea descripcion de prueba");
        tarea.setEstado((short)1);
        tarea.setIdUsuario(1l);
        return tarea;
    }

    public TaskDTO getTareaUpdate(Task tarea) {
        TaskDTO tareaDTO = new TaskDTO();
        tareaDTO.setIdTarea(tarea.getIdTarea());
        tareaDTO.setNombre(tarea.getNombre());
        tareaDTO.setDescripcion(tarea.getDescripcion());
        tareaDTO.setEstado(tarea.getEstado());
        tareaDTO.setIdUsuario(1l);
        return tareaDTO;
    }
}
