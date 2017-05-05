// interface que contiene los métodos del servicio

import java.rmi.*;


public interface Horoscopo extends Remote {
    public String getHoroscopo (String signo) throws RemoteException; 
} 




