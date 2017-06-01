package mainCliente;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


 
public class PronosHorosClient{

 	
 	
    public static void main(String[] args) {
    	boolean exit=false;

        String parteFecha = "";
        String parteSigno = "";

        String[] splitArray;
        boolean errorFecha;
        boolean errorSigno;
        String request = "";
        String signo = "";
        String fecha = "";

        try {            
            System.out.println("Cliente> Inicio");  
            while( !exit ){    
                                    
                errorFecha = false;
                errorSigno = false;

                   
                //PronosHoros pronosHoros = (PronosHoros)Naming.lookup ("//localhost:54323/PronosHorosImp");
                URL url = new URL("http://localhost:7780/ws/PronosHoros?wsdl");
            	QName qname = new QName("http://sc/", "PronosHorosImpl");
        	    Service service = Service.create(url, qname);
                PronosHoros  pronosHoros = service.getPort(PronosHoros.class);
                
                



                BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in));            
                System.out.println("Cliente> Escriba comando");                
                request = brRequest.readLine();   

                if(request.equals("exit")){
                    exit=true;                  
                    System.out.println("Cliente> Fin de programa");    
                }else if(request.equals("help")){
                    manPage();
                }else{

                    Pattern patHoroscopo = Pattern.compile("(?i)(Acuario|Piscis|Aries|Tauro|Geminis|Cancer|Leo|Virgo|Libra|Escorpio|Sagitario|Capricornio)");
                    Pattern patPronostico = Pattern.compile("(?i)(\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d)");

                    Matcher matHoroscopo = patHoroscopo.matcher(request);
                    Matcher matPronostico = patPronostico.matcher(request);
                    matHoroscopo.find();
                    matPronostico.find();

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


                    if(errorFecha && errorSigno){
                        System.out.println("Cliente> No se encontro una fecha o un Signo en el comando enviado."); 
                        System.out.println("Cliente> 'exit' para salir o 'help' para obtener la ayuda"); 
                        System.out.println("Cliente> Abortado."); 

                    }else if(errorFecha){
                        System.out.println("Cliente> No se encontro una fecha en el comando enviado."); 
                        System.out.print("Cliente> Desea consulta solamente el Horoscopo?[SI/no]:"); 
                        request = brRequest.readLine(); 
                        System.out.print(request); 

                        request = request.toLowerCase();
                        if(request.equals("si") || request.equals("")) {
                            
                            String st = pronosHoros.getHoroscopo(signo);
                            if( st != null ){
                                System.out.println("Servidor> " + st ); 
                            }  
                        }else{
                            System.out.println("Cliente> Abortado.");
                        }

                    }else if(errorSigno){
                        System.out.println("Cliente> No se encontro un Signo en el comando enviado."); 
                        System.out.print("Cliente> Desea consulta solamente el Pronostico?[SI/no]:"); 
                        request = brRequest.readLine(); 
                        System.out.print(request); 

                        request = request.toLowerCase();
                        if(request.equals("si") || request.equals("")) {
                            String st = pronosHoros.getPronostico(fecha);
                            if( st != null ){
                                System.out.println("Servidor> " + st ); 
                            }  
                        }else{
                            System.out.println("Cliente> Abortado.");
                        }
                         

                    }else{
                        String st = pronosHoros.getPronosHoros(fecha,signo);
                        if( st != null ){
                            System.out.println("Servidor> " + st ); 
                        }  
                    }
                }
               

            }                                  
       } catch (Exception ex) {        
         System.err.println("Cliente> -" + ex);   
       }
    }






    public static void manPage(){
        System.out.println("ClienteHoroscopo \t BSD General Commands Manual \t ClienteHoroscopo"); 
        System.out.println("NAME");
        System.out.println("\t ClienteHoroscpo");
        System.out.println("SYNOPSIS");
        System.out.println("\t DD/MM/YYYY-Signo");
        System.out.println("DESCRIPTION");
        System.out.println("\tMediante este programa usted podra consultar el pronostico, asi como");
        System.out.println("\ttambien consultar el horoscopo de un determinado signo en la misma consulta");
        System.out.println("\tSi no se proveen opciones la consulta por defecto es DD/MM/YYYY-Signo");
        System.out.println("");
        System.out.println("\tEn donde:");
        System.out.println("");
        System.out.println("\tDD\t Dia del mes entre 1 y 31 dependiendo del mes");
        System.out.println("");
        System.out.println("\tMM\t Mes del año entre 1 y 12");
        System.out.println("");
        System.out.println("\tAAAA\t Año");
        System.out.println("");
        System.out.println("\tSigno\t Signo del Zodiaco Occidental");
        System.out.println("\t\t Acuario|Piscis|Aries|Tauro|Geminis|Cancer"); 
        System.out.println("\t\t Leo|Virgo|Libra|Escorpio|Sagitario|Capricornio"); 
    }
 
}


