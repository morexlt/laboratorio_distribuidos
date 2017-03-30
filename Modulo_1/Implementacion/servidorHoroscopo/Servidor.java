import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;

public class Servidor {
    //static datosServidor
    public static void main(String args[]) throws IOException {
    
        //inicializo datos
        DatosServidor datosServidor = new DatosServidor();

        ServerSocket ss;
        System.out.print("Inicializando servidor... ");
        try {
            ss = new ServerSocket(10579);
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
    }




}