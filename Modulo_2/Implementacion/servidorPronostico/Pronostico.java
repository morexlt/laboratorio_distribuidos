// interface que contiene los métodos del servicio

import java.rmi.*;


public interface Pronostico extends Remote {
    public String getPronostico (String pronostico) throws RemoteException; 
} 




