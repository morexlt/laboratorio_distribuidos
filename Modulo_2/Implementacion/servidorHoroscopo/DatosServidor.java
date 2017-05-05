
import java.util.*;

public class DatosServidor {
    //Map para guardar "signo"->"horoscopo"
    private Map cache;
    private ArrayList<String> horoscoposList;

    public DatosServidor() {
        //Inicializo cache
        this.cache = new HashMap();


        String[] horoscopos = {
            "Las finanzas son bastante saludables la sorpresa te espera así que hay que consolidar tu situación",
            "Saca el mayor partido de un encuentro",
            "Hoy es día para dejar de lado las preocupaciones, el estrés y la ansiedad Hay una nueva decisión que tomar",
            "Ciertos arreglos económicos son algo irracionales Nadie te pide que pienses como un banquero, solo trata de ser más coherente en tus opciones financieras hoy",
            "Se prudente y no te arriesgues demasiado",
            "Te has ganado el derecho a la estima y a la admiración gracias al arduo trabajo y a una movida inteligente Los demás admirarán tu iniciativa",
            "Gracias a la conjunción astral de su signo, durante casi todo el mes, tendrá buena suerte y felicidad en el amor",
            "Las finanzas son bastante saludables la sorpresa te espera así que hay que consolidar tu situación"
                };
        horoscoposList = new ArrayList<String>();
        Collections.addAll(horoscoposList, horoscopos);  
    }

    public synchronized void actCache(String signo, String horoscopo){
        this.cache.put(signo,horoscopo);
    }

    public Map getCache(){
        return this.cache;
    }
    public ArrayList getHoroscopos(){
        return this.horoscoposList;
    }
}