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
public class VacunaM {
    private int id;
    private String descripcion, nota, fecha ;
    private int idmascota;
    
    public int getId(){
        return id;
    }
    
    public void setId(int Idvacuna){
        this.id = Idvacuna;
    }

    public int getIdmascota() {
        return idmascota;
    }

    public void setIdmascota(int idmascota) {
        this.idmascota = idmascota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public static ArrayList<VacunaM> All() {
        ArrayList<VacunaM> lista = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select * from vacuna order by idvacuna asc";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    VacunaM modelo = new VacunaM();
                    modelo.id = rs.getInt("id");
                    modelo.descripcion = rs.getString("descripcion");
                    modelo.nota = rs.getString("nota");
                    modelo.fecha = rs.getString("fecha");
                    modelo.idmascota = rs.getInt("idmascota");
                    lista.add(modelo);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al buscar vacuna por id: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return lista;
    }

    public String registrar() {
        Connection con = ConnectionDB.getInstance().getConnection();

        if (con != null) {
            String sql = "INSERT INTO vacuna(descripcion, nota, fecha, idmascota)"
                    + "	VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, descripcion);
                preparedStatement.setString(2, nota);
                preparedStatement.setString(3, fecha);
                preparedStatement.setInt(1, idmascota);
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

    public static VacunaM FindById(int id) {
        VacunaM modelo = null;
        Connection con = ConnectionDB.getInstance().getConnection();
        
        if (con != null) {
            String sql = "Select * from vacuna where idvacuna = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    modelo = new VacunaM();
                    modelo.id = rs.getInt("idvacuna");
                    modelo.descripcion = rs.getString("descripcion");
                    modelo.fecha = rs.getString("fecha");
                    modelo.nota = rs.getString("nota");
                    modelo.idmascota = rs.getInt("idmascota");
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener la vacuna por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return modelo;
    }

    public String modificar() {
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "UPDATE vacuna SET descripcion = ?, nota = ?, fecha = ?, idmascota = ? WHERE idvacuna = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, descripcion);
                preparedStatement.setString(2, nota);
                preparedStatement.setString(3, fecha);
                preparedStatement.setInt(4, idmascota);
                preparedStatement.setInt(5, id);
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
            String sql = "DELETE FROM vacuna WHERE idvacuna = ?;";
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
