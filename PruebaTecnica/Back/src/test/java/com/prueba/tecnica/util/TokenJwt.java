package com.prueba.tecnica.util;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Component
public class TokenJwt {

    /** Metodo para generar un token */
    public String generateToken(Object src){
        String subject = GsonUtils.serializar(src);
        Signer signer = HMACSigner.newSHA256Signer("key_secret");
        TimeZone tz = TimeZone.getTimeZone("America/Bogota");
        ZonedDateTime zdt = ZonedDateTime.now(tz.toZoneId()).plusSeconds(3600);
        JWT jwt = new JWT()
                .setIssuer("www.company.org")
                .setIssuedAt(ZonedDateTime.now(tz.toZoneId()))
                .setSubject(subject)
                .setExpiration(zdt);
        return JWT.getEncoder().encode(jwt, signer);
    }
}
