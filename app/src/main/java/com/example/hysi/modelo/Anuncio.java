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

/**
 * Clase ORM para anuncios de la aplicación.
 */

public class Anuncio {
    private final int id;
    private final int autor;
    private final String titulo;
    private final String descripcion;
    private final String dejar_en;
    private final String lo_perdi_en;
    private final int resuelto;
    private final double latitud;
    private final double longitud;
    private final int categoria;


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
    public static Anuncio crear(Context contexto, String titulo, String descripcion, String dejar_en, String lo_perdi_en, String categoria) {
        try {
            /*
            * Obtengo latitud y longitud con Geocode
            * */
            Address address = GeocodeUtils.getAddressSync(contexto, lo_perdi_en);
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
        } catch (SQLException | IOException ex) {
            throw new ConsultaBDException(ex);
        }
    }

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
}
