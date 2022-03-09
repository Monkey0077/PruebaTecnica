package com.prueba.tecnica.models.dao;

import com.prueba.tecnica.models.entity.Task;
import com.prueba.tecnica.models.entity.User;
import com.prueba.tecnica.models.entity.UserTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserTaskDao extends CrudRepository<UserTask, Long> {
    List<UserTask> findByUsuario(User user);
    UserTask findByTarea(Task task);
}
