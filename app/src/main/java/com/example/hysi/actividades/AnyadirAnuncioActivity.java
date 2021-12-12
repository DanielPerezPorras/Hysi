package com.example.hysi.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hysi.R;
import com.example.hysi.modelo.Categoria;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

import java.util.ArrayList;

public class AnyadirAnuncioActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;

    ArrayList<Categoria> listaCategorias = new ArrayList<>(Categoria.getCategorias());
    int cont = 0;
    private static final String[] paths = new String[Categoria.getCategorias().size()];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_anuncio);

        /*
         * Necesito un String[] con los nombres de las categorias
         * */
        ArrayList<Categoria> listaCategorias = Categoria.getCategorias();

        int length = listaCategorias.size();
        for (int i=0;i<length;i++) {
            System.out.println(listaCategorias.get(i).getNombre());
            paths[cont] = listaCategorias.get(i).getNombre();
            cont++;
        }
        System.out.println(paths.length);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnyadirAnuncioActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
