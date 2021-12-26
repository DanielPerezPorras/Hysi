package com.example.hysi.modelo;

import com.google.maps.GeoApiContext;

import java.util.HashMap;

/**
 * Singleton que puede almacenar objetos de diferentes clases.
 * Los objetos se pueden acceder como si fuera un diccionario.
 */
public class SingletonMap extends HashMap<String, Object> {

    private static class SingletonHolder {
        private static final SingletonMap ourInstance = new SingletonMap();
    }

    /**
     * Constructor para inicializar los objetos
     */
    private SingletonMap() {
        // "session": almacena el usuario que tiene la sesión abierta
        put("session", null);

        put("geoapi", new GeoApiContext.Builder()
                .apiKey("AIzaSyBTjMvJmg0Y23keInraxHlBGUS_GbhyIfw")
                .build());
    }

    public static SingletonMap getInstance() {
        return SingletonHolder.ourInstance;
    }

}
