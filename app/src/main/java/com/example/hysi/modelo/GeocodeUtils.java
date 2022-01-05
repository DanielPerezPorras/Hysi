package com.example.hysi.modelo;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hysi.R;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.io.IOException;
import java.util.Locale;
import java.util.function.Consumer;

public class GeocodeUtils {

    private static GeoApiContext geoctxt = (GeoApiContext)SingletonMap.getInstance().get("geoapi");

    /**
     * Devuelve los datos de una calle dada su dirección.
     * @param calle La dirección a convertir a coordenadas.
     * @return Un objeto Address con detalles de la calle, o null si la calle no existe.
     */
    public static Address getAddressSync(String calle) {
        if (calle != null && !calle.isEmpty()) {
            try {
                GeocodingResult[] results = GeocodingApi.geocode(geoctxt, calle).await();
                if (results != null && results.length > 0) {
                    return geocodingResultToAddress(results[0]);
                } else {
                    return null;
                }
            } catch (IOException | ApiException | InterruptedException ex) {
                throw new GeocodingException(ex);
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
        Consumer<Address> callback, Consumer<Exception> callbackError) {
        if (calle != null && !calle.isEmpty()) {
            Activity activity = ((Activity)ctxt);
            new Thread(
                () -> {
                    try {
                        GeocodingResult[] results = GeocodingApi.geocode(geoctxt, calle).await();
                        activity.runOnUiThread(
                            () -> {
                                if (results != null && results.length > 0) {
                                    callback.accept(geocodingResultToAddress(results[0]));
                                } else {
                                    callback.accept(null);
                                }
                            }
                        );
                    } catch (IOException | ApiException | InterruptedException ex) {
                        activity.runOnUiThread(
                            () -> {
                                callbackError.accept(ex);
                            }
                        );
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
                exception.printStackTrace();
            }
        );
    }

    private static Address geocodingResultToAddress(GeocodingResult res) {
        LatLng location = res.geometry.location;
        Address addr = new Address(Locale.getDefault());
        addr.setLatitude(location.lat);
        addr.setLongitude(location.lng);
        addr.setAddressLine(0, res.formattedAddress);
        return addr;
    }

}
