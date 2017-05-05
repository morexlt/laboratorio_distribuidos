import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;
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
            DatosServidor datosServidor = new DatosServidor();
            Pronostico pron = new PronosticoImp(datosServidor);
            System.out.println("aowdmaiodawdmw");

            Naming.rebind("rmi://localhost:"+args[0]+"/PronosticoImp",pron);
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
            ss = new ServerSocket(10578);
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