package com.example.hysi.actividades;

import com.example.hysi.R;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AnuncioActivity extends AppCompatActivity {

    TextView autor, titulo, categoria, descripcion, perdi, dejar;
    Button contactar;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);
        enlazarControles();
        rellenarDatos();
    }

    private void enlazarControles() {

        titulo = findViewById(R.id.tTitulo);
        autor = findViewById(R.id.tAutor);
        descripcion = findViewById(R.id.tDescripcion);
        perdi = findViewById(R.id.tPerdi);
        dejar = findViewById(R.id.tDejar);
        categoria = findViewById(R.id.tCategoria);

        contactar = findViewById(R.id.bContactar);
        contactar.setOnClickListener(v -> contactarViaMail());

    }

    private void rellenarDatos() {

        titulo.setText(getIntent().getExtras().getString("titulo"));
        autor.setText(getIntent().getExtras().getString("autor"));
        descripcion.setText(getIntent().getExtras().getString("descripcion"));
        perdi.setText(getIntent().getExtras().getString("perdi"));
        dejar.setText(getIntent().getExtras().getString("dejar"));
        categoria.setText(getIntent().getExtras().getString("categoria"));
        id = getIntent().getExtras().getInt("id");

    }


    private void contactarViaMail() {
        //TODO: enviar un correo de yo a él

        // Defino mi Intent y hago uso del objeto ACTION_SEND
        Intent intent = new Intent(Intent.ACTION_SEND);

        String enviarasunto = "He encontrado tu \"" + getIntent().getExtras().getString("titulo") + "\"";

        String enviarcorreo = Usuario.getEmailByID(id);

        // Defino los Strings Email, Asunto y Mensaje con la función putExtra
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{enviarcorreo});
        intent.putExtra(Intent.EXTRA_SUBJECT, enviarasunto);
        //intent.putExtra(Intent.EXTRA_TEXT, enviarmensaje);

        // Establezco el tipo de Intent
        intent.setType("message/rfc822");

        // Lanzo el selector de cliente de Correo
        startActivity(Intent.createChooser(intent, "Elije un cliente de Correo:"));

    }

}


