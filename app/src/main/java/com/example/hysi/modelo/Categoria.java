package com.example.hysi.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Categoria {

    private final int id;
    private final String nombre;

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
