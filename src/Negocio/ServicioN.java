/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.ServicioM;
import java.util.ArrayList;
import servicio.Contenido;
import servicio.Mensaje;

public class ServicioN {
    Contenido contenido;
    Mensaje mensaje;
    
    public ServicioN(Contenido contenido) {
        this.contenido = contenido;
    }
    
    public Mensaje analizar(Mensaje mensaje) {
        this.mensaje = mensaje;
        Mensaje myMensaje = null;
        String operacion = contenido.obtenerSiguiente();
        System.out.println(operacion);
        if (operacion == "") {
            System.out.println("Mensaje sin operacion");
            generarMensajeError(0);
        } else {
            System.out.println(" C : MENSAJE CON OPERACION EN COMANDO");

            switch (operacion) {
                case "REGISTRAR":
                    //myMensaje = new Mensaje(mensaje.getCorreo(), CU1_GESTIONAR_USUARIO, "-------------mensaje de respuesta " + CU1_GESTIONAR_USUARIO);
                    registrar(contenido.obtenerAtributo(), contenido.obtenerAtributo(), Float.parseFloat(contenido.obtenerAtributo()), Integer.parseInt(contenido.obtenerAtributo()));
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR SERVICIO", "<h1>--REGISTRO EXITOSO--</h1>");
                    break;              
                case "MODIFICAR":
                    modificar(Integer.parseInt(contenido.obtenerAtributo()), contenido.obtenerAtributo(), contenido.obtenerAtributo(), Float.parseFloat(contenido.obtenerAtributo()), Integer.parseInt(contenido.obtenerAtributo()));
                    
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR SERVICIO", "<h1>--MODIFICACION EXITOSA--</h1>");
                    break;
                
                case "ELIMINAR":
                    eliminar(Integer.parseInt(contenido.obtenerAtributo()));
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR SERVICIO", "<h1>--ELIMINACION EXITOSA--</h1>");
                    //myMensaje.setBandera(false);
                    
                    break;
                case "LISTAR":
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR SERVICIO", "\n" + obtenerDatos());
                    break;
                default:
                    myMensaje = generarMensajeError(1);
                    myMensaje.setBandera(false);
                    break;
            }
        }
        
        return myMensaje;
    }
    
    public Mensaje generarMensajeError(int nroComandos) {
        String subject = "ERROR";
        String data = "";
        if (nroComandos == 0) {
            data = "El comando enviado no es correcto\n"
                    + "Si desea saber los comandos del sistema ingrese :\n"
                    + "\t{HELP}\n\n";
        } else {
            data = "El Caso de Uso solicitado es incorrecto\n"
                    + "Si desea saber los comandos del sistema ingrese :\n"
                    + "\t{HELP}\n\n";
        }
        Mensaje myMensaje = new Mensaje(mensaje.getCorreo(), subject, data);
        return myMensaje;
    }
    
    private String obtenerDatos(){
        ArrayList<ServicioM> lista = ServicioM.All();
        String result = "<h1>LISTA DE SERVICIO</h1>" +
"<table>" + 
"<tr>\n" +
"<th>ID</th>\n" +
"<th>NOMBRE</th>\n" +
"<th>TIPO</th>\n" +
"<th>PRECIO</th>\n" +
"<th>IDRESERVA</th>\n" +
"</tr>\n";
        for(int i = 0; i < lista.size(); i++) {
            ServicioM modelo = lista.get(i);
            result = result + "<tr>\n" 
                    + "<td>" + Integer.toString(modelo.getId()) + "</td>\n"
                    + "<td>" + modelo.getNombre() + "</td>\n"
                    + "<td>" + modelo.getTiposervicio() + "</td>\n"
                    + "<td>" + Float.toString(modelo.getPrecio())+ "</td>\n"
                    + "<td>" + Integer.toString(modelo.getIdreserva()) + "</td>\n"
                    + "</tr>\n";
        }
        result = result + "</table>";
        return result;
    }
    
    public String registrar(String nombre, String tiposervicio, float precio, int personalId) {
        ServicioM modelo = new ServicioM();
        modelo.setNombre(nombre);
        modelo.setTiposervicio(tiposervicio);
        modelo.setPrecio(precio);
        modelo.setIdreserva(personalId);
        return modelo.registrar();
    }

    public String modificar(int id , String nombre, String descripcion, float precio, int personalId) {
        //ReservaM modelo = ReservaM.FindById(id);
        ServicioM modelo = ServicioM.FindById(id);
        if (modelo != null) {
            modelo.setNombre(nombre);
            modelo.setTiposervicio(descripcion);
            modelo.setPrecio(precio);
            modelo.setIdreserva(personalId);
            return modelo.modificar();
            
        } else {
            return "el modelo con el id " + id + " no se encontro.";
        }
    }

    public String eliminar(int id) {
        ServicioM modelo = ServicioM.FindById(id);
        if (modelo != null) {
            return modelo.eliminar();
        } else {
            return "El modelo con el id " + id + " no se encontro.";
        }        
    }
  
}
