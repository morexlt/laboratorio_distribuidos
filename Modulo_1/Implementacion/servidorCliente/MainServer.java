//package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
/**
 * @see http://www.jc-mouse.net/
 * @author mouse
 */
public class MainServer {
 
    /**
     * Puerto 
     */
    private final static int PORT = 5000;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //Socket de servidor para esperar peticiones de la red
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor> Servidor iniciado");    
            System.out.println("Servidor> En espera de cliente...");    
            //Socket de cliente
            Socket clientSocket;
            while(true){
                //en espera de conexion, si existe la acepta
                clientSocket = serverSocket.accept();
                //Para leer lo que envie el cliente
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //para imprimir datos de salida                
                PrintStream output = new PrintStream(clientSocket.getOutputStream());
                //se lee peticion del cliente
                String request = input.readLine();
                System.out.println("Cliente> petición [" + request +  "]");
                //se procesa la peticion y se espera resultado
                String strOutput = process(request);                
                //Se imprime en consola "servidor"
                System.out.println("Servidor> Resultado de petición");                    
                System.out.println("Servidor> \"" + strOutput + "\"");
                //se imprime en cliente
                output.flush();//vacia contenido
                output.println(strOutput);                
                //cierra conexion
                clientSocket.close();
            }    
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * procesa peticion del cliente y retorna resultado
     * @param request peticion del cliente
     * @return String
     */
    public static String process(String request){
        int t =0;
        String result="";        
        //frases
        String[] phrases = {
            "Acuario",
            "Piscis",
            "Aries",
            "Tauro",
            "Géminis",
            "Cáncer",
            "Leo",
            "Virgo",
            "Libra",
            "Escorpio",
            "Sagitario",
            "Capricornio"};
	ArrayList<String> phrasesList = new ArrayList<String>();
	Collections.addAll(phrasesList, phrases);
        //libros
        String[] books = {
            "Divina Comedia - Dante Alighieri", 
            "Don Quijote de la Mancha - Miguel de Cervantes",
            "Cien años de soledad - Gabriel García Márquez",
            "Moby Dick - Herman Melville",
            "Ana Karenina - Lev Tolstói",
            "Eneida - Virgilio",
            "Otelo - William Shakespeare",
            "El viejo y el mar - Ernest Hemingway",
            "Orgullo y prejuicio - Jane Austen"};
	ArrayList<String> booksList = new ArrayList<String>();
	Collections.addAll(booksList, books);   

        //String string = "004-034556";

        String[] parts = result.split(":");
        System.out.println(parts[0]);                    


        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556

        System.out.println(parts[0]);                    

        System.out.println(parts[1]);                    


        result = "El primer parametro es: " + part1 + " y el segundo es: " + part2;


        //request.
        /*switch (request.toString()){
            case "horoscopo":
                result = phrasesList.get(0);

            break;
        }  
        /*       
                if (request.equals("frase")) {t=1;}
                if (request.equals("libro")) t=2; 
                if (request.equals("exit")) t=3;  
        switch(t){
            case 1:
                Collections.shuffle(phrasesList);
                result = phrasesList.get(0);
                break;
            case 2:
                Collections.shuffle(booksList);
                result = booksList.get(0);
                break;
            case 3:                
                result = "bye";
                break;
            default:
                result = "La peticion no se puede resolver.";
                break;
        }
        */
        return result;
    }
    
}
