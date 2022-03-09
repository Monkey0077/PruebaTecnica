package com.prueba.tecnica.security;

import com.prueba.tecnica.util.GsonUtils;
import io.fusionauth.jwt.JWTUtils;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Component
public class JwtIO {

    @Value("${jwt.token.secret:secret}")
    private String SECRET;

    @Value("${jwt.timezone:UTC}")
    private String TIMEZONE;

    @Value("${jwt.token.expires-in:3600}")
    private int EXPIRES_IN;

    @Value("${jwt.issuer:none}")
    private String ISSUER;

    /** Metodo para generar un token */
    public String generateToken(Object src){
        String subject = GsonUtils.serializar(src);
        Signer signer = HMACSigner.newSHA256Signer(SECRET);
        TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
        ZonedDateTime zdt = ZonedDateTime.now(tz.toZoneId()).plusSeconds(EXPIRES_IN);
        JWT jwt = new JWT()
                .setIssuer(ISSUER)
                .setIssuedAt(ZonedDateTime.now(tz.toZoneId()))
                .setSubject(subject)
                .setExpiration(zdt);
        return JWT.getEncoder().encode(jwt, signer);
    }

    /** Metodo para validar el token */
    public boolean validateToken(String encodedJWT){
        JWT jwt = jwt(encodedJWT);
        if(jwt == null) return true;
        return jwt.isExpired();
    }

    /** Metodo para obtener la informacion agregada al token en el payload */
    public String getPayload(String encodedJWT){
        JWT jwt = jwt(encodedJWT);
        return jwt.subject;
    }

    /** Metodo para validar que el token este correcto y vigente */
    private JWT jwt(String encodedJWT){
        try {
            JWT jwt = JWTUtils.decodePayload(encodedJWT);
            return jwt;
        }catch (Exception e){
            return null;
        }
    }
}
