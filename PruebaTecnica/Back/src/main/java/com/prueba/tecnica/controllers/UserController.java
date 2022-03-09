package com.prueba.tecnica.controllers;

import com.prueba.tecnica.exceptions.Unauthorized;
import com.prueba.tecnica.models.dto.RequestLoginDTO;
import com.prueba.tecnica.models.dto.ResponseDTO;
import com.prueba.tecnica.models.dto.UserLoggedDTO;
import com.prueba.tecnica.models.entity.User;
import com.prueba.tecnica.services.IUserService;
import com.prueba.tecnica.validator.AuthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller con los servicios asociados al usuario.
 */
@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/api/usuario")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthValidator validator;

    /**
     * Servicio para guardar un usuario en el sistema.
     */
    @PostMapping
    public ResponseDTO<UserLoggedDTO> createUser(@Valid @RequestBody User usuario) {
        return userService.createUser(usuario);
    }

    /**
     * Servicio para iniciar sesion.
     */
    @PostMapping("/user")
    public ResponseDTO<UserLoggedDTO> loginUser(@RequestBody RequestLoginDTO request, @RequestParam("grant_type")String grantType) throws Unauthorized {
        validator.validate(request, grantType);
        return userService.loginUser(request);
    }

}
