package com.example.hysi.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hysi.R;
import com.example.hysi.modelo.Anuncio;
import com.example.hysi.modelo.GeocodeUtils;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.GeoApiContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class MapaFragment extends Fragment {

    private Usuario usuarioActual = (Usuario)SingletonMap.getInstance().get("session");

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        @Override
        public void onMapReady(GoogleMap googleMap) {

            Address addr = GeocodeUtils.getAddressSync(usuarioActual.getCalle());
            LatLng latlngUsuario = new LatLng(addr.getLatitude(), addr.getLongitude());

            ArrayList<Anuncio> anuncios = Anuncio.getLatitudLongitudTitulo();
            Map<LatLng, List<String>> chinchetas = new HashMap<>();
            for (Anuncio i : anuncios){
                LatLng pin = new LatLng(i.getLatitud(), i.getLongitud());
                if (chinchetas.containsKey(pin)) {
                    List<String> nuevaLista = chinchetas.get(pin);
                    nuevaLista.add(i.getTitulo());
                } else {
                    List<String> nuevaLista = new ArrayList<>();
                    nuevaLista.add(i.getTitulo());
                    chinchetas.put(pin, nuevaLista);
                }
            }

            for (LatLng newPushPin : chinchetas.keySet()){
                List<String> titles = chinchetas.get(newPushPin);
                StringJoiner joiner = new StringJoiner(", ");
                for (String s : titles) {
                    joiner.add(s);
                }
                googleMap.addMarker(new MarkerOptions().position(newPushPin).title(joiner.toString()));
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlngUsuario));
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(14));

        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mapa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}