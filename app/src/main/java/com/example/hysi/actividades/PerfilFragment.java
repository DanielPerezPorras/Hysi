package com.example.hysi.actividades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hysi.R;

public class PerfilFragment extends Fragment {

    Button bEmail, bListaObjetos;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void enlazarControles(View vista){
        bEmail = (Button) vista.findViewById(R.id.bEmail);
        bEmail.setOnClickListener(v -> irAEmail());

        bListaObjetos = (Button) vista.findViewById(R.id.btnListaObjetos);
        bListaObjetos.setOnClickListener(v -> irAObjetos());

        fragmentManager = getActivity().getSupportFragmentManager();

    }

    private void irAObjetos() {
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentoPrincipal, ListaObjetosPersonalesFragment.class, null)
                .commit();
    }

    private void irAEmail() {
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentoPrincipal, ConfigurarEmailFragment.class, null)
                .commit();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_perfil, container, false);

        enlazarControles(vista);

        return vista;
    }


}