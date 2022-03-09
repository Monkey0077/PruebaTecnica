package com.prueba.tecnica.services;

import com.prueba.tecnica.models.dto.RequestLoginDTO;
import com.prueba.tecnica.models.dto.ResponseDTO;
import com.prueba.tecnica.models.dto.UserLoggedDTO;
import com.prueba.tecnica.models.entity.User;

public interface IUserService {
    ResponseDTO<UserLoggedDTO> createUser(User user);
    ResponseDTO<UserLoggedDTO> loginUser(RequestLoginDTO request);
}
