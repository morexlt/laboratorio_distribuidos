
import java.util.*;

public class DatosServidor {
 
    private Map cache;

    public DatosServidor() {
        //Inicializo cache
        this.cache = new HashMap();

    }

    public synchronized void actCache(String signo, String datosCompletos){
        this.cache.put(signo,datosCompletos);
    }

    public Map getCache(){
        return this.cache;
    }
}