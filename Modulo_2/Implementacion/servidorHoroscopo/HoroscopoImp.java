import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.text.*;

public class HoroscopoImp extends UnicastRemoteObject implements Horoscopo
{
    protected DatosServidor datosServidor;
    
    protected HoroscopoImp(DatosServidor datosServidor) throws RemoteException { 
        super();
        this.datosServidor = datosServidor;
    }
   
    public String getHoroscopo(String signo) throws RemoteException { 
        int t =0;
        String result="";
        boolean error = false;
        signo = signo.toLowerCase();
        signo = Character.toUpperCase(signo.charAt(0)) + signo.substring(1); 
      
        switch(signo){
            case "Acuario":
            case "Piscis":
            case "Aries":
            case "Tauro":
            case "Geminis":
            case "Cancer":
            case "Leo":
            case "Virgo":
            case "Libra":
            case "Escorpio":
            case "Sagitario":
            case "Capricornio":
            break;
            default:
                result = "No se encuentra signo";
                error = true;
            break;
        }       
        if(!error){ 
            Map cache = datosServidor.getCache();
            System.out.println("Servidor Hijo");
            System.out.println(signo + cache.get(signo));

            if(cache.get(signo) != null){
               result = (String)cache.get(signo);
            }else{
               ArrayList<String> horoscoposList = datosServidor.getHoroscopos();
               Random ranClas = new Random();
               int randomNum = ranClas.nextInt(horoscoposList.size());
               result = horoscoposList.get(randomNum);
               datosServidor.actCache(signo,result);
            }
        }

        return result; 
    }
    
}


