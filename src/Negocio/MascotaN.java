/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.MascotaM;
import java.util.ArrayList;
import servicio.Contenido;
import servicio.Mensaje;

/**
 *
 * @author USER
 */
public class MascotaN {
    Contenido contenido;
    Mensaje mensaje;
    
    public MascotaN(Contenido contenido) {
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
                    registrar(contenido.obtenerAtributo(), contenido.obtenerAtributo(), contenido.obtenerAtributo(), Integer.parseInt(contenido.obtenerAtributo()));
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR MASCOTA", "<h1>--REGISTRO EXITOSO--</h1>");
                    break;              
                case "MODIFICAR":
                    modificar(Integer.parseInt(contenido.obtenerAtributo()), contenido.obtenerAtributo(), contenido.obtenerAtributo(), contenido.obtenerAtributo(), Integer.parseInt(contenido.obtenerAtributo()), Integer.parseInt(contenido.obtenerAtributo()));
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR MASCOTA", "<h1>--MODIFICACION EXITOSA--</h1>");
                    // myMensaje.setBandera(false);
                    break;
                
                case "ELIMINAR":
                    eliminar(Integer.parseInt(contenido.obtenerAtributo()));
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR MASCOTA", "<h1>--ELIMINACION EXITOSA--</h1>");
                    //myMensaje.setBandera(false);
                    break;
                case "LISTAR":
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR MASCOTA", "\n" + obtenerDatos());
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
        ArrayList<MascotaM> lista = MascotaM.All();
        String resultado = "<h1>LISTA DE MASCOTAS</h1>" +
"<table>" + 
"<tr>\n" +
"<th>ID</th>\n" +
"<th>APODO</th>\n" +
"<th>EDAD</th>\n" +
"<th>RAZA</th>\n" +
"<th>TIPOA</th>\n" +
"<th>DUENO</th>\n" +
"</tr>\n";
        for(int i = 0; i < lista.size(); i++) {
            MascotaM modelo = lista.get(i);
            resultado = resultado + "<tr>\n" 
                    + "<td>" + Integer.toString(modelo.getId()) + "</td>\n"
                    + "<td>" + modelo.getApodo() + "</td>\n"
                    + "<td>" + modelo.getEdad()+ "</td>\n"
                    + "<td>" + modelo.getRaza()+ "</td>\n"
                    + "<td>" + Integer.toString(modelo.getTipoAnimal())+ "</td>\n"
                    + "<td>" + Integer.toString(modelo.getUsuario())+ "</td>\n"
                    + "</tr>\n";
        }
        resultado = resultado + "</table>";
        return resultado;
    }
    
    public String registrar(String apodo, String edad, String raza, int usuarioId) {
        MascotaM modelo = new MascotaM();
        modelo.setApodo(apodo);
        modelo.setEdad(edad);
        modelo.setRaza(raza);
        modelo.setUsuario(usuarioId);
        return modelo.registrar();
    }

    public String modificar(int id , String apodo, String edad, String raza, int usuarioId, int idtipoa) {
        MascotaM modelo = MascotaM.FindById(id);
        if (modelo != null) {
            modelo.setId(id);
            modelo.setApodo(apodo);
            modelo.setEdad(edad);
            modelo.setUsuario(usuarioId);
            modelo.setRaza(raza);
            modelo.setTipoAnimal(idtipoa);
            return modelo.modificar();
            
        } else {
            return "La mascota con el id " + id + " no se encontro.";
        }
    }

    public String eliminar(int id) {
        MascotaM modelo = MascotaM.FindById(id);
        if (modelo != null) {
            return modelo.eliminar();
        } else {
            return "La mascota con el numero " + id + " no se encontro.";
        }        
    } 
}