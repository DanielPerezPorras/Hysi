package com.example.hysi.modelo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hysi.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.GeoApiContext;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class GeocodeUtils {

    private static GeoApiContext geoctxt = (GeoApiContext)SingletonMap.getInstance().get("geoapi");

    /**
     * Devuelve los datos de una calle dada su dirección.
     * @param ctxt El contexto de la actividad llamante.
     * @param calle La dirección a convertir a coordenadas.
     * @return Un objeto Address con detalles de la calle, o null si la calle no existe.
     * @throws IOException Si hay algún error al acceder al servicio de geocoding o
     *      calle es la cadena vacía.
     */
    public static Address getAddressSync(Context ctxt, String calle) throws IOException {
        if (calle != null && !calle.isEmpty()) {
            Geocoder geocoder = new Geocoder(ctxt, Locale.getDefault());
            List<Address> direcciones = geocoder.getFromLocationName(calle, 1);
            if (direcciones != null && direcciones.size() > 0) {
                return direcciones.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Devuelve los datos de una calle dada su dirección (asíncronamente)
     * @param ctxt El contexto de la actividad llamante.
     * @param calle La dirección a convertir a coordenadas.
     * @param callback Método a ejecutar cuando se obtiene respuesta de éxito,
     *                 recibe como parámetro la dirección
     * @param callbackError Método a ejecutar en caso de error del geocoder
     */
    public static void getAddress(Context ctxt, String calle,
        Consumer<Address> callback, Consumer<IOException> callbackError) {
        if (calle != null && !calle.isEmpty()) {
            new Thread(
                () -> {
                    try {
                        Geocoder geocoder = new Geocoder(ctxt, Locale.getDefault());
                        List<Address> direcciones = geocoder.getFromLocationName(calle, 1);
                        if (direcciones != null && direcciones.size() > 0) {
                            callback.accept(direcciones.get(0));
                        } else {
                            callback.accept(null);
                        }
                    } catch (IOException ex) {
                        callbackError.accept(ex);
                    }
                }
            ).start();
        }
    }

    public static String getNombreDireccion(Address addr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= addr.getMaxAddressLineIndex(); i++) {
            builder.append(addr.getAddressLine(i)).append("\n");
        }
        return builder.toString();
    }

    public static void mostrarDireccionDesdeEditText(Context ctxt, EditText editText, TextView target) {
        String calle = editText.getText().toString();
        getAddress(ctxt, calle,
            (address) -> {
                if (address != null) {
                    target.setText(GeocodeUtils.getNombreDireccion(address));
                } else {
                    target.setText(R.string.error_calle_no_valida);
                }
            }, (exception) -> {
                target.setText(R.string.error_calle_no_comprobada);
            }
        );
    }

}
