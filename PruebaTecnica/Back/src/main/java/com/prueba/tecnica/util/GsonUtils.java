package com.prueba.tecnica.util;

import com.google.gson.Gson;

public class GsonUtils {

    /** Metodo para transformar un objeto a un json */
    public static String serializar(Object src){
        Gson gson = new Gson();
        return gson.toJson(src);
    }

    /** Metodo para transformar un json a un objeto */
    public static <D> D toObject(String json, Class<D> dClass){
        Gson gson = new Gson();
        return gson.fromJson(json, dClass);
    }

    /** Metodo para transformar un objeto a otro objeto en particular */
    public static <D> D toObject(Object src, Class<D> dClass){
        Gson gson = new Gson();
        String srcJson = gson.toJson(src);
        return gson.fromJson(srcJson, dClass);
    }
}
