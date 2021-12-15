package com.example.hysi.modelo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodeUtils {

    /**
     * Devuelve la latitud y longitud de una calle dada su dirección.
     * @param ctxt El contexto de la actividad llamante.
     * @param calle La dirección a convertir a coordenadas.
     * @return Un objeto LatLng con las coordenadas, o null si la calle no existe.
     * @throws IOException Si hay algún error al acceder al servicio de geocoding o
     *      calle es la cadena vacía.
     */
    public static LatLng coordenadasAPartirDeCalle(Context ctxt, String calle) throws IOException {
        if (calle != null && !calle.isEmpty()) {
            Geocoder geocoder = new Geocoder(ctxt, Locale.getDefault());
            List<Address> direcciones = geocoder.getFromLocationName(calle, 10);
            if (direcciones != null && direcciones.size() > 0) {
                for (Address addr : direcciones) {
                    Log.i("Hysi", addr.getAddressLine(0)
                            + " (" + addr.getLatitude() + ", " +  addr.getLongitude() + ")");
                }
                Address miDireccion = direcciones.get(0);
                return new LatLng(miDireccion.getLatitude(), miDireccion.getLongitude());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Devuelve los datos de una calle dada su dirección.
     * @param ctxt El contexto de la actividad llamante.
     * @param calle La dirección a convertir a coordenadas.
     * @return Un objeto Address con detalles de la calle, o null si la calle no existe.
     * @throws IOException Si hay algún error al acceder al servicio de geocoding o
     *      calle es la cadena vacía.
     */
    public static Address direccionAPartirDeCalle(Context ctxt, String calle) throws IOException {
        if (calle != null && !calle.isEmpty()) {
            Geocoder geocoder = new Geocoder(ctxt, Locale.getDefault());
            List<Address> direcciones = geocoder.getFromLocationName(calle, 10);
            if (direcciones != null && direcciones.size() > 0) {
                return direcciones.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getNombreDireccion(Address addr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= addr.getMaxAddressLineIndex(); i++) {
            builder.append(addr.getAddressLine(i)).append("\n");
        }
        return builder.toString();
    }

}
