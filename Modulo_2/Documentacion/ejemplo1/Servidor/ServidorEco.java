import java.rmi.*;
import java.rmi.server.*;

class ServidorEco  {
    static public void main (String args[]) {
       if (args.length!=2) {
            System.err.println("Uso: ServidorEco IP Puerto");
            return;
        }
        if (System.getSecurityManager() == null) {
        	System.setSecurityManager(new RMISecurityManager()); 
	}
        try {
            ServicioEcoImpl srv = new ServicioEcoImpl();
            Naming.rebind("rmi://"+ args[0] + ":" + args[1] + "/Eco", srv);
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Excepcion en ServidorEco:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
