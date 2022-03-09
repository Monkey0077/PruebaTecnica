package com.prueba.tecnica.security;


import com.prueba.tecnica.exceptions.Unauthorized;
import com.prueba.tecnica.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class InterceptorJwtIO implements HandlerInterceptor {

    @Value("${jwt.token.auth.path}")
    private String AUTH_PATH;

    @Value("#{'${jwt.excluded.paht}'.split(',')}")
    private List<String> excluded;

    @Autowired
    private JwtIO jwtIO;

    @Autowired
    AuthValidator validator;

    /** Metodo para validar que un usuario esta autenticado y se esta enviando correctamente el token */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Unauthorized {
        boolean validate = false;
        String uri = request.getRequestURI();
        if(uri.equals(AUTH_PATH)){
            validate = true;
        }
        if(!validate && request.getHeader("Authorization") != null && !request.getHeader("Authorization").isEmpty()) {
            String token = request.getHeader("Authorization").replace("Bearer", "");
            validate = !jwtIO.validateToken(token);
        } else if(!validate && !excluded(uri)) {
            if(request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty()) {
                validate = true;
            }
        }

        if(!validate){
            throw new Unauthorized("El token no es valido");
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return true;
    }

    /** Metodo para validar rutas protegidas */
    private boolean excluded(String path){
        boolean result = false;
        for(String exc:excluded){
            if(!exc.equals("#") && exc.equals(path)){
                result = true;
            }
        }
        return result;
    }
}
