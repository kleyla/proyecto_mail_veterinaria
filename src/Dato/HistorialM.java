/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import servicio.ConnectionDB;

/**
 *
 * @author USER
 */
public class HistorialM {
    private int id;
    private String servicio;
    private String fecha;
    private int idusuario;
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int historialID){
        this.id = historialID;
    }
    
    public String getServicio(){
        return servicio;
    }
    
    public void setServicio(String Servicio){
        this.servicio = Servicio;
    }
    
    public String getFecha(){
        return fecha;
    }
    
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    
    public int getIdusuario(){
        return idusuario;
    }
    
    public void setIdusuario(int Idusuario){
        this.idusuario = Idusuario;
    }
    
    public static ArrayList<HistorialM> All() {
        ArrayList<HistorialM> usuarios = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select * from historial order by idusuario asc";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    HistorialM usuario = new HistorialM();
                    usuario.id = rs.getInt("idhistorial");
                    usuario.servicio = rs.getString("servicio");
                    usuario.fecha = rs.getString("fecha");
                    usuario.idusuario = rs.getInt("idusuario");
                    usuarios.add(usuario);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al buscar usuario por NumeroID: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return usuarios;
    }

    public String registrar() {
        Connection con = ConnectionDB.getInstance().getConnection();

        if (con != null) {
            String sql = "INSERT INTO historial(servicio, fecha, idusuario)"
                    + "	VALUES (?, ?, ?);";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, servicio);
                preparedStatement.setString(2, fecha);
                preparedStatement.setInt(3, idusuario);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al registrar el historial : " + ex.getMessage());
                return "Error al registrar el historial intentelo denuevo";
            }
        } else {
            return "No se pudo conectar al servidor";
        }
        return "Registrado Correctamente";
    }

    public static HistorialM FindById(int idhistorial) {
        HistorialM usuario = null;
        Connection con = ConnectionDB.getInstance().getConnection();
        
        if (con != null) {
            String sql = "Select * from usuario where idhistorial = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, idhistorial);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    usuario = new HistorialM();
                    usuario.id = rs.getInt("idhistorial");
                    usuario.servicio = rs.getString("servicio");
                    usuario.fecha = rs.getString("fecha");
                    usuario.idusuario = rs.getInt("idusuario");
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener el Historial por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return usuario;
    }

    public String modificar() {
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "UPDATE historial SET servicio = ?, fecha = ? WHERE idhistorial = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, servicio);
                preparedStatement.setString(2, fecha);
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                con.close();
                return "Modificacion Exitosa";
            } catch (SQLException ex) {
                return "Error al realizar cambios";
            }
        } else {
            return "No se pudo conectar al servidor";
        }
    }

    public String eliminar() {
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "DELETE FROM historial WHERE idhistorial = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                con.close();
                return "Eliminacion Exitosa";
            } catch (SQLException ex) {
                return "Error al realizar cambios, no existe";
            }
            
        } else {
            return "No se pudo conectar al servidor";
        }
    }

}
