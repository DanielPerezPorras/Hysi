package com.example.hysi.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hysi.R;
import com.example.hysi.modelo.GeocodeUtils;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

import java.io.IOException;

public class RegistroActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasenia1;
    private EditText etContrasenia2;
    private EditText etCalle;
    private TextView txtDireccion;
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
        etCalle = findViewById(R.id.etCalleRegistro);
        txtDireccion = findViewById(R.id.txtDireccionRegistro);

        etCalle.setOnFocusChangeListener((v, b)
                -> GeocodeUtils.mostrarDireccionDesdeEditText(this, etCalle, txtDireccion));

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
        String calle = etCalle.getText().toString();
        if (nombreUsuario.isEmpty()) {
            error.setText(R.string.registro_error_usuario_vacio);
            error.show();
        } else if (contrasenia1.isEmpty() || contrasenia2.isEmpty()) {
            error.setText(R.string.registro_error_contrasenia_vacia);
            error.show();
        } else if (calle.isEmpty()) {
            error.setText(R.string.registro_error_calle_vacia);
            error.show();
        } else if (!contrasenia1.equals(contrasenia2)) {
            error.setText(R.string.registro_error_contrasenias);
            error.show();
        } else if (Usuario.findByUsuario(nombreUsuario) != null) {
            error.setText(R.string.registro_error_usuario_en_uso);
            error.show();
        } else {
            try {
                if (GeocodeUtils.getAddressSync(this, calle) == null) {
                    error.setText(R.string.error_calle_no_valida);
                    error.show();
                } else {
                    Usuario nuevoUsuario = Usuario.crear(nombreUsuario, contrasenia1, calle);
                    singletonMap.put("session", nuevoUsuario);
                    irAPrincipal();
                }
            } catch (IOException e) {
                error.setText(R.string.error_calle_no_comprobada);
                error.show();
            }
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