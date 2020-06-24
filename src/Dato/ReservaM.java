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
public class ReservaM {
    private int id;
    private String fecha,hora ;
    private int idusuario;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public static ArrayList<ReservaM> All() {
        ArrayList<ReservaM> lista = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select * from reserva order by idreserva asc";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    ReservaM modelo = new ReservaM();
                    modelo.id = rs.getInt("idreserva");
                    modelo.fecha = rs.getString("fecha");
                    modelo.hora = rs.getString("hora");
                    modelo.idusuario = rs.getInt("idusuario");
                    lista.add(modelo);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al buscar reservas : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return lista;
    }

    public String registrar() {
        Connection con = ConnectionDB.getInstance().getConnection();

        if (con != null) {
            String sql = "INSERT INTO reservas(fecha, hora, idusuario)"
                    + "	VALUES (?, ?, ?);";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, fecha);
                preparedStatement.setString(2, hora);
                preparedStatement.setInt(3, idusuario);
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

    public static ReservaM FindById(int id) {
        ReservaM modelo = null;
        Connection con = ConnectionDB.getInstance().getConnection();
        
        if (con != null) {
            String sql = "Select * from reserva where idreserva = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    modelo = new ReservaM();
                    modelo.id = rs.getInt("idreserva");
                    modelo.fecha = rs.getString("fecha");
                    modelo.hora = rs.getString("hora");
                    modelo.idusuario = rs.getInt("idusuario");
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener reserva por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return modelo;
    }

    public String modificar() {
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "UPDATE reserva SET fecha = ?, hora = ? WHERE idreserva = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, fecha);
                preparedStatement.setString(2, hora);
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
            String sql = "DELETE FROM reserva WHERE idreserva = ?;";
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
