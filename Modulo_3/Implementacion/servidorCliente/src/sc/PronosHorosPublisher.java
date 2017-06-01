package sc;
import javax.xml.ws.Endpoint;
//Endpoint publisher
public class PronosHorosPublisher{
 
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:7779/ws/PronosHoros", new PronosHorosImpl());
    }
 
}
