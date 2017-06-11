package paq;

import java.util.*;

public class DatosServidor {
    //Map para guardar "signo"->"horoscopo"
    private Map cache;
    private ArrayList<String> pronosticosList;

    public DatosServidor() {
        //Inicializo cache
        this.cache = new HashMap();


        String[] pronosticos = {
            "Hoy y mañana con baja chance de alguna precipitación dispersa",
            "Miércoles y jueves con chance baja de precipitaciones dispersas",
            "Persisten precipitaciones dispersas de variada intensidad hasta la noche.",
            "Las condiciones mejorando hacia el final de la tarde",
            "Hoy no se descarta alguna precipitación a lo largo del día",
            "Sin lluvias por varios días!",
            "El tiempo mejora gradualmente en la mañana",
            "Chaparrones de moderada a fuerte intensidad",
            "Algún chaparrón de los que acompañan a esta tormenta, puede ser de moderada a fuerte intensidad, pero de corta duración",
            "Área de tormentas de reducidas dimensiones se acerca desde el O",
            "El tiempo estará algo fresco a agradable con 24ºC",
            "Se siguen registrando precipitaciones en forma dispersa"
        };
        pronosticosList = new ArrayList<String>();
        Collections.addAll(pronosticosList, pronosticos);  
    }

    public synchronized void actCache(String signo, String pronostico){
        this.cache.put(signo,pronostico);
    }

    public Map getCache(){
        return this.cache;
    }
    public ArrayList getPronosticos(){
        return this.pronosticosList;
    }
}