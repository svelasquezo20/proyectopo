/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopoo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author svela
 */
public class Conexion {

    private final String driver = "com.mysql.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/proyectopoo_bd";
    private final String usuario = "root";
    private final String contrasenia = "";
    private Connection conexion;

    public boolean abrirConexion() {
        try {
            Class.forName(driver);
            conexion = (Connection) DriverManager.getConnection(url, usuario, contrasenia);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String verificarLogin(String celular, String contrasenia) {
        ResultSet rs = null;
        PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement("SELECT * FROM persona p "
                    + "INNER JOIN administrador a ON p.celular = a.celular "
                    + "WHERE p.celular = ? AND p.contrasenia = ?");
            consulta.setString(1, celular);
            consulta.setString(2, contrasenia);
            rs = consulta.executeQuery();

            if (rs.next()) {
                return "ADMINISTRADOR";

            } else {
                consulta = conexion.prepareCall("SELECT * FROM persona p INNER JOIN cliente c ON p.celular = c.celular WHERE p.celular = ? AND p.contrasenia = ?");
                consulta.setString(1, celular);
                consulta.setString(2, contrasenia);
                rs = consulta.executeQuery();
                if (rs.next()) {
                    return "CLIENTE";
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
    
    public int agregarPersona(Persona unaPersona){
        int filasAfectadas = 0;
        try {
            PreparedStatement consulta = conexion.prepareCall("INSERT INTO persona VALUES(?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement sentenciaCliente = conexion.prepareCall("INSERT INTO cliente VALUES (?,'A')");
            consulta.setString(1, unaPersona.getCedula());
            consulta.setString(2, unaPersona.getNombre());
            consulta.setString(3, unaPersona.getApellidos());
            consulta.setString(4, unaPersona.getDireccion());
            consulta.setString(5, unaPersona.getEmail());
            consulta.setString(6, unaPersona.getCelular());
            consulta.setString(7, unaPersona.getContrasenia());
            filasAfectadas = consulta.executeUpdate();
            sentenciaCliente.setString(1, unaPersona.getCelular());
            sentenciaCliente.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return filasAfectadas;
    }
}
