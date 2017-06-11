package paq;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;
//Endpoint publisher
public class PronosHorosPublisher{
	protected static DatosServidor datosServidor;
 
	public static void main(String[] args) {
		datosServidor = new DatosServidor();
		
		String host = "localhost";
		String port = "7779";
		try{

            System.out.println("Servidor> Etapa de Configuracion");  
            BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in)); 
            System.out.println("Servidor> Ingrese el host en donde quiere publicar el ServidorCliente");  
            System.out.println("Servidor> (localhost)");  
            host = brRequest.readLine();
            System.out.println("Servidor> Ingrese el puerto en donde quiere publicar el ServidorCliente");  
            port = brRequest.readLine(); 
            
            
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
            
            PronosHorosImpl pronosHoros = new PronosHorosImpl();
            pronosHoros.inicializar(datosServidor,uriH,uriP);
            System.out.println("uriH");
            System.out.println(uriH);
            System.out.println("uriP");
            System.out.println(uriP);

        }catch (Exception e) {
            System.err.println("Excepcion en ServidorEco:");
            e.printStackTrace();
            System.exit(1); 
        }
		
        Endpoint.publish("http://"+host+":"+port+"/ws/PronosHoros", new PronosHorosImpl());
    }
	
	
 
}
