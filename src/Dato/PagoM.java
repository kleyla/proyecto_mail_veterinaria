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
public class PagoM {
    private int id;
    private float montototal;
    private String fecha;
    
    public int getId(){
        return id;
    }
    
    public void setId(int Idpago){
        this.id = Idpago;
    }

    public float getMontototal() {
        return montototal;
    }

    public void setMontototal(float montototal) {
        this.montototal = montototal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public static ArrayList<PagoM> All() {
        ArrayList<PagoM> lista = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select * from pago order by idpago asc";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    PagoM modelo = new PagoM();
                    modelo.id = rs.getInt("idpago");
                    modelo.fecha = rs.getString("fecha");
                    modelo.montototal = rs.getFloat("montototal");
                    lista.add(modelo);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al buscar pago por id: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return lista;
    }

    public String registrar(int monto) {
        Connection con = ConnectionDB.getInstance().getConnection();

        if (con != null) {
            String sql = "INSERT INTO pago(montototal, fecha)"
                    + "	VALUES (?, ?);";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setFloat(1, monto);
                preparedStatement.setString(2, "2019-24-05");
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
        return "registro";
    }

    public static PagoM FindById(int id) {
        PagoM modelo = null;
        Connection con = ConnectionDB.getInstance().getConnection();
        
        if (con != null) {
            String sql = "Select * from pago where idpago = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    modelo = new PagoM();
                    modelo.id = rs.getInt("idpago");
                    modelo.montototal = rs.getFloat("montototal");
                    modelo.fecha = rs.getString("fecha");
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener el pago por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return modelo;
    }

    public String modificar() {
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "UPDATE pago SET montototal = ?, fecha = ? WHERE idpago = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setFloat(1, montototal);
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
            String sql = "DELETE FROM pago WHERE idpago = ?;";
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
    
    
    public int sumarprecio(int id1,int id2){
        Connection con = ConnectionDB.getInstance().getConnection();
        int total = 0;
        if (con != null) {
            String sql = "select SUM(precio) as total from servicio where idservicio = ? or idservicio = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id1);
                preparedStatement.setInt(2, id2);
                
                ResultSet rs = preparedStatement.executeQuery();
                total = rs.getInt("total");
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener el pago por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return total;
    }
    public int ultimoid(){
         Connection con = ConnectionDB.getInstance().getConnection();
        int idpago = 0;
        if (con != null) {
            String sql = "select idpago from pago order by idpago desc LIMIT 1;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);      
                ResultSet rs = preparedStatement.executeQuery();
                idpago = rs.getInt("idpago");
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener el pago por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return idpago;
    }
    public int registrarDetalle(int idpago,int idser1){
          Connection con = ConnectionDB.getInstance().getConnection();
        
        if (con != null) {
            String sql = "insert into detallepago(idpago,idservicio) values(?,?);";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);      
                preparedStatement.setInt(1, idpago);
                preparedStatement.setInt(2, idser1);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener el pago por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        
        return 0;
    }
}
