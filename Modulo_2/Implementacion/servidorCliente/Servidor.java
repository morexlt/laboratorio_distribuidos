import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;
//nuevos imports
import java.rmi.*;
import java.rmi.server.*;


public class Servidor {
    //static datosServidor
    public static void main(String args[]) throws IOException {
    	
        if(args.length != 1){
            System.err.println("Uso: Servidor Puerto");
            return;
        }
        if(System.getSecurityManager() == null){
            System.setProperty("java.rmi.server.hostname","localhost");
        }

        try{

            System.out.println("Servidor> Etapa de Configuracion");  
            BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in)); 
            System.out.println("Servidor> Ingrese el host en donde se encuentra el ServidorHoroscopo");  
            System.out.println("Servidor> (localhost)");  
            String hosth = brRequest.readLine();  

            System.out.println("Servidor> Ingrese el puerto en donde se encuentra el ServidorHoroscopo");  
            //System.out.println("Servidor> (54323)");  
            String porth = brRequest.readLine();    

            System.out.println("Servidor> Ingrese el host en donde se encuentra el ServidorPronostico");  
            System.out.println("Servidor> (localhost)");  
            String hostp = brRequest.readLine();  

            System.out.println("Servidor> Ingrese el puerto en donde se encuentra el ServidorPronostico");  
            //System.out.println("Servidor> (54323)");  
            String portp = brRequest.readLine();    

            String uriH = hosth+":"+porth;
            String uriP = hostp+":"+portp;


            DatosServidor datosServidor = new DatosServidor();
            PronosHoros pronhoros = new PronosHorosImp(datosServidor,uriH,uriP);
            Naming.rebind("rmi://localhost:"+args[0]+"/PronosHorosImp",pronhoros);
        }catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1); }
        catch (Exception e) {
            System.err.println("Excepcion en ServidorEco:");
            e.printStackTrace();
            System.exit(1); 
        }
        


        /*

        //inicializo datos
        DatosServidor datosServidor = new DatosServidor();

	
        ServerSocket ss;
        System.out.print("Inicializando servidor... ");
        try {
            ss = new ServerSocket(10577);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante: "+socket);
                ((ServidorHilo) new ServidorHilo(socket, idSession, datosServidor)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }




}
