package sc;
import javax.jws.WebService;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;

import java.util.*;
import java.text.*;

import java.util.List;
import java.util.regex.*;
//Service Implementation
@WebService(endpointInterface = "sc.PronosHoros")
public class PronosHorosImpl implements PronosHoros{
	
	protected DatosServidor datosServidor;

	protected PronosHorosImpl() { 
		super(); 
		this.datosServidor = new DatosServidor();
		
	}
   

	@Override
	public String getPronosHoros(String fecha, String signo) throws MalformedURLException {
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

                                          
                    	URL url = new URL("http://localhost:7780/ws/pronostico?wsdl");
                    	QName qname = new QName("http://sp/", "PronosticoImpl");
                	    Service service = Service.create(url, qname);
                        Pronostico  pronos = service.getPort(Pronostico.class);
                        respuestaPron = pronos.getPronostico(fecha);

                    }else{
                        respuestaPron = "";
                    }

                    if(!errorSigno){
                        //---------------------------------------------------
                        //              Server de Horoscopo
                        //---------------------------------------------------
                        
                        URL url = new URL("http://localhost:7780/ws/horoscopo?wsdl");
                    	QName qname = new QName("http://sh/", "HoroscopoImpl");
                	    Service service = Service.create(url, qname);
                        Horoscopo  horos = service.getPort(Horoscopo.class);
                        respuestaHoros = horos.getHoroscopo(signo);
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


	@Override
	public String getPronostico(String fecha) throws MalformedURLException {
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
                        
                    	URL url = new URL("http://localhost:7780/ws/pronostico?wsdl");
                    	QName qname = new QName("http://sp/", "PronosticoImpl");
                	    Service service = Service.create(url, qname);
                        Pronostico  pronos = service.getPort(Pronostico.class);
                        respuestaPron = pronos.getPronostico(fecha);

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


	@Override
	public String getHoroscopo(String signo) throws MalformedURLException {
		
		
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
                        URL url = new URL("http://localhost:7780/ws/horoscopo?wsdl");
                    	QName qname = new QName("http://sh/", "HoroscopoImpl");
                	    Service service = Service.create(url, qname);
                        Horoscopo  horos = service.getPort(Horoscopo.class);
                        respuestaHoros = horos.getHoroscopo(signo);
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





