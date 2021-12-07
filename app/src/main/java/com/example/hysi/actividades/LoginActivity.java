package com.example.hysi.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hysi.R;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;


public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasenia;
    private Toast error;
    private SingletonMap singletonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        enlazarControles();
        singletonMap = SingletonMap.getInstance();
    }

    private void enlazarControles() {

        etUsuario = findViewById(R.id.etUsuario);
        etContrasenia = findViewById(R.id.etContrasenia);

        Button btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(v -> validarLogin());

        Button btnRegistrate = findViewById(R.id.btnRegistrate);
        btnRegistrate.setOnClickListener(v -> irARegistro());

        Context appCtxt = getApplicationContext();
        error = Toast.makeText(appCtxt, R.string.login_error, Toast.LENGTH_SHORT);

    }

    private void validarLogin() {
        String nombreUsuario = etUsuario.getText().toString();
        String contrasenia = etContrasenia.getText().toString();
        Usuario usuario = Usuario.findByUsuarioAndPassword(nombreUsuario, contrasenia);
        if (usuario != null) {
            singletonMap.put("session", usuario);
            irAPrincipal();
        } else {
            error.show();
        }
    }

    private void irARegistro() {
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
        finish();
    }

    private void irAPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}