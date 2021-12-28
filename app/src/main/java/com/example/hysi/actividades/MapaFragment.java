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

            ArrayList<Anuncio> anuncios = Anuncio.getLatitudLongitudTitulo();

            Address addr = GeocodeUtils.getAddressSync(usuarioActual.getCalle());
            LatLng latlngUsuario = new LatLng(addr.getLatitude(), addr.getLongitude());

            for (Anuncio i : anuncios){
                LatLng newPushPin = new LatLng(i.getLatitud(), i.getLongitud());
                googleMap.addMarker(new MarkerOptions().position(newPushPin).title(i.getTitulo()));
                System.out.println("Latitud:" + i.getLatitud() + " Longitud: " + i.getLongitud());
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