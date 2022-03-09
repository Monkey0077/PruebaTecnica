package com.prueba.tecnica.models.dao;

import com.prueba.tecnica.models.entity.Task;
import org.springframework.data.repository.CrudRepository;


public interface ITaskDao extends CrudRepository<Task, Long> {
}
