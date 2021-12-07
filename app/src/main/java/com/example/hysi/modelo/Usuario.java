package com.example.hysi.modelo;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase ORM para usuarios de la aplicación.
 */
public class Usuario {

    private final int id;
    private final String usuario;
    private final String password;

    private Usuario(int id, String usuario, String password) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Devuelve un usuario a partir de su ID
     * @param id El ID del usuario a devolver
     * @return El usuario si existe, null en caso contrario
     */
    public static Usuario findById(int id) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "SELECT * FROM USUARIO WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Usuario resultado = null;

            if (rs.next()) {
                resultado = new Usuario(id,
                        rs.getString("usuario"),
                        rs.getString("password"));
            }

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    /**
     * Devuelve un usuario a partir de su nombre de usuario y contraseña.
     * @param usuario El nombre del usuario a devolver
     * @param password La contraseña del usuario a devolver
     * @return El usuario si existe, null en caso contrario
     */
    public static Usuario findByUsuarioAndPassword(String usuario, String password) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "SELECT * FROM USUARIO WHERE usuario = ? AND password = ?");
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            Usuario resultado = null;

            if (rs.next()) {
                resultado = new Usuario(rs.getInt("ID"),
                        usuario,
                        password);
            }

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    /**
     * Devuelve un usuario a partir de su nombre de usuario
     * @param usuario El nombre del usuario a devolver
     * @return El usuario si existe, null en caso contrario
     */
    public static Usuario findByUsuario(String usuario) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "SELECT * FROM USUARIO WHERE usuario = ?");
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            Usuario resultado = null;

            if (rs.next()) {
                resultado = new Usuario(rs.getInt("id"),
                        usuario,
                        rs.getString("password"));
            }

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    /**
     * Crea un nuevo usuario en la base de datos y lo devuelve.
     * @param usuario El nombre del nuevo usuario.
     * @param contrasenia La contraseña del nuevo usuario.
     * @return Representación del nuevo usuario, si se ha creado correctamente.
     */
    public static Usuario crear(String usuario, String contrasenia) {
        try {
            Connection conex = BaseDatos.getConexion();
            PreparedStatement stmt = conex.prepareStatement(
                    "INSERT INTO USUARIO (usuario, password) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasenia);

            stmt.executeUpdate();
            ResultSet rskey = stmt.getGeneratedKeys();
            stmt.getGeneratedKeys().next();
            Usuario resultado = new Usuario(rskey.getInt("id"), usuario, contrasenia);

            BaseDatos.cerrarConexion();
            return resultado;
        } catch (SQLException ex) {
            throw new ConsultaBDException(ex);
        }
    }

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }



}
