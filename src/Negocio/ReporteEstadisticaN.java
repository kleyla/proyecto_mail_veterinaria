/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;


import Dato.ReporteEstadisticaM;
import java.util.ArrayList;
import servicio.Contenido;
import servicio.Mensaje;

/**
 *
 * @author USER
 */
public class ReporteEstadisticaN {
    Contenido contenido;
    Mensaje mensaje;
    
    public ReporteEstadisticaN(Contenido contenido) {
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
                case "SERVICIOS_POR_USUARIO":
                    myMensaje = new Mensaje(mensaje.getCorreo(), "ESTADISTICAS SERVICIOS POR USUARIO", "\n" + servicios_usuarios());
                    break;              
                case "RESERVA_POR_TIPOANIMAL":
                    myMensaje = new Mensaje(mensaje.getCorreo(), "ESTADISTICAS RESERVA POR TIPOANIMAL", "\n" + reserva_tipoanimal());
                    break;
                
                case "CANTIDAD_TIPO_ANIMAL":
                    myMensaje = new Mensaje(mensaje.getCorreo(), "ESTADISTICAS CANTIDAD TIPOANIMAL", "\n" + cantidad_tipoanimal());
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
    
    private String servicios_usuarios(){
        ArrayList<ReporteEstadisticaM> est = ReporteEstadisticaM.getServicios_usuarios();
        int total_servicios = ReporteEstadisticaM.getTotalReservas();
        String all = "<style>\n" +
".graph { position: relative; /* IE is dumb */\n" +
"width: 200px;\n" +
"border: 1px solid #B1D632;\n" +
"padding: 5px;\n" +
"}\n" +
".graph .bar {\n" +
"display: block;\n" +
"position: relative;\n" +
"background: #B1D632;\n" +
"text-align: center;\n" +
"color: #333;\n" +
"height: 2em;\n" +
"line-height: 2em;\n" +
"}\n" +
".graph .bar span {\n" +
"position: absolute;\n" +
"left: 1em;\n" +
"}\n" +
"</style>\n" +
"<h1>Estadisticas de cantidad de reservas que se han solicitado por raza</h1>\n";
        for(int i = 0; i < est.size(); i++) {
            ReporteEstadisticaM e = est.get(i);
            double porcentaje = (double)(e.getCantidad()*100) / total_servicios;
            all = all + "<div class=\"graph\">\n" +
                e.getRaza() + "<strong class=\"bar\" style=\"width: " + redondearDecimales(porcentaje, 2) + "%;\">" + redondearDecimales(porcentaje, 2) + "%</strong>\n" +
                "</div>\n";
        }
        return all;
    }
    
    private String reserva_tipoanimal(){
        ArrayList<ReporteEstadisticaM> est = ReporteEstadisticaM.getReserva_tipoanimal();
        int total_reserva = ReporteEstadisticaM.getTotalReservas();
        String all = "<style>\n" +
".graph { position: relative; /* IE is dumb */\n" +
"width: 200px;\n" +
"border: 1px solid #B1D632;\n" +
"padding: 5px;\n" +
"}\n" +
".graph .bar {\n" +
"display: block;\n" +
"position: relative;\n" +
"background: #B1D632;\n" +
"text-align: center;\n" +
"color: #333;\n" +
"height: 2em;\n" +
"line-height: 2em;\n" +
"}\n" +
".graph .bar span {\n" +
"position: absolute;\n" +
"left: 1em;\n" +
"}\n" +
"</style>\n" +
"<h1>Estadisticas de cantidad de reservas que se han solicitado por especialidades</h1>\n";
        for(int i = 0; i < est.size(); i++) {
            ReporteEstadisticaM e = est.get(i);
            double porcentaje = (double)(e.getCantidad()*100) / total_reserva;
            all = all + "<div class=\"graph\">\n" +
                e.getRaza() + "<strong class=\"bar\" style=\"width: " + redondearDecimales(porcentaje, 2) + "%;\">" + redondearDecimales(porcentaje, 2) + "%</strong>\n" +
                "</div>\n";
        }
        return all;
    }

    private String cantidad_tipoanimal(){
        ArrayList<ReporteEstadisticaM> est = ReporteEstadisticaM.getCantidadtipoanimal();
        int total_mascotas = ReporteEstadisticaM.getTotalReservas();
        String all = "<style>\n" +
".graph { position: relative; /* IE is dumb */\n" +
"width: 200px;\n" +
"border: 1px solid #B1D632;\n" +
"padding: 5px;\n" +
"}\n" +
".graph .bar {\n" +
"display: block;\n" +
"position: relative;\n" +
"background: #B1D632;\n" +
"text-align: center;\n" +
"color: #333;\n" +
"height: 2em;\n" +
"line-height: 2em;\n" +
"}\n" +
".graph .bar span {\n" +
"position: absolute;\n" +
"left: 1em;\n" +
"}\n" +
"</style>\n" +
"<h1>Estadisticas de cantidad de consultas que se han solicitado por raza</h1>\n";
        for(int i = 0; i < est.size(); i++) {
            ReporteEstadisticaM e = est.get(i);
            double porcentaje = (double)(e.getCantidad()*100) / total_mascotas;
            all = all + "<div class=\"graph\">\n" +
                e.getRaza() + "<strong class=\"bar\" style=\"width: " + redondearDecimales(porcentaje, 2) + "%;\">" + redondearDecimales(porcentaje, 2) + "%</strong>\n" +
                "</div>\n";
        }
        return all;
    }
        
    
 
    public double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
    
}
