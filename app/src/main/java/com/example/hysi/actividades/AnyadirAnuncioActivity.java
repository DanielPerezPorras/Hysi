package com.example.hysi.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hysi.R;
import com.example.hysi.modelo.Anuncio;
import com.example.hysi.modelo.Categoria;
import com.example.hysi.modelo.GeocodeUtils;

import java.io.IOException;
import java.util.ArrayList;

public class AnyadirAnuncioActivity extends Activity {

    private Spinner spinner;
    private EditText etTitulo;
    private EditText etDescripcion;
    private EditText etLoPerdiEn;
    private EditText etDejarEn;
    private TextView txtDireccionLoPerdiEn;
    private TextView txtDireccionDejarEn;
    private Spinner etCategoria;
    private Button btnAnyadir;

    private Toast error;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_anuncio);
        rellenarSpinner();
        enlazarControles();
    }

    public void rellenarSpinner(){

        ArrayList<Categoria> listaCategorias = Categoria.getCategorias();
        int length = listaCategorias.size();

        // Debemos llenar un array de strings con los nombres de las categorías
        String[] nombres = new String[length];
        for (int i = 0; i < length; i++) {
            nombres[i] = listaCategorias.get(i).getNombre();
        }

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nombres);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void enlazarControles(){
        etTitulo = findViewById(R.id.titulo_anyadir_anuncio);
        etDescripcion = findViewById(R.id.descripcion_anyadir_anuncio);
        etLoPerdiEn = findViewById(R.id.perdi_en_anyadir_anuncio);
        etDejarEn = findViewById(R.id.dejar_en_anyadir_anuncio);
        txtDireccionLoPerdiEn = findViewById(R.id.txtDireccionLoPerdiEn);
        txtDireccionDejarEn = findViewById(R.id.txtDireccionDejarEn);
        etCategoria = findViewById(R.id.spinner);
        btnAnyadir = findViewById(R.id.anyadir);
        btnAnyadir.setOnClickListener(v -> anyadir());

        etLoPerdiEn.setOnFocusChangeListener((v, b)
                -> GeocodeUtils.mostrarDireccionDesdeEditText(this, etLoPerdiEn, txtDireccionLoPerdiEn));
        etDejarEn.setOnFocusChangeListener((v, b)
                -> GeocodeUtils.mostrarDireccionDesdeEditText(this, etDejarEn, txtDireccionDejarEn));

        error = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    public void anyadir(){
        String loPerdiEn = etLoPerdiEn.getText().toString();
        String dejarEn = etDejarEn.getText().toString();
        try {
            if (GeocodeUtils.getAddressSync(this, loPerdiEn) == null
            || GeocodeUtils.getAddressSync(this, dejarEn) == null) {
                error.setText(R.string.error_calle_no_valida);
                error.show();
            } else {
                Anuncio a = Anuncio.crear(this,
                        etTitulo.getText().toString(),
                        etDescripcion.getText().toString(),
                        loPerdiEn, dejarEn,
                        etCategoria.getSelectedItem().toString() );
                irAPrincipal();
            }
        } catch (IOException ex) {
            error.setText(R.string.error_calle_no_comprobada);
            error.show();
        }
    }

    private void irAPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
