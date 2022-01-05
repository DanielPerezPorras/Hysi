package com.example.hysi.actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hysi.R;
import com.example.hysi.modelo.Anuncio;
import com.example.hysi.modelo.Categoria;
import com.example.hysi.modelo.SingletonMap;
import com.example.hysi.modelo.Usuario;

import java.util.ArrayList;

public class ListaObjetosPersonalesActivity extends AppCompatActivity {

    private LinearLayout lObjetos;
    private Usuario usuarioActual;
    private int resolvedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lista_objetos_personales);
        enlazarControles();
        usuarioActual = (Usuario)SingletonMap.getInstance().get("session");
        resolvedColor = getResources().getColor(R.color.orange_lighter, getTheme());
        mostrarObjetos();
    }

    private void enlazarControles(){
        lObjetos = findViewById(R.id.linear_objetosP);
    }

    private void mostrarObjetos(){
        int id = usuarioActual.getId();
        ArrayList<Anuncio> anuncios = Anuncio.listadoAnunciosByID(id);
        for(int i = 0; i < anuncios.size();i++){
            Anuncio aux = anuncios.get(i);
            inflarObjeto(aux.getID(), aux.getTitulo(), aux.getAutor(), aux.getDescripcion(),
                    aux.getLo_perdi_en(), aux.getDejar_en(), aux.getCategoria());
        }
    }

    @SuppressLint("ResourceAsColor")
    private void inflarObjeto(int id, String sTitulo, String sAutor, String sDescripcion, String sPerdi, String sDejar, String sCategoria){

        View vi = getLayoutInflater().inflate(R.layout.anuncio_corto,null);

        TextView titulo = vi.findViewById(R.id.tTituloCorto);
        TextView autor = vi.findViewById(R.id.tAutorCorto);
        TextView categoria = vi.findViewById(R.id.tCategoriaCorto);
        TextView yaEncontrado = vi.findViewById(R.id.tYaEncontrado);
        LinearLayout lGeneral = vi.findViewById(R.id.linearGeneralCorto);

        titulo.setText(sTitulo);
        autor.setText(sAutor);
        categoria.setText(sCategoria);
        if (Anuncio.estaEncontrado(id) == 0) {
            lGeneral.removeView(yaEncontrado);
        } else {
            lGeneral.setBackgroundTintList(ColorStateList.valueOf(resolvedColor));
        }

        vi.setOnClickListener(v -> irAnuncio(id, sTitulo, sAutor, sDescripcion, sPerdi, sDejar, sCategoria));

        lObjetos.addView(vi);

    }

    private void irAnuncio(int sID,String sTitulo, String sAutor, String sDescripcion, String sPerdi, String sDejar, String sCategoria) {
        Intent intent = new Intent(this, AnuncioActivity.class);
        intent.putExtra("vengoDePerfil", true);
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