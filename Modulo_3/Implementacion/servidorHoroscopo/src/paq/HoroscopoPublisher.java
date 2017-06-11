package paq;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;
//Endpoint publisher
public class HoroscopoPublisher{
	protected static DatosServidor datosServidor;

 
	public static void main(String[] args) {
		datosServidor = new DatosServidor();
		
		String host = "localhost";
		String port = "7779";
		try{

            System.out.println("Servidor> Etapa de Configuracion");  
            BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in)); 
            System.out.println("Servidor> Ingrese el host en donde quiere publica el ServidorHoroscopo");  
            System.out.println("Servidor> (localhost)");  
            host = brRequest.readLine();
            System.out.println("Servidor> Ingrese el puerto en donde quiere publica el ServidorHoroscopo");  
            port = brRequest.readLine(); 
              
            
            HoroscopoImpl pronosHoros = new HoroscopoImpl();
            pronosHoros.inicializar(datosServidor);


        }catch (Exception e) {
            System.err.println("Excepcion en ServidorEco:");
            e.printStackTrace();
            System.exit(1); 
        }
	   Endpoint.publish("http://"+host+":"+port+"/ws/horoscopo", new HoroscopoImpl());
    }
 
}
