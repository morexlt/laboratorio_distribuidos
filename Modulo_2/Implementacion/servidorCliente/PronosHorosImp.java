import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.rmi.*;

import java.util.*;
import java.text.*;

import java.util.List;
import java.util.regex.*;

public class PronosHorosImp extends UnicastRemoteObject implements PronosHoros
{
    private DatosServidor datosServidor;
    protected PronosHorosImp(DatosServidor datosServidor) throws RemoteException { 
        super();
        this.datosServidor = datosServidor;
    }
   
    public String getPronosHoros(String fecha, String signo) throws RemoteException { 
        String fechaFormat = "";
        String result="";
        boolean error = false;
        boolean errorFecha = false;
        boolean errorSigno = false;
        //String signo = "";
        //String fecha = "";

        String request = fecha+"-"+signo;

        String respuestaHoros = "";
        String respuestaPron = "";

        Pattern patHoroscopo = Pattern.compile("(?i)(Acuario|Piscis|Aries|Tauro|Geminis|Cancer|Leo|Virgo|Libra|Escorpio|Sagitario|Capricornio)");
        Pattern patPronostico = Pattern.compile("(?i)(\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d)");
        Matcher matHoroscopo = patHoroscopo.matcher(signo);
        Matcher matPronostico = patPronostico.matcher(fecha);
        matHoroscopo.find();
        matPronostico.find();

        System.out.println("EL REQUEST ES -> " + request);
        //Me fijo si Me mando un Signo del Horoscopo
        try{
            signo = matHoroscopo.group();
        }catch(Exception e){
            errorSigno = true;    
        }

        //Me fijo si mando una fecha
        try{
            fecha = matPronostico.group();
        }catch(Exception e){
            errorFecha = true;   
        }


        Map cache = datosServidor.getCache();

        if(cache.get(request) != null){
           result = (String)cache.get(request);
        }else{
            try {  
                if(errorFecha && errorSigno){

                }else{

                    if(!errorFecha){
                        //---------------------------------------------------
                        //              Server de Pronostico
                        //---------------------------------------------------
                        Pronostico pron = (Pronostico)Naming.lookup("//localhost:54322/PronosticoImp");
                        respuestaPron = pron.getPronostico(fecha);

                    }else{
                        respuestaPron = "";
                    }

                    if(!errorSigno){
                        //---------------------------------------------------
                        //              Server de Horoscopo
                        //---------------------------------------------------
                        Horoscopo horo = (Horoscopo)Naming.lookup("//localhost:54321/HoroscopoImp");
                        respuestaHoros = horo.getHoroscopo(signo);
                    }else{
                        respuestaHoros = "";
                    }

                }

            } catch (Exception ex) {        
                System.err.println("Cliente> " + ex.getMessage());   
            }

            if(respuestaHoros != "" && respuestaPron != ""){
                result = "El Pronostico para el "+respuestaPron+"\n y el Horoscopo para "+signo+" es "+respuestaHoros;
            }else if(respuestaPron != ""){
                result = "El Pronostico para el "+respuestaPron;
            }else if(respuestaHoros != ""){
                result = "El Horoscopo para "+signo+" es "+respuestaHoros;
            }

            System.out.println(result);

            datosServidor.actCache(request,result);
        }
        return result;

    }

    public String getPronostico(String fecha) throws RemoteException { 
        String fechaFormat = "";
        String result="";
        boolean error = false;
        boolean errorFecha = false;

        String request = fecha;
 
        String respuestaPron = "";

        Pattern patPronostico = Pattern.compile("(?i)(\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d)");

        Matcher matPronostico = patPronostico.matcher(fecha);

        matPronostico.find();


        //Me fijo si mando una fecha
        try{
            fecha = matPronostico.group();
        }catch(Exception e){
            errorFecha = true;   
        }


        Map cache = datosServidor.getCache();
        System.out.println("Servidor Hijo");
        System.out.println(request + cache.get(request));

        if(cache.get(request) != null){
           result = (String)cache.get(request);
        }else{
            try {  
                if(errorFecha){

                }else{

                    if(!errorFecha){
                        //---------------------------------------------------
                        //              Server de Pronostico
                        //---------------------------------------------------
                        Pronostico pron = (Pronostico)Naming.lookup("//localhost:54321/PronosticoImp");
                        respuestaPron = pron.getPronostico(fecha);

                    }else{
                        respuestaPron = "";
                    }
                }

            } catch (Exception ex) {        
                System.err.println("Cliente> " + ex.getMessage());   
            }

            if(respuestaPron != ""){
                result = "El Pronostico para el "+respuestaPron;
            }

            System.out.println(result);

            datosServidor.actCache(request,result);
        }
        return result;

    }

    public String getHoroscopo(String signo) throws RemoteException { 
        String fechaFormat = "";
        String result="";
        boolean error = false;
        boolean errorSigno = false;
    
        //String signo = "";
        //String fecha = "";
        String request = signo;

        String respuestaHoros = "";
  

        Pattern patHoroscopo = Pattern.compile("(?i)(Acuario|Piscis|Aries|Tauro|Geminis|Cancer|Leo|Virgo|Libra|Escorpio|Sagitario|Capricornio)");
        
        Matcher matHoroscopo = patHoroscopo.matcher(signo);

        matHoroscopo.find();
 

        //Me fijo si Me mando un Signo del Horoscopo
        try{
            signo = matHoroscopo.group();
        }catch(Exception e){
            errorSigno = true;    
        }

        Map cache = datosServidor.getCache();
        System.out.println("Servidor Hijo");
        System.out.println(request + cache.get(request));

        if(cache.get(request) != null){
           result = (String)cache.get(request);
        }else{
            try {  
                if(errorSigno){

                }else{
                    if(!errorSigno){
                        //---------------------------------------------------
                        //              Server de Horoscopo
                        //---------------------------------------------------
                        Horoscopo horo = (Horoscopo)Naming.lookup("//localhost:54322/HoroscopoImp");
                        respuestaHoros = horo.getHoroscopo(signo);
                    }else{
                        respuestaHoros = "";
                    }

                }

            } catch (Exception ex) {        
                System.err.println("Cliente> " + ex.getMessage());   
            }

            if(respuestaHoros != ""){
                result = "El Horoscopo para "+signo+" es "+respuestaHoros;
            }

            System.out.println(result);

            datosServidor.actCache(request,result);
        }
        return result;

    }
    
}


