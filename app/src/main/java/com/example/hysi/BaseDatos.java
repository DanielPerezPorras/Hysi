package com.example.hysi;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {

    private static String databaseName = "databaseName=PRACTICAS_SSEDM;";
    private static String userpass = "user=ssedm;password=ssedm$2021;";
    private static String REMOTEserversql = "//62.82.58.26:14225;";
    private static String cadenadeConexion = "jdbc:jtds:sqlserver:" + REMOTEserversql + databaseName + userpass;

    public static void ejemplo() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            Connection conn = DriverManager.getConnection(cadenadeConexion);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM test");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String coche = rs.getString("COCHE");
                Log.i("SSEDM", "(" + id + ", " + nombre + ", " + coche + ")");
            }

        } catch (SQLException ex) {
            Log.e("SSEDM", "Error SSEDM");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Log.e("SSEDM", "Error SSEDM");
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            Log.e("SSEDM", "Error SSEDM");
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            Log.e("SSEDM", "Error SSEDM");
            ex.printStackTrace();
        }



    }



}
