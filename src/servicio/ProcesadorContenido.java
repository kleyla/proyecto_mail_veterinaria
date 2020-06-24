/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import Negocio.HistorialN;
import Negocio.MascotaN;
import Negocio.PagoN;
import Negocio.ServicioN;
import Negocio.ReporteEstadisticaN;
import Negocio.ReservaN;
import Negocio.UsuarioN;

public class ProcesadorContenido {
    private Contenido contenido;
    private Mensaje mensaje;

    private final String CU1 = "GESTIONAR_USUARIO"; //////ok
    private final String CU2 = "GESTIONAR_MASCOTA";/////ok
    private final String CU3 = "GESTIONAR_SERVICIO";//////"GESTIONAR PROMOCION";////ok
    private final String CU4 = "GESTIONAR_RESERVA";/////cu5//////ok
    private final String CU5 = "GESTIONAR_ATENCION";/////////////////////////////
    private final String CU6 = "GESTIONAR_HISTORIAL";/////ok
    private final String CU7 = "GESTIONAR_PAGO";////
    private final String CU8 = "REPORTES_Y_ESTADISTICAS";
    
    public final static String ERROR_COMANDO = "NO EXISTE COMANDO";
    public static final String HELP = "HELP";

    public ProcesadorContenido(Mensaje mensaje, Contenido contenido) {
        this.contenido = contenido;
        this.mensaje = mensaje;
    }

    public Mensaje getRespuesta() {

        Mensaje myMensaje = null;
        String casoUso = contenido.obtenerSiguiente();
        if (casoUso == "") {
            System.out.println("Mensaje sin comandos");
            return generarMensajeError(0);
        } else {
            System.out.println(" C : MENSAJE CON VARIOS DATOS EN COMANDO");

            switch (casoUso) {
                case CU1:
                    UsuarioN usuario = new UsuarioN(contenido);
                    myMensaje = usuario.analizar(mensaje);
                    break;              
                case CU2:
                    MascotaN mascota = new MascotaN(contenido);
                    myMensaje = mascota.analizar(mensaje);
                    break;
                case CU3:///*************
                    ServicioN servicio = new ServicioN(contenido);
                    myMensaje = servicio.analizar(mensaje);
                    break;
                case CU4:
                    ReservaN reserva = new ReservaN(contenido);
                    myMensaje = reserva.analizar(mensaje);
                    break;
                case CU6:
                    HistorialN historial = new HistorialN(contenido);
                    myMensaje = historial.analizar(mensaje);
                    break;
                case CU7:
                    PagoN pago = new PagoN(contenido);
                    myMensaje = pago.analizar(mensaje);
                    break;
                case CU8:
                    ReporteEstadisticaN re = new ReporteEstadisticaN(contenido);
                    myMensaje = re.analizar(mensaje);
                    break;    
                default:
                    myMensaje = generarComandosAyuda();
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

    private Mensaje generarComandosAyuda() {
        Mensaje myMensaje = new Mensaje(mensaje.getCorreo(), "AYUDA DEL SISTEMA\n",
                "FORMATO DE LOS COMANDOS:<br/>\n"
                + "{casoDeUso}{Operacion}{[Atributo1][Atributo2]...[AtributoN]}<br/>\n\n"
                + "LOS COMANDOS PERMITIDOS DEL SISTEMA SON: \n<br/>"
                //Gestionar Usuario
                + "**CASO DE USO : GESTIONAR USUARIO**\n"
                + "* LISTAR : \n"
                + "{GESTIONAR_USUARIO}{LISTAR}\n"
                + "* REGISTRARSE : \n"
                + "{GESTIONAR_USUARIO}{REGISTRAR}{[NOMBRE][APELLIDO][GENERO][DIRECCION][TELEFONO]}\n"
                + "* MODIFICAR : \n"
                + "{GESTIONAR_USUARIO}{MODIFICAR}{[USUARIOID][NOMBRE][APELLIDO][GENERO][DIRECCION][TELEFONO]}\n"
                + "* ELIMINAR : \n"
                + "{GESTIONAR_USUARIO}{ELIMINAR}{[USUARIOID]}\n"
                //Gestionar mascota
                + "**CASO DE USO : GESTIONAR MASCOTA**\n"
                + "* LISTAR : \n"
                + "{GESTIONAR_MASCOTA}{LISTAR}\n"
                + "* REGISTRARSE : \n"
                + "{GESTIONAR_MASCOTA}{INSERTAR}{[APODO][EDAD][RAZA][USUARIOID][IDTIPOANIMAL]}\n"
                + "* MODIFICAR : \n"
                + "{GESTIONAR_MASCOTA}{MODIFICAR}{[MASCOTAID][APODO][EDAD][RAZA][USUARIOID][IDTIPOANIMAL]}\n"
                + "* ELIMINAR : \n"
                + "{GESTIONAR_MASCOTA}{ELIMINAR}{[MASCOTAID]}\n\n"
                //Gestionar SERVICIO
                + "**CASO DE USO : GESTIONAR SERVICIO**\n"
                + "* LISTAR : \n"
                + "{GESTIONAR_SERVICIO}{LISTAR}\n"
                + "* REGISTRARSE : \n"
                + "{GESTIONAR_SERVICIO}{INSERTAR}{[NOMBRE][TIPOSERVICIO][PRECIO][IDRESERVA]}\n"
                + "* MODIFICAR : \n"
                + "{GESTIONAR_SERVICIO}{MODIFICAR}{[SERVICIOID][NOMBRE][TIPOSERVICIO][PRECIO][IDRESERVA]}\n"
                + "* ELIMINAR : \n"
                + "{GESTIONAR_SERVICIO}{ELIMINAR}{[SERVICIOID]}\n\n"
                //Gestionar RESERVA
                + "**CASO DE USO : GESTIONAR RESERVA**\n"
                + "* LISTAR : \n"
                + "{GESTIONAR_RESERVA}{LISTAR}\n"
                + "* REGISTRARSE : \n"
                + "{GESTIONAR_RESERVA}{REGISTRAR}{[FECHA][HORA][USUARIOID]}\n"
                + "* MODIFICAR : \n"
                + "{GESTIONAR_RESERVA}{MODIFICAR}{[RESERVAID][FECHA][HORA][USUARIOID]}\n"
                + "* ELIMINAR : \n"
                + "{GESTIONAR RESERVA}{ELIMINAR}{[RESERVAID]}\n"
                // GESTIONAR ATENCION        **********************
                + "**CASO DE USO : GESTIONAR ATENCION**\n"
                + "* LISTAR : \n"
                + "{GESTIONAR_ATENCION}{LISTAR}\n"
                + "* REGISTRARSE : \n"
                + "{GESTIONAR_ATENCION}{REGISTRAR}{[NOMBRE][APELLIDO][TELEFONO][CORREO][TIPO]}\n"
                + "* MODIFICAR : \n"
                + "{GESTIONAR_PAGO}{MODIFICAR}{[USUARIOID][NOMBRE][APELLIDO][TELEFONO][CORREO][TIPO]}\n"
                + "* ELIMINAR : \n"
                + "{GESTIONAR_PAGO}{ELIMINAR}{[USUARIOID]}\n"
                //GESTIONAR HISTORIAL
                + "**CASO DE USO : GESTIONAR HISTORIAL**\n"
                + "* LISTAR : \n"
                + "{GESTIONAR_HISTORIAL}{LISTAR}\n"
                + "* REGISTRARSE : \n"
                + "{GESTIONAR_HISTORIAL}{REGISTRAR}{[SERVICIO][FECHA][USUARIOID]}\n"
                + "* MODIFICAR : \n"
                + "{GESTIONAR_HISTORIAL}{MODIFICAR}{[HISTORIALID][SERVICIO][FECHA][USUARIOID]}\n"
                + "* ELIMINAR : \n"
                + "{GESTIONAR_HISTORIAL}{ELIMINAR}{[HISTORIALID]}\n"
                //GESTIONAR PAGO
                + "**CASO DE USO : GESTIONAR PAGO**\n"
                + "* LISTAR : \n"
                + "{GESTIONAR_PAGO}{LISTAR}\n"
                + "* REGISTRARSE : \n"
                + "{GESTIONAR_PAGO}{REGISTRAR}{[1IDSERVICIO][2IDSERVICIO]}\n"///////
                //REPORTE Y ESTADISTICA
                + "**CASO DE USO : REPORTE Y ESTADISTICA**\n"
                + " \n"
                + "\n{REPORTES_Y_ESTADISTICAS}{SERVICIOS_POR_USUARIO}\n"
                + "\n{REPORTES_Y_ESTADISTICAS}{RESERVA_POR_TIPOANIMAL}\n"
                + "\n{REPORTES_Y_ESTADISTICAS}{CANTIDAD_TIPO_ANIMAL}\n" );
                //FIN /*   
  /*              + "-------------------------------------------------------------------\n"
                + "<h1>GRUPO 03 SA</h1>\n");*/
        return myMensaje;
    }
}
