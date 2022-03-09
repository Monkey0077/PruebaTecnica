package com.prueba.tecnica.controllers;

import com.prueba.tecnica.exceptions.ErrorData;
import com.prueba.tecnica.exceptions.Unauthorized;
import com.prueba.tecnica.models.dto.ResponseDTO;
import com.prueba.tecnica.models.dto.TaskDTO;
import com.prueba.tecnica.models.entity.Task;
import com.prueba.tecnica.services.ITaskService;
import com.prueba.tecnica.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

/**
 * Controlador con los servicios asociados a la gestión de las tareas.
 */
@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    AuthValidator validator;

    /**
     * Servicio para crear una tarea.
     */
    @PostMapping
    public ResponseDTO<Task> createTask(@Valid @RequestBody TaskDTO tareaDTO, @RequestHeader("Authorization") String token) throws Unauthorized {
        validator.validarToken(token);
        return taskService.createTask(tareaDTO);

    }

    /**
     * Servicio para consultar el listdo de tareas asociadas a un usuario.
     */
    @CrossOrigin(allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<Object> getTaskUser(@PathParam("idUsuario") Long idUsuario, @RequestHeader("Authorization") String token) throws Unauthorized, ErrorData {
        validator.validarToken(token);
        if(idUsuario == null) {
            throw new ErrorData("Error al consultar la información del usuario");
        }
        return ResponseEntity.ok(taskService.getTasksUser(idUsuario));
    }

    /**
     * Servicio para consultar una tarea por Id.
     */
    @GetMapping("/{idTarea}")
    public ResponseEntity<Object> getTaskId(@PathVariable("idTarea") Long idTarea, @RequestHeader("Authorization") String token) throws Unauthorized, ErrorData {
        validator.validarToken(token);
        if(idTarea == null) {
            throw new ErrorData("Error al consultar la información de la tarea");
        }
        return ResponseEntity.ok(taskService.getTaskId(idTarea));
    }

    /**
     * Servicio para actualizar una tarea.
     */
    @PutMapping
    public ResponseEntity<Object> updateTask(@RequestBody TaskDTO tareaDTO, @RequestHeader("Authorization") String token) throws Unauthorized {
        validator.validarToken(token);
        return ResponseEntity.ok(taskService.updateTask(tareaDTO));
    }

    /**
     * Servicio para eliminar una tarea.
     */
    @DeleteMapping("/{idTarea}")
    public ResponseEntity<Object> deleteTask(@PathVariable("idTarea") Long idTarea, @RequestHeader("Authorization") String token) throws Unauthorized, ErrorData {
        validator.validarToken(token);
        if(idTarea == null) {
            throw new ErrorData("Error al eliminar la información de la tarea");
        }
        return ResponseEntity.ok(taskService.deleteTask(idTarea));
    }
}
