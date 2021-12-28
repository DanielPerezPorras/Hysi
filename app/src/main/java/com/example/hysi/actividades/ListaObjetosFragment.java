package com.example.hysi.actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.hysi.R;
import com.example.hysi.modelo.Anuncio;
import com.example.hysi.modelo.Categoria;
import java.util.ArrayList;


public class ListaObjetosFragment extends Fragment {

    Button bFiltrar;
    LinearLayout lObjetos, lGeneral;
    EditText tTexto;
    Spinner spin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void enlazarControles(View vista) {

        lObjetos = (LinearLayout) vista.findViewById(R.id.linear_objetos);
        tTexto = (EditText) vista.findViewById(R.id.txt_filtro);
        spin = (Spinner) vista.findViewById(R.id.spinner_filtro);

        bFiltrar = (Button) vista.findViewById(R.id.bFiltrar);
        bFiltrar.setOnClickListener(v -> realizarFiltrado());


    }

    private void realizarFiltrado() {
        //TODO: hacer filtrado

        lObjetos.removeAllViews();

        // Categoria.findById(Integer.valueOf(spin.getSelectedItem().toString())).getNombre()

        ArrayList<Anuncio> anuncios = Anuncio.listadoAnunciosFiltrado(spin.getSelectedItem().toString(),tTexto.getText().toString());
        for(int i = 0; i < anuncios.size();i++){
            Anuncio aux = anuncios.get(i);
            inflarObjeto(i,aux.getID(),aux.getTitulo(),aux.getAutor(),aux.getDescripcion(),aux.getLo_perdi_en(),aux.getDejar_en(),aux.getCategoria());
        }



    }

    public void rellenarSpinner(){

        ArrayList<Categoria> listaCategorias = Categoria.getCategorias();
        int length = listaCategorias.size();

        // Debemos llenar un array de strings con los nombres de las categorías
        String[] nombres = new String[length];
        for (int i = 0; i < length; i++) {
            nombres[i] = listaCategorias.get(i).getNombre();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombres);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

    }

    private void mostrarObjetos(){

        ArrayList<Anuncio> anuncios = Anuncio.listadoAnuncios();

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
        lGeneral = (LinearLayout) vi.findViewById(R.id.linearGeneralCorto);

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

        intent.putExtra("id",sID);
        intent.putExtra("autor",sAutor);
        intent.putExtra("titulo",sTitulo);
        intent.putExtra("descripcion",sDescripcion);
        intent.putExtra("perdi",sPerdi);
        intent.putExtra("dejar",sDejar);
        intent.putExtra("categoria",sCategoria);

        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_lista_objetos, container, false);

        enlazarControles(vista);
        rellenarSpinner();
        mostrarObjetos();

        return vista;

    }


}