package sh;
import javax.xml.ws.Endpoint;
//Endpoint publisher
public class HoroscopoPublisher{
 
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:7780/ws/horoscopo", new HoroscopoImpl());
    }
 
}
