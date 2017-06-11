package paq;
import javax.jws.WebService;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import paq.DatosServidor;

import java.net.MalformedURLException;

import java.util.*;

import java.util.regex.*;
//Service Implementation
//@WebService(endpointInterface = "sc.PronosHoros")

@WebService(endpointInterface = "paq.PronosHoros")
public class PronosHorosImpl implements PronosHoros{
	
	protected static DatosServidor datosServidor;
	protected static String uriH;
	protected static String uriP;

	@Override
	public String getPronosHoros(String fecha, String signo) throws MalformedURLException {
		System.out.println("DESDE getPronosHoros");
		System.out.println("uriH");
        System.out.println(uriH);
        System.out.println("uriP");
        System.out.println(uriP);

        String result="";
        boolean errorFecha = false;
        boolean errorSigno = false;

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

                                          
                    	URL url = new URL("http://"+this.uriP+"/ws/pronostico?wsdl");
                    	QName qname = new QName("http://paq/", "PronosticoImplService");
                    	
                    	Service service = Service.create(url, qname);
                    	QName port_name = new QName("http://paq/", "PronosticoImplPort");
                        Pronostico  pronos = service.getPort(port_name,Pronostico.class);
                        respuestaPron = pronos.getPronostico(fecha);

                    }else{
                        respuestaPron = "";
                    }

                    if(!errorSigno){
                        //---------------------------------------------------
                        //              Server de Horoscopo
                        //---------------------------------------------------
                        
                        URL url = new URL("http://"+this.uriH+"/ws/horoscopo?wsdl");
                    	QName qname = new QName("http://paq/", "HoroscopoImplService");
                	    Service service = Service.create(url, qname);
                	    QName port_name = new QName("http://paq/", "HoroscopoImplPort");
                        Horoscopo  horos = service.getPort(port_name,Horoscopo.class);
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
                        
                    	URL url = new URL("http://"+this.uriP+"/ws/pronostico?wsdl");
                    	QName qname = new QName("http://paq/", "PronosticoImplService");
                    	
                    	Service service = Service.create(url, qname);
                    	QName port_name = new QName("http://paq/", "PronosticoImplPort");
                        Pronostico  pronos = service.getPort(port_name,Pronostico.class);
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
		
		//return "MORE CAPO";
		
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

        //Map cache = datosServidor.getCache();
        Map cache  = new HashMap<String, String>();
        System.out.println("Servidor Hijo");
        System.out.println(request + cache.get(request));
        
        System.out.println("this.uriH");
        System.out.println(this.uriH);
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
                    	URL url = new URL("http://"+this.uriH+"/ws/horoscopo?wsdl");
                    	QName qname = new QName("http://paq/", "HoroscopoImplService");
                	    Service service = Service.create(url, qname);
                	    QName port_name = new QName("http://paq/", "HoroscopoImplPort");
                        Horoscopo  horos = service.getPort(port_name,Horoscopo.class);
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
  
	public void inicializar(DatosServidor datosServidor,String uriH, String uriP){
		   this.datosServidor= new DatosServidor();
		   this.uriH = uriH;
		   this.uriP = uriP;
		   System.out.println("EN INICIALIZAR");
		   System.out.println("uriH");
           System.out.println(uriH);
           System.out.println("uriP");
           System.out.println(uriP);

	}
   
}





