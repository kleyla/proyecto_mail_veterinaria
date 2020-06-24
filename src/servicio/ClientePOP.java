package servicio;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeoutException;

public class ClientePOP {
    private static int UNILINE=0;
    private static int BILINE=1;
    private static int MULTILINE=2;
    private static String Servidor ="mail.ficct.uagrm.edu.bo";
    private static String Usuario ="grupo03sa";
    private static String Contraseña ="grupo03grupo03";
    private int Puerto=110;
    
    private String getMensaje(String entrada){
        String aux=entrada.toUpperCase();
      //  System.out.println("S : getMensaje\n");
        int pos=aux.indexOf("SUBJECT: ")+8;
       // System.out.println("S : Pos subject "+pos+"\n");
        int fin=entrada.indexOf("\n",pos);
     //   System.out.println("S : Fin subject "+fin+"\n");
        return entrada.substring(fin).trim();
    }
    private String getSubject(String entrada){
        String aux=entrada.toUpperCase();
      //  System.out.println("S : getSubject\n");
        int pos=aux.indexOf("SUBJECT: ")+8;
      //  System.out.println("S :Length "+entrada.length()+" pos "+pos+"\n");
        int fin=entrada.indexOf("\n",pos);
      //  System.out.println("S : fin "+fin+"\n");
        return entrada.substring(pos,fin).trim();
    }
    private String getCorreoUser(String entrada){
        String aux=entrada.toUpperCase();
     //   System.out.println("S : getCorreoUser\n");
        int pos=aux.indexOf("FROM: ")+6;
      //  System.out.println("S :Length "+entrada.length()+" pos "+pos+"\n");
        int fin=entrada.indexOf("\n",pos);
        
        //correos enviados desde un servidor de correo ej. Gmail
        int auxini=entrada.indexOf("<",pos);
        int auxfin=entrada.indexOf(">",pos);
        if(auxini!=-1 && auxfin!=-1)
        {
            if(pos<auxini && fin>auxfin){
                pos=auxini+1;
                fin=auxfin;
            }
        }
        
        //System.out.println("S : fin "+fin+"\n");
        return entrada.substring(pos,fin).trim();
    }
    private int getIdPrimerMensaje(String entrada){
        //System.out.println("S : funcion getIdPrimerMensaje\n");
        int pos=entrada.indexOf("messages:")+9;
        //System.out.println("S :Length "+entrada.length()+" pos "+pos+"\n");
        int fin=entrada.indexOf(" ",pos);
        //System.out.println("S : fin "+fin+"\n");
        String valor=entrada.substring(pos,fin).trim();
        //System.out.println("S : valor "+valor+"\n");
        return Integer.parseInt(valor);
    }
    private int getNumeroMensajes(String entrada){
        int pos=entrada.indexOf("+OK")+3;
        int fin=entrada.indexOf("messages");
        String valor=entrada.substring(pos,fin).trim();
        return Integer.parseInt(valor);
    }
    static protected String getMultiline(BufferedReader in) throws IOException{
        String lines = "";
        while (true){
            String line = in.readLine();
            if (line == null){
               throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")){
                break;
            }
            lines=lines+"\r\n"+line;
        }
        return lines;
    }
    private String ejecutarComando(BufferedReader entrada, DataOutputStream salida, String comando,int tipoRespuesta){
        try{
         //   System.out.print("C : "+comando);
            salida.writeBytes( comando );
            String respuesta;
            if(tipoRespuesta==UNILINE){
                respuesta=entrada.readLine();
            }else if(tipoRespuesta==BILINE){
                respuesta=entrada.readLine();
                respuesta+="\n"+entrada.readLine();
            }else{
                respuesta=getMultiline(entrada);
            }
          //  System.out.println("S : "+respuesta+"\n");
            return respuesta;
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }
    public Mensaje LeerCorreo(){
        Mensaje msj = null;
        try{
          //  System.out.println(" S : Leyendo correo");
            String comando="";
            Socket socket=new Socket(Servidor,Puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream (socket.getOutputStream());
            String respuesta="";
            if( socket != null && entrada != null && salida != null ){
                //System.out.println("S : "+entrada.readLine()+"\r\n");
                comando="USER "+Usuario+"\r\n";
                respuesta=ejecutarComando(entrada, salida, comando,UNILINE);
                if(respuesta.indexOf("+OK")==-1)
                    return null;         
                
                comando="PASS "+Contraseña+"\r\n";
                respuesta=ejecutarComando(entrada, salida, comando,BILINE);
                if(respuesta.indexOf("+OK")==-1)
                    return null;
                
                comando="LIST \r\n";
                respuesta=ejecutarComando(entrada, salida, comando, MULTILINE);
                if(getNumeroMensajes(respuesta)>0){
          //          System.out.println("S : Hay "+getNumeroMensajes(respuesta)+" mensajes en la bandeja\n");
                    int idmensaje=getIdPrimerMensaje(respuesta);
            //        System.out.println("S : El Id del primer mensaje es "+idmensaje+"\n");
                    comando="RETR "+idmensaje+"\n";
                    String data=ejecutarComando(entrada, salida, comando, MULTILINE);
                    if(data.indexOf("+OK")!=-1){
                        String correo=getCorreoUser(data);
              //          System.out.println("S : From: "+correo+"\n");
                        String subject=getSubject(data);
                //        System.out.println("S : Subject: "+subject+"\n");
                        String mensaje=getMensaje(data);
                      //  System.out.println("S : Mensaje: "+mensaje+"\n");
                        msj = new Mensaje(idmensaje, correo, subject,mensaje);
                    }
                }
                comando="QUIT\r\n";
              //  System.out.print("C : "+comando);
                salida.writeBytes( comando );                
               // System.out.println("S : "+entrada.readLine()+"\r\n");
                
            }
            salida.close();
            entrada.close();
            socket.close();
        }catch(UnknownHostException e){
           System.out.println(e.getMessage());
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return msj;
    }
    
    public boolean eliminarCorreo(int idmensaje){
        try{
            System.out.println(" S : Eliminando correo");
            String comando="";
            Socket socket=new Socket(Servidor,Puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream (socket.getOutputStream());
            String respuesta="";
            if( socket != null && entrada != null && salida != null ){
                //System.out.println("S : "+entrada.readLine()+"\r\n");
                comando="USER "+Usuario+"\r\n";
                respuesta=ejecutarComando(entrada, salida, comando,UNILINE);
                if(respuesta.indexOf("+OK")==-1){
                    salida.close();
                    entrada.close();
                    socket.close();
                    return false;         
                }
                
                comando="PASS "+Contraseña+"\r\n";
                respuesta=ejecutarComando(entrada, salida, comando,BILINE);
                if(respuesta.indexOf("+OK")==-1){
                    salida.close();
                    entrada.close();
                    socket.close();
                    return false;
                }
                
                comando="DELE "+idmensaje+"\r\n";
                respuesta=ejecutarComando(entrada, salida, comando,UNILINE);
                if(respuesta.indexOf("+OK")==-1){
                    salida.close();
                    entrada.close();
                    socket.close();
                    return false;
                }
                
                comando="QUIT\r\n";
           //     System.out.print("C : "+comando);
                salida.writeBytes( comando );                
           //     System.out.println("S : "+entrada.readLine()+"\r\n");
                
            }
            salida.close();
            entrada.close();
            socket.close();
        }catch(UnknownHostException e){
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        }catch (IOException e){
            e.printStackTrace();
        }catch(Exception e){
            
        }
        return true;
    }
}