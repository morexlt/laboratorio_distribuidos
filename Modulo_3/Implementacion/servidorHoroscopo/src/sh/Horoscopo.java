package sh;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Horoscopo{

// van las definiciones de los metodos

   @WebMethod String getHoroscopo (String signo); 
 
} 


