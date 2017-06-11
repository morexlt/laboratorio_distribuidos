package paq;

import javax.jws.WebService;

import paq.DatosServidor;

import java.util.*;
import java.text.*;

//Service Implementation
@WebService(endpointInterface = "paq.Horoscopo")
public class HoroscopoImpl implements Horoscopo{
	
	protected static DatosServidor datosServidor;



   @Override
    public String getHoroscopo (String signo) { 
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
       //return "MORE CAPO";
   }
   
	public void inicializar(DatosServidor datosServidor){
		   this.datosServidor= new DatosServidor();
	}

}

