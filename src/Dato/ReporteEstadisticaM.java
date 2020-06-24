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
public class ReporteEstadisticaM {
    
    private String tipoanimal;
    private int cantidad;

    public String getRaza() {
        return tipoanimal;
    }

    public void setRaza(String raza) {
        this.tipoanimal = raza;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public static ArrayList<ReporteEstadisticaM> getCantidadtipoanimal() {////////////////////
        ArrayList<ReporteEstadisticaM> estadisticas = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            /*String sql = "select m.tipoanimal, count(*) as consultas " +
            "from mascota m, reserva re " +
            "where r.razaid = m.razaid and re.mascotaid = m.mascotaid and c.reservaid = re.reservaid and r.estado = '1' and m.estado = '1' and re.estado = '1' and c.estado = '1' " +
            "group by r.razaid, r.nombre";*/
            String sql = "select t.idtipoanimal, t.descripcion,count(*) as cantidad from mascota m, tipoanimal t where m.idtipoanimal=t.idtipoanimal group by t.idtipoanimal";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                
                while (rs.next()) {
                    ReporteEstadisticaM e = new ReporteEstadisticaM();
                    e.tipoanimal = rs.getString("descripcion");
                    e.cantidad = rs.getInt("cantidad");
                    estadisticas.add(e);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al solicitar estadistica: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return estadisticas;
    }

    public static ArrayList<ReporteEstadisticaM> getServicios_usuarios() {/////////////////////////////////////
        ArrayList<ReporteEstadisticaM> estadisticas = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select u.idusuario, u.nombre, count(*) as cantidad from usuario u, servicio s, reserva r where u.idusuario=r.idusuario and r.idreserva=s.idreserva group by u.idusuario";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                
                while (rs.next()) {
                    ReporteEstadisticaM e = new ReporteEstadisticaM();
                    e.tipoanimal = rs.getString("nombre");
                    e.cantidad = rs.getInt("cantidad");
                    estadisticas.add(e);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al solicitar estadistica: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return estadisticas;
    }
        
    public static ArrayList<ReporteEstadisticaM> getReserva_tipoanimal() {
        ArrayList<ReporteEstadisticaM> estadisticas = new ArrayList<>();
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select t.idtipoanimal, t.descripcion, count(*) as cantidad from tipoanimal t, mascota m, usuario u, reserva r where t.idtipoanimal=m.idtipoanimal and m.idusuario=u.idusuario and u.idusuario=r.idusuario group by t.idtipoanimal";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    ReporteEstadisticaM e = new ReporteEstadisticaM();
                    e.tipoanimal = rs.getString("descripcion");
                    e.cantidad = rs.getInt("cantidad");
                    estadisticas.add(e);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al solicitar estadistica: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return estadisticas;
    }
   
    public static int getTotalReservas() {
        int total = 1; 
        Connection con = ConnectionDB.getInstance().getConnection();
        if (con != null) {
            String sql = "select count(*) as total from reserva ";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    total = rs.getInt("total");
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al solicitar estadistica: " + ex.getMessage());
            } catch (Exception ex) {

            }
        }
        return total;
    }
}
