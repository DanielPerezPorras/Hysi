package com.example.hysi.modelo;


import android.content.Context;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Clase ORM para anuncios de la aplicación.
 */

public class Anuncio {
    private  int id;
    private  int autor;
    private String nombreAutor;
    private  String titulo;
    private  String descripcion;
    private  String dejar_en;
    private  String lo_perdi_en;
    private  int resuelto;
    private  double latitud;
    private  double longitud;
    private  int categoria;
    private String nombreCategoria;


    public Anuncio(int id, int autor, String titulo, String descripcion, String dejar_en, String lo_perdi_en, int resuelto, double latitud, double longitud, int categoria) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.dejar_en = dejar_en;
        this.lo_perdi_en = lo_perdi_en;
        this.resuelto = resuelto;
        this.latitud = latitud;
        this.longitud = longitud;
        this.categoria = categoria;
    }

    public Anuncio(int id,String autor, String titulo, String descripcion, String dejar_en, String lo_perdi_en, String categoria) {
        this.id = id;
        this.nombreAutor = autor;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.dejar_en = dejar_en;
        this.lo_perdi_en = lo_perdi_en;
        this.nombreCategoria = categoria;
    }

    public Anuncio(String titulo, double latitud, double longitud) {
        this.titulo = titulo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Devuelve un usuario a partir de su ID
     */
    public static Anuncio findById(int id) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "SELECT * FROM ANUNCIO WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Anuncio resultado = null;

            if (rs.next()) {
                resultado = new Anuncio(id,
                        rs.getInt("autor"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString( "dejar_en"),
                        rs.getString("lo_perdi_en"),
                        rs.getInt("resuelto"),
                        rs.getDouble("latitud"),
                        rs.getDouble("longitud"),
                        rs.getInt("categoria"));
            }

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    /**
     * Devuelve anuncios a partir de su titulo.
     */
    public static Anuncio findByTitulo(String titulo) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "SELECT * FROM ANUNCIO WHERE titulo = ?");
            stmt.setString(1, titulo);
            ResultSet rs = stmt.executeQuery();
            Anuncio resultado = null;

            if (rs.next()) {
                resultado = new Anuncio(rs.getInt("ID"),
                        rs.getInt("autor"),
                        titulo,
                        rs.getString("descripcion"),
                        rs.getString( "dejar_en"),
                        rs.getString("lo_perdi_en"),
                        rs.getInt("resuelto"),
                        rs.getDouble("latitud"),
                        rs.getDouble("longitud"),
                        rs.getInt("categoria"));
            }

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }


    /**
     * Crea un nuevo anuncio en la base de datos y lo devuelve.
     */
    public static Anuncio crear(String titulo, String descripcion, String dejar_en, String lo_perdi_en, String categoria) {
        try {
            /*
             * Obtengo latitud y longitud con Geocode
             * */
            Address address = GeocodeUtils.getAddressSync(lo_perdi_en);
            //System.out.println(lat_long.toString());
            double longitud = address.getLongitude();
            double latitud = address.getLatitude();


            /*
             * Obtengo id del usuario que crea el anuncio, es decir, el que tiene iniciada la sesión
             * */
            Usuario usuario = (Usuario) SingletonMap.getInstance().get("session");
            int autor = usuario.getId();

            /*
             * En principio, el anuncio estará no resuelto (false)
             * */
            int resuelto = 0;

            /*
             * Lo que me llega es el nombre de la categoria, debo pasar ese nombre a id.
             * */
            Categoria objetoCategoria = Categoria.findByNombre(categoria);
            System.out.println(objetoCategoria.getId());
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "INSERT INTO ANUNCIO (autor, titulo, descripcion, dejar_en, lo_perdi_en, resuelto, latitud, longitud, categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, autor);
            stmt.setString(2, titulo);
            stmt.setString(3, descripcion);
            stmt.setString(4, dejar_en);
            stmt.setString(5, lo_perdi_en);
            stmt.setInt(6, resuelto);
            stmt.setDouble(7, latitud);
            stmt.setDouble(8, longitud);
            stmt.setInt(9, objetoCategoria.getId());

            stmt.executeUpdate();
            ResultSet rskey = stmt.getGeneratedKeys();
            rskey.next();
            Anuncio resultado = new Anuncio(rskey.getInt("id"), autor, titulo, descripcion, dejar_en, lo_perdi_en, resuelto, latitud, longitud, objetoCategoria.getId());

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException | GeocodingException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    /*
         Resuelve un anuncio
     */

    public static void resolver(int anuncio) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "UPDATE ANUNCIO SET resuelto=? WHERE id=? VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, 1);
            stmt.setInt(2, anuncio);

            stmt.executeUpdate();
            ResultSet rskey = stmt.getGeneratedKeys();
            rskey.next();
            BaseDatos.cerrarConexion();
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    /*
          Devuelve una lista con todos los ids de los anuncios
     */

    public static ArrayList<Integer> getIDAnuncios(){
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement("SELECT id FROM ANUNCIO");

            ResultSet rs = stmt.executeQuery();
            ArrayList<Integer> anuncios = new ArrayList<>();

            while(rs.next()){
                int a = rs.getInt("id");
                anuncios.add(a);
            }

            BaseDatos.cerrarConexion();

            return anuncios;

        } catch (SQLException ex) {
            throw new ConsultaBDException (ex);
        }
    }


    public static ArrayList<Anuncio> listadoAnuncios(){

        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement("SELECT A.id,A.titulo, U.usuario, A.descripcion, C.nombre 'Categoria', A.dejar_en, A.lo_perdi_en FROM ANUNCIO A,CATEGORIA C,USUARIO U WHERE A.autor = U.id AND A.categoria = C.id  AND A.resuelto = 0");

            ResultSet rs = stmt.executeQuery();
            ArrayList<Anuncio> anuncios = new ArrayList<>();

            while(rs.next()){
                Anuncio a = new Anuncio(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("dejar_en"),
                        rs.getString("lo_perdi_en"),
                        rs.getString("categoria"));
                anuncios.add(a);
            }

            BaseDatos.cerrarConexion();

            return anuncios;


        } catch (SQLException ex) {
            throw new ConsultaBDException (ex);
        }
    }

    public static ArrayList<Anuncio> listadoAnunciosByID(int id){

        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement("SELECT A.id,A.titulo, U.usuario, A.descripcion, C.nombre 'Categoria', A.dejar_en, A.lo_perdi_en FROM ANUNCIO A,CATEGORIA C,USUARIO U WHERE A.autor = U.id AND A.categoria = C.id AND A.autor = ?;");

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            ArrayList<Anuncio> anuncios = new ArrayList<>();

            while(rs.next()){
                Anuncio a = new Anuncio(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("dejar_en"),
                        rs.getString("lo_perdi_en"),
                        rs.getString("categoria"));
                anuncios.add(a);
            }

            BaseDatos.cerrarConexion();

            return anuncios;


        } catch (SQLException ex) {
            throw new ConsultaBDException (ex);
        }
    }

    public static ArrayList<Anuncio> listadoAnunciosFiltrado(String spinner, String  texto){

        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement("SELECT A.ID,A.titulo, A.descripcion, C.nombre 'Categoria', A.dejar_en, A.lo_perdi_en, U.usuario FROM ANUNCIO A \n" +
                    "LEFT JOIN CATEGORIA C ON A.categoria = C.id LEFT JOIN USUARIO U ON U.id = a.autor \n" +
                    "WHERE resuelto = 0 AND (C.nombre = ? OR U.usuario = ? OR A.titulo = ?)");

            stmt.setString(1, spinner);
            stmt.setString(2, texto);
            stmt.setString(3, texto);

            ResultSet rs = stmt.executeQuery();
            ArrayList<Anuncio> anuncios = new ArrayList<>();

            while(rs.next()){
                Anuncio a = new Anuncio(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("dejar_en"),
                        rs.getString("lo_perdi_en"),
                        rs.getString("categoria"));
                anuncios.add(a);
            }

            BaseDatos.cerrarConexion();

            return anuncios;


        } catch (SQLException ex) {
            throw new ConsultaBDException (ex);
        }

    }


    public static ArrayList<Anuncio> listadoAnunciosFiltradoSoloNombre(String  texto){

        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement("SELECT A.ID,A.titulo, A.descripcion, C.nombre 'Categoria', A.dejar_en, A.lo_perdi_en, U.usuario FROM ANUNCIO A \n" +
                    "LEFT JOIN CATEGORIA C ON A.categoria = C.id LEFT JOIN USUARIO U ON U.id = a.autor \n" +
                    "WHERE resuelto = 0 AND (U.usuario = ? OR A.titulo = ?)");

            stmt.setString(1, texto);
            stmt.setString(2, texto);

            ResultSet rs = stmt.executeQuery();
            ArrayList<Anuncio> anuncios = new ArrayList<>();

            while(rs.next()){
                Anuncio a = new Anuncio(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("dejar_en"),
                        rs.getString("lo_perdi_en"),
                        rs.getString("categoria"));
                anuncios.add(a);
            }

            BaseDatos.cerrarConexion();

            return anuncios;


        } catch (SQLException ex) {
            throw new ConsultaBDException (ex);
        }

    }




    public static ArrayList<Anuncio> getLatitudLongitudTitulo(){

        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement("SELECT titulo, latitud, longitud FROM ANUNCIO ");

            ResultSet rs = stmt.executeQuery();
            ArrayList<Anuncio> anuncios = new ArrayList<>();

            while(rs.next()){
                Anuncio a = new Anuncio(rs.getString("titulo"),rs.getDouble("latitud"),rs.getDouble("longitud"));
                anuncios.add(a);
            }

            BaseDatos.cerrarConexion();

            return anuncios;


        } catch (SQLException ex) {
            throw new ConsultaBDException (ex);
        }
    }

    public static void encontrado (int id) {
        try {
            Connection conex = BaseDatos.getConexion();

            PreparedStatement stmt = conex.prepareStatement(
                    "UPDATE anuncio SET resuelto = 1 WHERE ID = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();

            BaseDatos.cerrarConexion();

        } catch(SQLException ex){
            throw new ConsultaBDException(ex);
        }

    }

    public static int estaEncontrado(int id) {

        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement("SELECT resuelto FROM anuncio WHERE id = ? ");

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            int encontrado = -1;

            if(rs.next())
                encontrado = rs.getInt("resuelto");

            BaseDatos.cerrarConexion();

            return encontrado;


        } catch (SQLException ex) {
            throw new ConsultaBDException (ex);
        }

    }

    public static boolean noTieneEmailAutor(int id) {

        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement("select u.email from usuario u, anuncio a where a.autor = u.id and a.id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            String email = "";

            if(rs.next()){
                email = rs.getString("email");
            }


            BaseDatos.cerrarConexion();

            return (email == null || email.isEmpty());


        } catch (SQLException ex) {
            throw new ConsultaBDException (ex);
        }

    }


    public String getAutor() {
        return this.nombreAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDejar_en() {
        return dejar_en;
    }

    public String getLo_perdi_en() {
        return lo_perdi_en;
    }

    public String getCategoria() {
        return nombreCategoria;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public int getID() {
        return id;
    }
}
