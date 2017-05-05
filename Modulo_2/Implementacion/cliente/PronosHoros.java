// interface que contiene los métodos del servicio

import java.rmi.*;


public interface PronosHoros extends Remote {
    public String getPronosHoros (String fecha, String signo) throws RemoteException; 
    public String getPronostico (String fecha) throws RemoteException; 
    public String getHoroscopo (String signo) throws RemoteException; 
} 




