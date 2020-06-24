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
public class ServicioM {
    private int id, idreserva;
    private String nombre, tiposervicio;
    private float precio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(int idreserva) {
        this.idreserva = idreserva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiposervicio() {
        return tiposervicio;
    }

    public void setTiposervicio(String tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
        
    public static ArrayList<ServicioM> All() {
        ArrayList<ServicioM> lista = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select * from servicio order by idservicio asc";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    ServicioM modelo = new ServicioM();
                    modelo.id = rs.getInt("idservicio");
                    modelo.nombre = rs.getString("nombre");
                    modelo.tiposervicio = rs.getString("tiposervicio");
                    modelo.precio = rs.getFloat("precio");
                    modelo.idreserva = rs.getInt("idreserva");
                    lista.add(modelo);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al buscar servicio por id: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return lista;
    }

    public String registrar() {
        Connection con = ConnectionDB.getInstance().getConnection();

        if (con != null) {
            String sql = "INSERT INTO servicio(nombre, tiposervicio, precio, idreserva)"
                    + "	VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, tiposervicio);
                preparedStatement.setFloat(2, precio);
                preparedStatement.setInt(1, idreserva);
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

    public static ServicioM FindById(int id) {
        ServicioM modelo = null;
        Connection con = ConnectionDB.getInstance().getConnection();
        
        if (con != null) {
            String sql = "Select * from servicio where idservicio = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    modelo = new ServicioM();
                    modelo.id = rs.getInt("idservicio");
                    modelo.nombre = rs.getString("nombre");
                    modelo.tiposervicio = rs.getString("tiposervicio");
                    modelo.precio = rs.getFloat("precio");
                    modelo.idreserva = rs.getInt("idreserva");
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al obtener el servicio por ID : " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return modelo;
    }

    public String modificar() {
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "UPDATE servicio SET nombre = ?, tiposervicio = ?, precio = ?, idreserva = ? WHERE idservicio = ?;";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, tiposervicio);
                preparedStatement.setFloat(3, precio);
                preparedStatement.setInt(4, idreserva);
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
            String sql = "DELETE FROM servicio WHERE idservicio = ?;";
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
