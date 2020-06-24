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
public class UsuarioM {
    private int id;
    private String nombre;
    private String apellido;
    private String genero;
    private String direccion;
    private String telefono;
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int usuarioID){
        this.id = usuarioID;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
    public String getGenero(){
        return genero;
    }
    
    public void setGenero(String genero){
        this.genero = genero;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    
    public static ArrayList<UsuarioM> All() {
        ArrayList<UsuarioM> usuarios = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select * from usuario order by idusuario asc";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    UsuarioM usuario = new UsuarioM();
                    usuario.id = rs.getInt("idusuario");
                    usuario.nombre = rs.getString("nombre");
                    usuario.apellido = rs.getString("apellido");
                    usuario.telefono = rs.getString("telefono");
                    usuario.direccion = rs.getString("direccion");
                    usuario.genero = rs.getString("genero");
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
            String sql = "INSERT INTO usuario(nombre, apellido, genero, direccion, telefono)"
                    + "	VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, apellido);
                preparedStatement.setString(3, genero);
                preparedStatement.setString(4, direccion);
                preparedStatement.setString(5, telefono);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al registrar al usuario : " + ex.getMessage());
                return "Error al registrar al usuario intentelo de nuevo con otro numero de celular";
            }
        } else {
            return "No se pudo conectar al servidor";
        }
        return "Registrado Correctamente";
    }

    public static UsuarioM FindById(int usuarioID) {
        UsuarioM usuario = null;
        Connection con = ConnectionDB.getInstance().getConnection();
        
        if (con != null) {
            String sql = "Select * from usuario where idusuario = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, usuarioID);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    usuario = new UsuarioM();
                    usuario.id = rs.getInt("idusuario");
                    usuario.nombre = rs.getString("nombre");
                    usuario.apellido = rs.getString("apellido");
                    usuario.telefono = rs.getString("telefono");
                    usuario.genero = rs.getString("genero");
                    usuario.direccion = rs.getString("direccion");
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener al usuario por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return usuario;
    }

    public String modificar() {
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "UPDATE usuario SET nombre = ?, apellido = ?, telefono = ?, direccion = ?, genero = ? WHERE idusuario = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, apellido);
                preparedStatement.setString(3, telefono);
                preparedStatement.setString(4, direccion);
                preparedStatement.setString(5, genero);
                preparedStatement.setInt(6, id);
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
            String sql = "DELETE FROM usuario WHERE idusuario = ?;";
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
