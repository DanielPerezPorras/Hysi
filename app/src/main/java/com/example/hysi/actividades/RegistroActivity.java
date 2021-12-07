package com.example.hysi.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hysi.R;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

public class RegistroActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasenia1;
    private EditText etContrasenia2;
    private Toast error;

    private SingletonMap singletonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        enlazarControles();
        singletonMap = SingletonMap.getInstance();
    }

    private void enlazarControles() {

        etUsuario = findViewById(R.id.etUsuarioRegistro);
        etContrasenia1 = findViewById(R.id.etContraseniaRegistro);
        etContrasenia2 = findViewById(R.id.etContraseniaRegistro2);

        Button btnConfirmar = findViewById(R.id.btnRegistrarse);
        btnConfirmar.setOnClickListener(v -> validarRegistro());

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> irALogin());

        Context appCtxt = getApplicationContext();
        error = Toast.makeText(appCtxt, R.string.login_error, Toast.LENGTH_SHORT);

    }

    private void validarRegistro() {
        String nombreUsuario = etUsuario.getText().toString().replaceAll("\\s", "");
        String contrasenia1 = etContrasenia1.getText().toString();
        String contrasenia2 = etContrasenia2.getText().toString();
        if (nombreUsuario.isEmpty()) {
            error.setText(R.string.registro_error_usuario_vacio);
            error.show();
        } else if (contrasenia1.isEmpty() || contrasenia2.isEmpty()) {
            error.setText(R.string.registro_error_contrasenia_vacia);
            error.show();
        } else if (!contrasenia1.equals(contrasenia2)) {
            error.setText(R.string.registro_error_contrasenias);
            error.show();
        } else if (Usuario.findByUsuario(nombreUsuario) != null) {
            error.setText(R.string.registro_error_usuario_en_uso);
            error.show();
        } else {
            Usuario nuevoUsuario = Usuario.crear(nombreUsuario, contrasenia1);
            singletonMap.put("session", nuevoUsuario);
            irAPrincipal();
        }
    }

    private void irALogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void irAPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}