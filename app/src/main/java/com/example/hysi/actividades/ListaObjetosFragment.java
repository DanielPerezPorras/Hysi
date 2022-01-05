package com.example.hysi.actividades;

import android.content.Intent;
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

    Button bFiltrarNombre, bFiltrarCategoria;
    LinearLayout lObjetos, lGeneral;
    EditText tTexto;
    Spinner spin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_lista_objetos, container, false);
        enlazarControles(vista);
        rellenarSpinner();
        mostrarObjetos();
        return vista;
    }

    private void enlazarControles(View vista) {

        lObjetos = vista.findViewById(R.id.linear_objetos);
        tTexto = vista.findViewById(R.id.txt_filtro);
        spin = vista.findViewById(R.id.spinner_filtro);

        bFiltrarCategoria = vista.findViewById(R.id.bFiltrarAmbos);
        bFiltrarCategoria.setOnClickListener(v -> realizarFiltradoCompleto());

        bFiltrarNombre = vista.findViewById(R.id.bFiltrarNombres);
        bFiltrarNombre.setOnClickListener(v -> realizarFiltrado());

    }

    private void realizarFiltradoCompleto() {
        lObjetos.removeAllViews();

        ArrayList<Anuncio> anuncios = Anuncio.listadoAnunciosFiltrado(spin.getSelectedItem().toString(),tTexto.getText().toString());
        for(int i = 0; i < anuncios.size();i++){
            Anuncio aux = anuncios.get(i);
            inflarObjeto(aux.getID(),aux.getTitulo(),aux.getAutor(),aux.getDescripcion(),aux.getLo_perdi_en(),aux.getDejar_en(),aux.getCategoria());
        }
    }

    private void realizarFiltrado() {
        lObjetos.removeAllViews();

        ArrayList<Anuncio> anuncios = Anuncio.listadoAnunciosFiltradoSoloNombre(tTexto.getText().toString());
        for(int i = 0; i < anuncios.size();i++){
            Anuncio aux = anuncios.get(i);
            inflarObjeto(aux.getID(),aux.getTitulo(),aux.getAutor(),aux.getDescripcion(),aux.getLo_perdi_en(),aux.getDejar_en(),aux.getCategoria());
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
            inflarObjeto(aux.getID(), aux.getTitulo(), aux.getAutor(), aux.getDescripcion(),
                    aux.getLo_perdi_en(), aux.getDejar_en(), aux.getCategoria());
        }

    }

    private void inflarObjeto(int id, String sTitulo, String sAutor, String sDescripcion, String sPerdi, String sDejar, String sCategoria){
        View vi = getLayoutInflater().inflate(R.layout.anuncio_corto,null);

        TextView titulo = vi.findViewById(R.id.tTituloCorto);
        TextView autor = vi.findViewById(R.id.tAutorCorto);
        TextView categoria = vi.findViewById(R.id.tCategoriaCorto);
        TextView yaEncontrado = vi.findViewById(R.id.tYaEncontrado);
        lGeneral = vi.findViewById(R.id.linearGeneralCorto);

        titulo.setText(sTitulo);
        autor.setText(sAutor);
        categoria.setText(sCategoria);
        lGeneral.removeView(yaEncontrado);

        vi.setOnClickListener(v -> irAnuncio(id, sTitulo, sAutor, sDescripcion, sPerdi, sDejar, sCategoria));
        lObjetos.addView(vi);
    }

    private void irAnuncio(int sID,String sTitulo, String sAutor, String sDescripcion, String sPerdi, String sDejar, String sCategoria) {
        Intent intent = new Intent(getContext(), AnuncioActivity.class);
        intent.putExtra("vengoDePerfil", false);
        intent.putExtra("id", sID);
        intent.putExtra("autor", sAutor);
        intent.putExtra("titulo", sTitulo);
        intent.putExtra("descripcion", sDescripcion);
        intent.putExtra("perdi", sPerdi);
        intent.putExtra("dejar", sDejar);
        intent.putExtra("categoria", sCategoria);
        startActivity(intent);
    }

}