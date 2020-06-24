package servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Esclavo {

    private Timer temporizador;
    private int milisegundosDeRetraso = 7000;

    public void start() {
        stop();
        temporizador = new Timer();
        planificar();
    }

    private void planificar() {
        if (temporizador != null) {
            Date timeToRun = new Date(System.currentTimeMillis() + milisegundosDeRetraso);
            temporizador.schedule(new TimerTask() {
                public void run() {
                    verificarBandeja();
                }
            }, timeToRun);
        }
    }

    /*Este metodo verificara cada rato el servidor
     */
    private synchronized void verificarBandeja() {
        System.out.println("verificado en " + System.currentTimeMillis());
        ClientePOP pop = new ClientePOP();
        Mensaje mensaje = pop.LeerCorreo();
        
        if (mensaje != null) {
            String contenidoMensaje = mensaje.getSubject();
            System.out.println(contenidoMensaje);
                
            // {GESTIONAR USUARIO}{INSERTAR}{[NOMBRE][APELLIDO][][]}
            Contenido contenido = new Contenido(contenidoMensaje);
            ProcesadorContenido procesador = new ProcesadorContenido(mensaje, contenido);
            Mensaje nuevoMsg = procesador.getRespuesta();
            System.out.println(nuevoMsg.toString());
          //  Mensaje respuesta = new Mensaje(mensaje.getCorreo(), "Respuesta", "");
            ClienteSMTP smtp = new ClienteSMTP();
            if (smtp.enviarCorreo(nuevoMsg)) {
                System.out.println(" S : Se envio el mensaje");
                //pop.eliminarCorreo(mensaje.getId());
            }
            pop.eliminarCorreo(mensaje.getId());
        } else {
            System.out.println(" S : No hay mensajes nuevos");
        }
        planificar();
    }

    private ArrayList<String> obtenerComandos(String subject) {
        String sub = subject.trim();
        System.out.println(" S : Obteniendo comandos");
        ArrayList<String> comandos = new ArrayList<>();

        if (sub != null && !sub.isEmpty()) {
            String raw = "";
            boolean isOpen = false;
            for (int i = 0; i < sub.length(); i++) {
                if (isOpen) {
                    if (sub.charAt(i) == ']') {
                        isOpen = false;
                        comandos.add(raw.trim());
                        raw = "";
                    } else {
                        raw += sub.charAt(i);
                    }
                } else {
                    if (sub.charAt(i) == '[') {
                        isOpen = true;
                    }
                }
            }
        }
        System.out.println(" S : Terminando obtencion de comandos");
        return comandos;
    }

    public void stop() {
        if (temporizador != null) {
            temporizador.cancel();
            temporizador = null;
        }
    }
}
