package sp;
import javax.xml.ws.Endpoint;
//Endpoint publisher
public class PronosticoPublisher{
 
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:7780/ws/pronostico", new PronosticoImpl());
    }
 
}
