import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;
import java.text.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class ServidorHilo extends Thread {
    private Socket socket;
    private Socket socketPron;
    private Socket socketHoros;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;
    private DatosServidor padre;
    public ServidorHilo(Socket socket, int id, DatosServidor padre) {
        this.socket = socket;
        this.idSessio = id;
        this.padre = padre;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desconnectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        String accion = "";
        try {
            accion = dis.readUTF();
            String result = this.process(accion);
            dos.writeUTF(result);

        } 
        catch(EOFException ex){
            System.out.println("Deconeccion de cliente");
        }
        catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconnectar();
    }



    /**
     * procesa peticion del cliente y retorna resultado
     * @param request peticion del cliente
     * @return String
     */
    public String process(String request){
        String fechaFormat = "";
        String result="";
        boolean error = false;
        boolean errorFecha = false;
        boolean errorSigno = false;
        String signo = "";
        String fecha = "";

        String respuestaHoros = "";
        String respuestaPron = "";

        Pattern patHoroscopo = Pattern.compile("(?i)(Acuario|Piscis|Aries|Tauro|Geminis|Cancer|Leo|Virgo|Libra|Escorpio|Sagitario|Capricornio)");
        Pattern patPronostico = Pattern.compile("(?i)(\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d)");
        Matcher matHoroscopo = patHoroscopo.matcher(request);
        Matcher matPronostico = patPronostico.matcher(request);
        matHoroscopo.find();
        matPronostico.find();

        //Me fijo si Me mando un Signo del Horoscopo
        try{
            signo = matHoroscopo.group();
        }catch(Exception e){
            errorSigno = true;    
        }

        //Me fijo si mando una fecha
        try{
            fecha = matPronostico.group();
        }catch(Exception e){
            errorFecha = true;   
        }


        Map cache = this.padre.getCache();
        System.out.println("Servidor Hijo");
        System.out.println(request + cache.get(request));

        if(cache.get(request) != null){
           result = (String)cache.get(request);
        }else{
            try {  
                if(errorFecha && errorSigno){

                }else{

                    if(!errorFecha){
                        //---------------------------------------------------
                        //              Server de Pronostico
                        //---------------------------------------------------
                        socketPron = new Socket("localhost", 10578);//abre socket     
                        DataOutputStream dosPron = new DataOutputStream(socketPron.getOutputStream());
                        DataInputStream disPron = new DataInputStream(socketPron.getInputStream());                          
                        dosPron.writeUTF(fecha);
                        respuestaPron = disPron.readUTF();
                        socketPron.close();
                    }else{
                        respuestaPron = "";
                    }

                    if(!errorSigno){
                        //---------------------------------------------------
                        //              Server de Horoscopo
                        //---------------------------------------------------
                        socketHoros = new Socket("localhost", 10579);//abre socket     
                        DataOutputStream dosHoros = new DataOutputStream(socketHoros.getOutputStream());
                        DataInputStream disHoros = new DataInputStream(socketHoros.getInputStream());                          
                        dosHoros.writeUTF(signo);
                        respuestaHoros = disHoros.readUTF();
                        socketHoros.close();
                    }else{
                        respuestaHoros = "";
                    }

                }

            } catch (IOException ex) {        
                System.err.println("Cliente> " + ex.getMessage());   
            }

            if(respuestaHoros != "" && respuestaPron != ""){
                result = "El Pronostico para el "+respuestaPron+"\n y el Horoscopo para "+signo+" es "+respuestaHoros;
            }else if(respuestaPron != ""){
                result = "El Pronostico para el "+respuestaPron;
            }else if(respuestaHoros != ""){
                result = "El Horoscopo para "+signo+" es "+respuestaHoros;
            }

            System.out.println(result);

            this.padre.actCache(request,result);
        }
        return result;
    }
}
