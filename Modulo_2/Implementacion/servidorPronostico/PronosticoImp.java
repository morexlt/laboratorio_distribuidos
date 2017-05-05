import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.text.*;

public class PronosticoImp extends UnicastRemoteObject implements Pronostico
{
	protected DatosServidor datosServidor;
    
    protected PronosticoImp(DatosServidor datosServidor) throws RemoteException { 
        super();
        this.datosServidor = datosServidor;
    }
   
    public String getPronostico(String fecha) throws RemoteException { 
        String fechaFormat = "";
        String result="";
        boolean error = false;

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        
        try{
            Date date = format.parse(fecha);
            System.out.println("FechaFormat");
            System.out.println(date);
            
            fecha = new SimpleDateFormat("dd/MM/yyyy").format(date);
            System.out.println("fecha"); 
            System.out.println(fecha); 

        }catch(Exception e){
            System.out.println("Error en la fecha enviada por el cliente");
            result = "Error en la fecha enviada por el cliente";
            error = true;
        }

        if(!error){ 
            Map cache = datosServidor.getCache();
            System.out.println(fecha + cache.get(fecha));

            if(cache.get(fecha) != null){
               result = (String)cache.get(fecha);
            }else{
               ArrayList<String> pronosticosList = datosServidor.getPronosticos();
               Random ranClas = new Random();
               int randomNum = ranClas.nextInt(pronosticosList.size());
               result = pronosticosList.get(randomNum);
               datosServidor.actCache(fecha,result);
            }
        }

        return fecha+" es "+result;
    }
    
}


