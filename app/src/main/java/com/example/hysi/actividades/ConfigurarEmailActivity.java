package com.example.hysi.actividades;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hysi.R;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

public class ConfigurarEmailActivity extends AppCompatActivity {

    private EditText etEmail1, etEmail2;
    private Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_email);
        enlazarControles();
        usuarioActual = (Usuario)SingletonMap.getInstance().get("session");
    }

    private void enlazarControles() {
        etEmail1 = findViewById(R.id.tEmail1);
        etEmail2 = findViewById(R.id.tEmail2);
        Button btnIntroducirEmail = findViewById(R.id.bIntroducirEmail);
        btnIntroducirEmail.setOnClickListener(v -> introducirEmail());
    }

    private void introducirEmail() {
        String email1 = etEmail1.getText().toString();
        String email2 = etEmail2.getText().toString();
        if (email1.isEmpty() || email2.isEmpty()) {
            Toast.makeText(this, R.string.error_email_vacio, Toast.LENGTH_SHORT).show();
        } else if (!email1.equals(email2)) {
            Toast.makeText(this, R.string.error_email_no_coincide, Toast.LENGTH_SHORT).show();
        } else {
            usuarioActual.updateEmail(email1);
            Toast.makeText(this, R.string.updateEmailCorrecto, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}