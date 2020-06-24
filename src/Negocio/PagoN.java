/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Dato.ReservaM;
import Dato.PagoM;
import Dato.ServicioM;
import java.util.ArrayList;
import servicio.Contenido;
import servicio.Mensaje;

public class PagoN {
    Contenido contenido;
    Mensaje mensaje;
    
    public PagoN(Contenido contenido) {
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
                    registrar(Integer.parseInt(contenido.obtenerAtributo()), Integer.parseInt(contenido.obtenerAtributo()));
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR PAGO", "<h1>--REGISTRO EXITOSO--</h1>");
                    break;              
                case "LISTAR":
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR PAGO", "\n" + obtenerDatos());
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
        ArrayList<PagoM> lista = PagoM.All();
        String result = "<h1>LISTA DE PAGOS</h1>" +
"<table>" + 
"<tr>\n" +
"<th>ID</th>\n" +
"<th>MONTO TOTAL</th>\n" +
"<th>FECHA</th>\n" +
"</tr>\n";
        for(int i = 0; i < lista.size(); i++) {
            PagoM modelo = lista.get(i);
            result = result + "<tr>\n" 
                    + "<td>" + Integer.toString(modelo.getId()) + "</td>\n"
                    + "<td>" + Float.toString(modelo.getMontototal())+ "</td>\n"
                    + "<td>" + modelo.getFecha()+ "</td>\n"
                    + "</tr>\n";
        }
        result = result + "</table>";
        return result;
    }
    
    public String registrar(int idserv1, int idserv2 ) {
        PagoM modelo=new PagoM();
        int monto = modelo.sumarprecio(idserv1, idserv2);
        String resultado = modelo.registrar(monto);
        if(resultado == "registro"){
            int obeteneridultimo = modelo.ultimoid();
            int respuesta1 = modelo.registrarDetalle(obeteneridultimo,idserv1); 
            int respuesta2 = modelo.registrarDetalle(obeteneridultimo,idserv2); 
        }
        return "correcto";
    }

}