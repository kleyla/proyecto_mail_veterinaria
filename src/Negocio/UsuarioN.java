
package Negocio;

import Dato.UsuarioM;
import java.util.ArrayList;
import servicio.Contenido;
import servicio.Mensaje;

public class UsuarioN {
    Contenido contenido;
    Mensaje mensaje;
    
    public UsuarioN(Contenido contenido) {
        this.contenido = contenido;
    }
    
    public Mensaje analizar(Mensaje mensaje) {
        this.mensaje = mensaje;
        Mensaje myMensaje = null; //= new Mensaje(mensaje.getCorreo(), "GESTIONAR USUARIO", "-------------mensaje de respuesta ");
        String operacion = contenido.obtenerSiguiente();
        System.out.println(operacion);
        if (operacion == "") {
            System.out.println("Mensaje sin operacion");
            generarMensajeError(0);
        } else {
            System.out.println(" C : MENSAJE CON OPERACION EN COMANDO");

            switch (operacion) {
                case "REGISTRAR":
                    registrar(contenido.obtenerAtributo(), contenido.obtenerAtributo(), contenido.obtenerAtributo(), contenido.obtenerAtributo(), contenido.obtenerAtributo());
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR USUARIO", "<h1>--REGISTRO EXITOSO--</h1>");
                    break;              
                case "MODIFICAR":
                    modificar(Integer.parseInt(contenido.obtenerAtributo()), contenido.obtenerAtributo(), contenido.obtenerAtributo(), contenido.obtenerAtributo(), contenido.obtenerAtributo(), contenido.obtenerAtributo());
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR USUARIO", "<h1>--MODIFICACION EXITOSA--</h1>"); 
                    break;
                case "ELIMINAR":
                    eliminar(Integer.parseInt(contenido.obtenerAtributo()));
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR USUARIO", "<h1>--ELIMINACION EXITOSA--</h1>");
                    break;
                case "LISTAR":
                    myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR USUARIO", "\n" + obtenerUsuarios());
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
    
    public String registrar(String nombre, String apellido, String genero, String direccion, String telefono) {
        
        UsuarioM modelo = new UsuarioM();
        modelo.setNombre(nombre);
        modelo.setApellido(apellido);
        modelo.setTelefono(telefono);
        modelo.setDireccion(direccion);
        modelo.setGenero(genero);
        return modelo.registrar();
    }

    public String modificar(int usuarioID , String nombre, String apellido, String genero, String direccion, String telefono) {
        UsuarioM modelo = UsuarioM.FindById(usuarioID);
        if (modelo != null) {
            modelo.setNombre(nombre);
            modelo.setApellido(apellido);
            modelo.setTelefono(telefono);
            modelo.setDireccion(direccion);
            modelo.setGenero(genero);
            return modelo.modificar();
            
        } else {
            return "El usuario con el id " + usuarioID + " no se encontro.";
        }
    }

    public String eliminar(int usuarioID) {
        UsuarioM modelo = UsuarioM.FindById(usuarioID);
        if (modelo != null) {
            return modelo.eliminar();
        } else {
            return "El usuario con el numero " + usuarioID + " no se encontro.";
        }        
    }
  
    private String obtenerUsuarios(){
        ArrayList<UsuarioM> lista = UsuarioM.All();
        String result = "<h1>LISTA DE USUARIOS</h1>" +
"<table>" + 
"<tr>\n" +
"<th>ID</th>\n" +
"<th>NOMBRE</th>\n" +
"<th>APELLIDO</th>\n" +
"<th>TELEFONO</th>\n" +
"<th>DIRECCION</th>\n" +
"<th>GENERO</th>\n" +
"</tr>\n";
        for(int i = 0; i < lista.size(); i++) {
            UsuarioM modelo = lista.get(i);
            result = result + "<tr>\n" 
                    + "<td>" + Integer.toString(modelo.getId()) + "</td>\n"
                    + "<td>" + modelo.getNombre() + "</td>\n"
                    + "<td>" + modelo.getApellido() + "</td>\n"
                    + "<td>" + modelo.getTelefono() + "</td>\n"
                    + "<td>" + modelo.getDireccion() + "</td>\n"
                    + "<td>" + modelo.getGenero() + "</td>\n"
                    + "</tr>\n";
        }
        result = result + "</table>";
        return result;
    }
    
}