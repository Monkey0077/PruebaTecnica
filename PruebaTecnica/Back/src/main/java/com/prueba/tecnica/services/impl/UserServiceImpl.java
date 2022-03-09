package com.prueba.tecnica.services.impl;

import com.prueba.tecnica.exceptions.Unauthorized;
import com.prueba.tecnica.models.dao.IUserDao;
import com.prueba.tecnica.models.dto.RequestLoginDTO;
import com.prueba.tecnica.models.dto.ResponseDTO;
import com.prueba.tecnica.models.dto.UserLoggedDTO;
import com.prueba.tecnica.models.entity.User;
import com.prueba.tecnica.security.JwtIO;
import com.prueba.tecnica.services.IUserService;
import com.prueba.tecnica.util.DateUtils;
import com.prueba.tecnica.validator.AuthValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao usuarioDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthValidator validator;

    @Autowired
    private JwtIO jwtIO;

    @Autowired
    private DateUtils dateUtils;

    @Value("${jwt.token.expires-in}")
    private int EXPIRES_IN;

    /**
     * Método para crear un usuario.
     */
    @Override
    public ResponseDTO<UserLoggedDTO> createUser(User usuario) {
        ResponseDTO<UserLoggedDTO> response = new ResponseDTO<>();
        try {
            User usuarioExistente = usuarioDao.findByUserName(usuario.getUserName());
            if (usuarioExistente == null) {
                String passEncoder = passwordEncoder.encode(usuario.getPassword());
                usuario.setPassword(passEncoder);
                usuario.setFechaCreacion(Calendar.getInstance().getTime());
                User usuarioNuevo = usuarioDao.save(usuario);
                usuarioNuevo.setPassword("");
                UserLoggedDTO jwt = UserLoggedDTO.builder()
                        .tokenType("bearer")
                        .accessToken(jwtIO.generateToken(usuarioNuevo))
                        .issuedAt(dateUtils.getDateMillis()+"")
                        .user(usuarioNuevo.getPrimerNombre() +" "+usuarioNuevo.getSegundoNombre() + " "+usuarioNuevo.getPrimerApellido() +" "+usuarioNuevo.getSegundoApellido())
                        .userId(usuarioNuevo.getIdUsuario())
                        .expiresIn(EXPIRES_IN)
                        .build();
                response.setCodeStatus(HttpStatus.OK.value());
                response.setData(jwt);
            } else {
                response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                response.setMensajeError("El usuario ya se encuentra registrado");
            }
        } catch (Exception e) {
            log.error("Error ", e);
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensajeError(e.toString());
        }
        return response;
    }

    /**
     * Método para ingresar a la aplicación.
     */
    @Override
    public ResponseDTO<UserLoggedDTO> loginUser(RequestLoginDTO loginUsuarioDTO) {
        ResponseDTO<UserLoggedDTO> response = new ResponseDTO<>();
        try {
            User usuario = usuarioDao.findByUserName(loginUsuarioDTO.getUserName());
            if (usuario != null) {
                if (passwordEncoder.matches(loginUsuarioDTO.getPassword(), usuario.getPassword())) {
                    usuario.setPassword("");
                    UserLoggedDTO jwt = UserLoggedDTO.builder()
                            .tokenType("bearer")
                            .accessToken(jwtIO.generateToken(usuario))
                            .issuedAt(dateUtils.getDateMillis()+"")
                            .user(usuario.getPrimerNombre() + " "+usuario.getPrimerApellido())
                            .userId(usuario.getIdUsuario())
                            .expiresIn(EXPIRES_IN)
                            .build();
                    response.setCodeStatus(HttpStatus.OK.value());
                    response.setData(jwt);
                } else {
                    response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                    response.setMensajeError("El usuario y/o contraseña son incorrectos");
                }
            } else {
                response.setCodeStatus(HttpStatus.NOT_ACCEPTABLE.value());
                response.setMensajeError("El usuario no se encuentra registrado");
            }
        } catch (Exception e) {
            log.error("Error ", e);
            response.setCodeStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMensajeError(e.toString());
        }
        return response;
    }

}
