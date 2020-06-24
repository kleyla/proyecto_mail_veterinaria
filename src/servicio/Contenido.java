/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

/**
 *
 * @author USER
 */
public class Contenido {
    String contenido;
    
    public Contenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String obtenerSiguiente(){
        String sig = "";
        if(contenido.length() > 1){
            int j = contenido.indexOf("}");
            if(j > -1) {
                sig = contenido.substring(1, j);
                contenido = contenido.substring(j+1);
            }
        }
        return sig;
    }
    
    public String obtenerAtributo(){
        String atr = "";
        if(contenido.length() > 1){
            int i = contenido.indexOf("[");
            int j = contenido.indexOf("]");
            if(j > -1) {
                atr = contenido.substring(i+1, j);
                contenido = contenido.substring(j+1);
            }
        }
        return atr; 
    }
 
}
