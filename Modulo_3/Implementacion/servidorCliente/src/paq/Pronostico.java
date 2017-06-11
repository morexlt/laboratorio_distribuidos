package paq;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Pronostico{

// van las definiciones de los metodos

   @WebMethod String getPronostico (String fecha); 
 
} 


