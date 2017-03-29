//package cliente;
import java.io.*;
import java.net.Socket;

public class MainCliente {
	
    /**
    * Puerto
    * */
    private final static int PORT = 10578;
    /**
    * Host
    * */
    private final static String SERVER = "localhost";
    
    public static void main(String[] args) {
    	boolean exit=false;//bandera para controlar ciclo del programa
        Socket socket;//Socket para la comunicacion cliente servidor        
        try {            
            System.out.println("Cliente> Inicio");  
            while( !exit ){//ciclo repetitivo       
                                    
                socket = new Socket(SERVER, PORT);//abre socket   
                //BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));                
                //PrintStream output = new PrintStream(socket.getOutputStream());                
                    
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in));            
                System.out.println("Cliente> Escriba comando");                
                String request = brRequest.readLine();   

                

                if(request.equals("exit")){//terminar aplicacion
                    exit=true;                  
                    System.out.println("Cliente> Fin de programa");    
                }else{
                    dos.writeUTF(request);
                    String st = dis.readUTF();

                    if( st != null ){
                        System.out.println("Servidor> " + st ); 
                    }   
                }
                socket.close();
                

                /*
                sk = new Socket("127.0.0.1", 10578);
                dos = new DataOutputStream(sk.getOutputStream());
                dis = new DataInputStream(sk.getInputStream());
                System.out.println(id + " envía saludo");
                dos.writeUTF("hola");
                String respuesta="";
                respuesta = dis.readUTF();
                System.out.println(id + " Servidor devuelve saludo: " + respuesta);
                dis.close();
                dos.close();
                sk.close();
                */

            }//end while                                    
       } catch (IOException ex) {        
         System.err.println("Cliente> " + ex.getMessage());   
       }
    }
    
}
