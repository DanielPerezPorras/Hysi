package com.example.hysi.modelo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class GeocodeUtils {

    /**
     * Devuelve la latitud y longitud de una calle dada su dirección.
     * @param ctxt El contexto de la actividad llamante.
     * @param calle La dirección a convertir a coordenadas.
     * @return Un objeto LatLng con las coordenadas, o null si la calle no existe.
     * @throws IOException Si hay algún error al acceder al servicio de geocoding.
     */
    public static LatLng coordenadasAPartirDeCalle(Context ctxt, String calle) throws IOException {
        Geocoder geocoder = new Geocoder(ctxt);
        List<Address> direcciones = geocoder.getFromLocationName(calle, 1);
        if (direcciones != null && direcciones.size() > 0) {
            Address miDireccion = direcciones.get(0);
            return new LatLng(miDireccion.getLatitude(), miDireccion.getLongitude());
        } else {
            return null;
        }
    }

}
