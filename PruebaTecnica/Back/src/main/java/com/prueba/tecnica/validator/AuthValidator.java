package com.prueba.tecnica.validator;

import com.prueba.tecnica.exceptions.Unauthorized;
import com.prueba.tecnica.models.dto.RequestLoginDTO;
import com.prueba.tecnica.models.entity.User;
import com.prueba.tecnica.security.JwtIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    private static final String CLIENT_CREDENTIALS="client_credentials";

    @Autowired
    private JwtIO jwtIO;

    AuthValidator(){}

    /** Metodo para validar que el grant_type sea correcto y que las credenciales no esten vacias */
    public void validate(RequestLoginDTO requestLoginDTO, String grantType) throws Unauthorized {
        if(grantType.isEmpty() || !grantType.equals(CLIENT_CREDENTIALS)){
            message("El campo gran_type no es valido");
        }

        if(requestLoginDTO.getUserName().isEmpty() || requestLoginDTO.getPassword().isEmpty()){
            message("Usuario y/o contraseña incorrectos");
        }
    }


    /** Metodo para validar que el token esté vigente */
    public void validarToken(String token) throws Unauthorized {
        boolean res = jwtIO.validateToken(token);
        if (res) {
            message("Ocurrio un error al validar el token");
        }
    }

    /** Metodo para retornar mensaje de error
     * @return*/
    public ResponseEntity<Object> message(String message) throws Unauthorized {
        throw new Unauthorized(message);
    }
}
