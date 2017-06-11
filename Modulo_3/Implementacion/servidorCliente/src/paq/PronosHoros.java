package paq;
import java.net.MalformedURLException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
//Service Endpoint Interface
@WebService()
@SOAPBinding(style = Style.DOCUMENT)
public interface PronosHoros{

   @WebMethod String getPronosHoros (String fecha, String signo) throws MalformedURLException ; 
   @WebMethod String getPronostico (String fecha) throws MalformedURLException;
   @WebMethod String getHoroscopo (String signo) throws MalformedURLException;

} 


