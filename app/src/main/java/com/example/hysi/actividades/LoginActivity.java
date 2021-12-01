package com.example.hysi.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hysi.R;
import com.example.hysi.modelo.BaseDatos;


public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasenia;
    private Context appCtxt;
    private Toast error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        enlazarControles();
    }

    private void enlazarControles() {

        etUsuario = findViewById(R.id.etUsuario);
        etContrasenia = findViewById(R.id.etContrasenia);

        Button btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(v -> validarLogin());

        Button btnRegistrate = findViewById(R.id.btnRegistrate);
        btnRegistrate.setOnClickListener(v -> irARegistro());

        appCtxt = getApplicationContext();
        error = Toast.makeText(appCtxt, R.string.login_error, Toast.LENGTH_SHORT);

    }

    private void validarLogin() {
        String usuario = etUsuario.getText().toString();
        String contrasenia = etContrasenia.getText().toString();
        if (false) {
            // hacer el login
        } else {
            error.show();
        }
        BaseDatos.consultaPrueba();
    }

    private void irARegistro() {

    }

}