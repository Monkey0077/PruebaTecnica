package com.prueba.tecnica.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateUtils {

    @Value("${jwt.timezone}")
    private String TIMEZONE;

    /** Metodo para retornar la fecha actual con formato */
    private SimpleDateFormat simpleDateFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        return sdf;
    }

    /** Metodo para obtener la fecha formateada en milisegundos */
    public long getDateMillis(){
        Date now = new Date();
        String strDate = simpleDateFormat().format(now);
        Date strNow = new Date();
        try {
            strNow = simpleDateFormat().parse(strDate);
        }catch (ParseException e){}
        return strNow.getTime();
    }
}
