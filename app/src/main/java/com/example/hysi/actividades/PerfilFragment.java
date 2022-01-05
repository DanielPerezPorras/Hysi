package com.example.hysi.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.hysi.R;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

public class PerfilFragment extends Fragment {

    private TextView txtUsername, txtEmail, txtCalle;
    private Button bEmail, bListaObjetos;
    private Usuario usuarioActual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        enlazarControles(vista);
        usuarioActual = (Usuario)SingletonMap.getInstance().get("session");
        mostrarDatosUsuario();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        mostrarDatosUsuario();
    }

    private void enlazarControles(View vista) {
        txtUsername = vista.findViewById(R.id.txtUsername);
        txtEmail = vista.findViewById(R.id.txtEmail);
        txtCalle = vista.findViewById(R.id.txtCalle);

        bEmail = vista.findViewById(R.id.bEmail);
        bEmail.setOnClickListener(v -> irAEmail());

        bListaObjetos = vista.findViewById(R.id.btnListaObjetos);
        bListaObjetos.setOnClickListener(v -> irAObjetos());
    }

    private void mostrarDatosUsuario() {
        txtUsername.setText(usuarioActual.getUsuario());
        txtCalle.setText(usuarioActual.getCalle());
        String email = usuarioActual.getEmail();
        if (email != null) {
            txtEmail.setText(email);
        } else {
            txtEmail.setText(R.string.perfil_email_no_configurado);
        }
    }

    private void irAObjetos() {
        Intent intent = new Intent(getContext(), ListaObjetosPersonalesActivity.class);
        startActivity(intent);
    }

    private void irAEmail() {
        Intent intent = new Intent(getContext(), ConfigurarEmailActivity.class);
        startActivity(intent);
    }

}