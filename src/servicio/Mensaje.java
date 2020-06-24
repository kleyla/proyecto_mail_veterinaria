
package servicio;

import java.util.ArrayList;


public class Mensaje {
    private int id;
    private String correo;
    private String subject;
    private String data;
    private ArrayList<String> comandos;
    private boolean bandera = true;

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
    
    

    public Mensaje(int id, String correo, String subject, String data) {
        this.id = id;
        this.correo = correo;
        this.subject = subject;
        this.data = data;
    }
    
    public Mensaje(String correo, String subject, String data) {
        this.correo = correo;
        this.subject = subject;
        this.data = data;
    }

    public Mensaje() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<String> getComandos() {
        return comandos;
    }

    public void setComandos(ArrayList<String> comandos) {
        this.comandos = comandos;
    }

    
    
    @Override
    public String toString() {
        return "Mensaje{" + "id=" + id + ", correo=" + correo + ", subject=" + subject + ", data=" + data + ", comandos=" + comandos + '}';
    }
    
}
