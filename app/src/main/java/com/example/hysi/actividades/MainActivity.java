package com.example.hysi.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hysi.R;

public class MainActivity extends AppCompatActivity {

    private int selectionColor;
    private FragmentManager fragmentManager;

    private Button btnListaObjetos;
    private Button btnMapa;
    private Button btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarControles();
    }

    private void enlazarControles() {

        fragmentManager = getSupportFragmentManager();

        btnListaObjetos = findViewById(R.id.btnListaObjetos);
        btnMapa = findViewById(R.id.btnMapa);
        btnPerfil = findViewById(R.id.btnPerfil);

        btnListaObjetos.setOnClickListener(v -> mostrarListaObjetos());
        btnMapa.setOnClickListener(v -> mostrarMapa());
        btnPerfil.setOnClickListener(v -> mostrarPerfil());

        Button btnAnyadirAnuncio = findViewById(R.id.btnAnyadirAnuncio);
        btnAnyadirAnuncio.setOnClickListener(v -> ejecutarAnyadirAnuncio());

        selectionColor = getResources().getColor(R.color.orange, getTheme());
        mostrarListaObjetos();
    }

    private void mostrarListaObjetos() {
        limpiarSeleccion();
        btnListaObjetos.setBackgroundColor(selectionColor);
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentoPrincipal, ListaObjetosFragment.class, null)
                .commit();
    }

    private void mostrarMapa() {
        limpiarSeleccion();
        btnMapa.setBackgroundColor(selectionColor);
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentoPrincipal, MapaFragment.class, null)
                .commit();
    }

    private void mostrarPerfil() {
        limpiarSeleccion();
        btnPerfil.setBackgroundColor(selectionColor);
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentoPrincipal, PerfilFragment.class, null)
                .commit();
    }

    private void ejecutarAnyadirAnuncio() {
        Intent i = new Intent(this, AnyadirAnuncioActivity.class);
        startActivity(i);
    }

    private void limpiarSeleccion() {
        btnListaObjetos.setBackgroundColor(Color.TRANSPARENT);
        btnMapa.setBackgroundColor(Color.TRANSPARENT);
        btnPerfil.setBackgroundColor(Color.TRANSPARENT);
    }

}