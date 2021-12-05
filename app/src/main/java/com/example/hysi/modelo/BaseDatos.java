package com.example.hysi.modelo;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDatos {

    private static final String CADENA_CONEXION
            = "jdbc:jtds:sqlserver://62.82.58.26:14225;"
            + "databaseName=PRACTICAS_SSEDM;"
            + "user=ssedm;password=ssedm$2021;";

    private static Connection conexion = null;
    private static Object driver = null;

    public static Connection getConexion() {
        try {
            if (conexion == null) {
                if (driver == null) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                            .Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    driver = Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                    Log.i("Hysi", "Creada nueva instancia del driver");
                }
                conexion = DriverManager.getConnection(CADENA_CONEXION);
            }
            return conexion;
        } catch (Exception ex) {
            Log.e("Hysi", "Error de conexión con la base de datos.");
            throw new ConexionBDException(ex);
        }
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
            }
        } catch (SQLException ex) {
            Log.e("Hysi", "Error al cerrar la conexión con la base de datos.");
            throw new ConexionBDException(ex);
        }
    }

    public static void consultaPrueba() {
        try {

            PreparedStatement stmt = getConexion().prepareStatement("SELECT * FROM test WHERE id >= ?");
            stmt.setInt(1, 3);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String coche = rs.getString("COCHE");
                Log.i("Hysi", "(" + id + ", " + nombre + ", " + coche + ")");
            }

        } catch (SQLException ex) {
            Log.e("Hysi", "Error en una consulta a la base de datos.");
            ex.printStackTrace();
        }
    }

}
