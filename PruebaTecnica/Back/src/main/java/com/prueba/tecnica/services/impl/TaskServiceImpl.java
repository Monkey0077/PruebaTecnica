package com.prueba.tecnica.services.impl;

import com.prueba.tecnica.exceptions.Unauthorized;
import com.prueba.tecnica.models.dao.ITaskDao;
import com.prueba.tecnica.models.dao.IUserDao;
import com.prueba.tecnica.models.dao.IUserTaskDao;
import com.prueba.tecnica.models.dto.ResponseDTO;
import com.prueba.tecnica.models.dto.TaskDTO;
import com.prueba.tecnica.models.entity.Task;
import com.prueba.tecnica.models.entity.User;
import com.prueba.tecnica.models.entity.UserTask;
import com.prueba.tecnica.services.ITaskService;
import com.prueba.tecnica.validator.AuthValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ITaskDao tareaDao;

    @Autowired
    private IUserTaskDao usuarioTareaDao;

    @Autowired
    private IUserDao usuarioDao;

    /**
     * Método para crear una tarea.
     */
    @Override
    @Transactional
    public ResponseDTO<Task> createTask(TaskDTO taskDTO) {
        ResponseDTO<Task> response = new ResponseDTO<>();
        try {
            Optional<User> usuario = usuarioDao.findById(taskDTO.getIdUsuario());
            if (usuario.isPresent()) {
                Task tarea = new Task();
                tarea.setNombre(taskDTO.getNombre());
                tarea.setDescripcion(taskDTO.getDescripcion());
                tarea.setEstado(taskDTO.getEstado());
                tarea.setFechaCreacion(Calendar.getInstance().getTime());
                tarea.setFechaModificacion(Calendar.getInstance().getTime());
                tarea = tareaDao.save(tarea);
                guardarUsuarioTarea(tarea, taskDTO.getIdUsuario());
                response.setCodeStatus(HttpStatus.OK.value());
                response.setData(tarea);
            } else {
                response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                response.setMensajeError("El usuario asociado a la tarea no existe");
            }
        } catch (Exception e) {
            log.error("Error ", e);
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensajeError(e.toString());
        }
        return response;
    }

    /**
     * Método para guardar el registro en la entidad UsuarioTarea
     */
    private void guardarUsuarioTarea(Task task, Long idUsuario) {
        try {
            User usuario = new User();
            usuario.setIdUsuario(idUsuario);
            UserTask usuarioTarea = new UserTask();
            usuarioTarea.setTarea(task);
            usuarioTarea.setUsuario(usuario);
            usuarioTareaDao.save(usuarioTarea);
        } catch (Exception e) {
            log.error("Error ", e);
        }
    }

    /**
     * Método para consultar una tarea por Id.
     */
    @Override
    public ResponseDTO<Task> getTaskId(Long idTarea) {
        ResponseDTO<Task> response = new ResponseDTO<>();
        try {
            Optional<Task> tarea = tareaDao.findById(idTarea);
            if (tarea.isPresent()) {
                response.setCodeStatus(HttpStatus.OK.value());
                response.setData(tarea.get());
            } else {
                response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                response.setMensajeError("La tarea no existe");
            }
        } catch (Exception e) {
            log.error("Error ", e);
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensajeError(e.toString());
        }
        return response;
    }

    /**
     * Método para consultar el listdo de tareas asociadas a un usuario.
     */
    @Override
    public ResponseDTO<Task> getTasksUser(Long idUsuario) {
        ResponseDTO<Task> response = new ResponseDTO<>();
        try {
            List<Task> taskList = new ArrayList<>();
            Optional<User> usuario = usuarioDao.findById(idUsuario);
            if (usuario.isPresent()) {
                List<UserTask> usuarioTareaList = usuarioTareaDao.findByUsuario(usuario.get());
                usuarioTareaList.stream().forEach(usuarioTarea -> taskList.add(usuarioTarea.getTarea()));
                response.setCodeStatus(HttpStatus.OK.value());
                response.setListData(taskList);
            } else {
                response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                response.setMensajeError("El usuario no existe");
            }
        } catch (Exception e) {
            log.error("Error ", e);
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensajeError(e.toString());
        }
        return response;
    }

    /**
     * Método para actualizar una tarea.
     */
    @Override
    public ResponseDTO<Task> updateTask(TaskDTO taskDTO) {
        ResponseDTO<Task> response = new ResponseDTO<>();
        try {
            Optional<Task> tarea = tareaDao.findById(taskDTO.getIdTarea());
            if (tarea.isPresent()) {
                Task tareaUpdate = tarea.get();
                if (taskDTO.getNombre() != null) tareaUpdate.setNombre(taskDTO.getNombre());
                if (taskDTO.getDescripcion() != null) tareaUpdate.setDescripcion(taskDTO.getDescripcion());
                if (taskDTO.getEstado() != null) tareaUpdate.setEstado(taskDTO.getEstado());
                tareaUpdate.setFechaModificacion(Calendar.getInstance().getTime());
                tareaUpdate = tareaDao.save(tareaUpdate);
                response.setCodeStatus(HttpStatus.OK.value());
                response.setData(tareaUpdate);
            } else {
                response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                response.setMensajeError("La tarea no existe");
            }
        } catch (Exception e) {
            log.error("Error ", e);
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensajeError(e.toString());
        }
        return response;
    }

    /**
     * Método para borrar una tarea.
     */
    @Override
    @Transactional
    public ResponseDTO deleteTask(Long idTarea) {
        ResponseDTO response = new ResponseDTO<>();
        try {
            Optional<Task> tarea = tareaDao.findById(idTarea);
            if (tarea.isPresent()) {
                UserTask usuarioTarea = usuarioTareaDao.findByTarea(tarea.get());
                if (usuarioTarea != null) {
                    usuarioTareaDao.delete(usuarioTarea);
                    tareaDao.delete(tarea.get());
                    response.setCodeStatus(HttpStatus.OK.value());
                } else {
                    response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                    response.setMensajeError("El usuario no tiene asociada la tarea");
                }
            } else {
                response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                response.setMensajeError("La tarea no existe");
            }
        } catch (Exception e) {
            log.error("Error ", e);
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensajeError(e.toString());
        }
        return response;
    }
}
