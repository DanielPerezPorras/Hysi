package com.example.hysi.actividades;


import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hysi.R;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

public class ConfigurarEmailFragment extends Fragment {

    EditText email1, email2;
    TextView tError;
    Button bIntroducirEmail;

    private void enlazarControles(View vista){

        email1 = (EditText) vista.findViewById(R.id.tEmail1);
        email2 = (EditText) vista.findViewById(R.id.tEmail2);

        tError = (TextView) vista.findViewById(R.id.tError);
        tError.setVisibility(View.INVISIBLE);

        bIntroducirEmail = (Button) vista.findViewById(R.id.bIntroducirEmail);
        bIntroducirEmail.setOnClickListener(v -> introducirEmail());
    }

    private void introducirEmail() {
        if(!email1.getText().toString().equals(email2.getText().toString()) || email1.getText().toString().isEmpty() || email2.getText().toString().isEmpty()){
            tError.setVisibility(View.VISIBLE);
        }else{
            Usuario usuarioActual = (Usuario)SingletonMap.getInstance().get("session");
            int id = usuarioActual.getId();
            String email = email1.getText().toString();
            Usuario.updateEmail(id,email);

            tError.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(),R.string.updateEmailCorrecto, Toast.LENGTH_SHORT).show();
        }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.activity_configurar_email, container, false);

        enlazarControles(vista);

        return vista;
    }


}