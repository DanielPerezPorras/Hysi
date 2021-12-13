package com.example.hysi.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Categoria {

    private final int id;
    private final String nombre;

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static ArrayList<Categoria> getCategorias(){
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "SELECT * FROM CATEGORIA");

            ResultSet rs = stmt.executeQuery();
            ArrayList<Categoria> categorias = new ArrayList<>();

            while(rs.next()){
                Categoria c = new Categoria(    rs.getInt("id"),
                                                rs.getString("nombre"));
                categorias.add(c);
                System.out.println(c.getNombre());
            }

            BaseDatos.cerrarConexion();
            return categorias;

        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    /**
     * Devuelve una categoria a partir de su ID
     */
    public static Categoria findById(int id) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "SELECT * FROM CATEGORIA WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Categoria resultado = null;

            if (rs.next()) {
                resultado = new Categoria(id,
                        rs.getString("nombre"));
            }

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    public static Categoria findByNombre(String nombre) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "SELECT * FROM CATEGORIA WHERE nombre = ?");
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            Categoria resultado = null;

            if (rs.next()) {
                resultado = new Categoria(rs.getInt("id"),
                        nombre);
            }

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

}
