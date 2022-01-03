package com.example.hysi.actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hysi.R;
import com.example.hysi.modelo.Anuncio;
import com.example.hysi.modelo.Categoria;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

import java.util.ArrayList;

public class ListaObjetosPersonalesFragment extends Fragment {

    LinearLayout lObjetos;




    private void enlazarControles(View vista){
        lObjetos = (LinearLayout) vista.findViewById(R.id.linear_objetosP);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_lista_objetos_personales, container, false);

        enlazarControles(vista);
        mostrarObjetos();

        return vista;
    }

    private void mostrarObjetos(){

        Usuario usuarioActual = (Usuario) SingletonMap.getInstance().get("session");
        int id = usuarioActual.getId();

        ArrayList<Anuncio> anuncios = Anuncio.listadoAnunciosByID(id);

        for(int i = 0; i < anuncios.size();i++){
            Anuncio aux = anuncios.get(i);
            inflarObjeto(i,aux.getID(),aux.getTitulo(),aux.getAutor(),aux.getDescripcion(),aux.getLo_perdi_en(),aux.getDejar_en(),aux.getCategoria());
        }

    }

    @SuppressLint("ResourceAsColor")
    private void inflarObjeto(int i,int id, String sTitulo, String sAutor, String sDescripcion, String sPerdi, String sDejar, String sCategoria){

        View vi = getLayoutInflater().inflate(R.layout.anuncio_corto,null);

        TextView titulo = (TextView) vi.findViewById(R.id.tTituloCorto);
        TextView autor = (TextView) vi.findViewById(R.id.tAutorCorto);
        TextView categoria = (TextView) vi.findViewById(R.id.tCategoriaCorto);
        LinearLayout lGeneral = (LinearLayout) vi.findViewById(R.id.linearGeneralCorto);

        titulo.setText(sTitulo);
        autor.setText(sAutor);
        categoria.setText(sCategoria);

        if(i % 2 == 0)
            lGeneral.setBackgroundColor(Color.parseColor("#FFFCE792"));
        else
            lGeneral.setBackgroundColor(Color.parseColor("#FFFDED"));

        vi.setOnClickListener(v -> irAnuncio( id,sTitulo,  sAutor,  sDescripcion,  sPerdi,  sDejar,  sCategoria));



        lObjetos.addView(vi);

    }

    private void irAnuncio(int sID,String sTitulo, String sAutor, String sDescripcion, String sPerdi, String sDejar, String sCategoria) {
        Intent intent = new Intent(getContext(), AnuncioActivity.class);

        boolean vengoDePerfil = true;

        intent.putExtra("vengoDePerfil",vengoDePerfil);
        intent.putExtra("id",sID);
        intent.putExtra("autor",sAutor);
        intent.putExtra("titulo",sTitulo);
        intent.putExtra("descripcion",sDescripcion);
        intent.putExtra("perdi",sPerdi);
        intent.putExtra("dejar",sDejar);
        intent.putExtra("categoria",sCategoria);

        startActivity(intent);
    }



}