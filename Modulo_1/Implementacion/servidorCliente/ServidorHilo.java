import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;
import java.text.*;

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
            
            //if(accion.equals("hola")){
            //    System.out.println("El cliente con idSesion "+this.idSessio+" saluda");
            //    dos.writeUTF("adios");
            //}
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

        String respuestaHoros = "";
        String respuestaPron = "";

        String[] parts = request.split("-");
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556

        Map cache = this.padre.getCache();
        System.out.println("Servidor Hijo");
        System.out.println(request + cache.get(request));

        if(cache.get(request) != null){
           result = (String)cache.get(request);
        }else{
            try {  
                //System.out.println("Antes del Pronostico");

                //---------------------------------------------------
                //              Server de Pronostico
                //---------------------------------------------------
                socketPron = new Socket("localhost", 10578);//abre socket     
                DataOutputStream dosPron = new DataOutputStream(socketPron.getOutputStream());
                DataInputStream disPron = new DataInputStream(socketPron.getInputStream());                          
                dosPron.writeUTF(part1);
                respuestaPron = disPron.readUTF();
                socketPron.close();


                //System.out.println("Antes del Horoscopo");
                //---------------------------------------------------
                //              Server de Horoscopo
                //---------------------------------------------------
                socketHoros = new Socket("localhost", 10579);//abre socket     
                DataOutputStream dosHoros = new DataOutputStream(socketHoros.getOutputStream());
                DataInputStream disHoros = new DataInputStream(socketHoros.getInputStream());                          
                dosHoros.writeUTF(part2);
                respuestaHoros = disHoros.readUTF();
                socketHoros.close();

            } catch (IOException ex) {        
                System.err.println("Cliente> " + ex.getMessage());   
            }
            result = "El Pronostico para el "+part1+" es "+respuestaPron+"\n y el Horoscopo para "+part2+" es "+respuestaHoros;

            System.out.println(result);

            this.padre.actCache(request,result);
        }
        return result;
    }
}
