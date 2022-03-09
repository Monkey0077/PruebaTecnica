package com.prueba.tecnica.models.dao;

import com.prueba.tecnica.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao extends CrudRepository<User, Long> {
    User findByUserName(String userName);
}
