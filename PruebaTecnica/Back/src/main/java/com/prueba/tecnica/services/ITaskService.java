package com.prueba.tecnica.services;

import com.prueba.tecnica.exceptions.Unauthorized;
import com.prueba.tecnica.models.dto.ResponseDTO;
import com.prueba.tecnica.models.dto.TaskDTO;
import com.prueba.tecnica.models.entity.Task;


public interface ITaskService {
    ResponseDTO<Task> createTask(TaskDTO tareaDTO);
    ResponseDTO<Task> getTaskId(Long idTarea);
    ResponseDTO<Task> getTasksUser(Long idUsuario);
    ResponseDTO<Task> updateTask(TaskDTO tareaDTO);
    ResponseDTO deleteTask(Long idTarea);
}
