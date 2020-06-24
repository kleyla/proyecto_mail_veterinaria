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
public class MascotaM {
    private int id;
    private String apodo;
    private String edad;
    private String raza;
    private int idusuario;
    private int tipoAnimal;
    
    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public int getUsuario() {
        return idusuario;
    }

    public void setUsuario(int usuario) {
        this.idusuario = usuario;
    }

    public int getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(int tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int mascotaID) {
        this.id = mascotaID;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }
    
    public static ArrayList<MascotaM> All() {
        ArrayList<MascotaM> mascotas = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select m.idmascota, m.apodo, m.edad, m.raza, u.idusuario as usuario, t.idtipoanimal as tipoanimal from mascota m, usuario u, tipoanimal t where m.idusuario = u.idusuario order by m.idmascota asc";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    MascotaM e = new MascotaM();
                    e.id = rs.getInt("idmascota");
                    e.apodo = rs.getString("apodo");
                    e.edad = rs.getString("edad");
                    e.idusuario = rs.getInt("usuario");
                    e.raza = rs.getString("raza");
                    e.tipoAnimal = rs.getInt("tipoanimal");
                    mascotas.add(e);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al buscar mascota por id: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return mascotas;
    }

    public String registrar() {
        Connection con = ConnectionDB.getInstance().getConnection();

        if (con != null) {
            String sql = "INSERT INTO mascota(apodo, edad, raza, idusuario, idtipoanimal)"
                    + "	VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, apodo);
                preparedStatement.setString(2, edad);
                preparedStatement.setString(3, raza);
                preparedStatement.setInt(4, idusuario);
                preparedStatement.setInt(5, tipoAnimal);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al registrar : " + ex.getMessage());
                return "Error al registrar";
            }
        } else {
            return "No se pudo conectar al servidor";
        }
        return "Registrado Exitosamente";
    }

    public static MascotaM FindById(int id) {
        MascotaM modelo = null;
        Connection con = ConnectionDB.getInstance().getConnection();
        
        if (con != null) {
            String sql = "Select * from mascota where id = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    modelo = new MascotaM();
                    modelo.id = rs.getInt("id");
                    modelo.apodo = rs.getString("apodo");
                    modelo.edad = rs.getString("edad");
                    modelo.idusuario = rs.getInt("idusuario");
                    modelo.raza = rs.getString("raza");
                    modelo.tipoAnimal = rs.getInt("idtipoanimal");
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener la mascota por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return modelo;
    }

    public String modificar() {
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "UPDATE mascota SET apodo = ?, edad = ?, idtipoanimal = ?,raza = ?, id_usuario = ? WHERE id = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, apodo);
                preparedStatement.setString(2, edad);
                preparedStatement.setInt(3, tipoAnimal);                
                preparedStatement.setString(4, raza);
                preparedStatement.setInt(5, idusuario);
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
            String sql = "DELETE FROM mascota WHERE idmascota = ?;";
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
