import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;
import java.text.*;

public class ServidorHilo extends Thread {
    private Socket socket;
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

        DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        
        try{
            Date date = format.parse(request);
            System.out.println("FechaFormat");
            System.out.println(date);

        }catch(Exception e){
            System.out.println("Error en la fecha enviada por el cliente");
            result = "Error en la fecha enviada por el cliente";
            error = true;
        }

        if(!error){ 
            Map cache = this.padre.getCache();
            System.out.println("Servidor Hijo");
            System.out.println(request + cache.get(request));

            if(cache.get(request) != null){
               result = (String)cache.get(request);
            }else{
               ArrayList<String> pronosticosList = this.padre.getPronosticos();
               Random ranClas = new Random();
               int randomNum = ranClas.nextInt(pronosticosList.size());
               result = pronosticosList.get(randomNum);
               this.padre.actCache(request,result);
            }
        }

        return result;
    }
}
