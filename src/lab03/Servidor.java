/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab03;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;
/**
 *
 * @author Santi Mercado
 */
public class Servidor {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public static boolean StrNum(String str) {
    if (str == null) {
        return false;
    }
    try {
        int t = Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
    public void CorregirVector(ArrayList<Integer> vector){
        boolean keep = false;
        do {
            keep = false;
          for (int i=0; i < vector.size(); i++){
            for (int j=i+1; j < vector.size(); j++){
                if (vector.get(i) == vector.get(j)){
                    Random r = new Random();
                    vector.set(j, r.nextInt(99999));
                    keep = true;
                }
            }
        }  
        } while(keep == true);
        
    }
    
    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Welcome, I'm the Great Sage!");
            System.out.println("Notice:");
            System.out.println("In order to start, please log in as Client in another PC");
            System.out.println("Please enter the following IP address in the client: ");
            String ip = InetAddress.getLocalHost().toString();
            boolean find = false;
            int pos = 0;
            while (find == false && pos < ip.length()){
                if (ip.substring(pos, pos+1).equals("/")){
                   find = true; 
                } 
                 pos++;
            }
            System.out.println(ip.substring(pos));
            System.out.println("Establishing connection...");
            clientSocket = serverSocket.accept();
            System.out.println("Succesful");
            System.out.println("");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Scanner sc = new Scanner(System.in);
            boolean correct = false;
            System.out.println("Notice:");
            System.out.println("Please enter the array's size");
            String input;
            while (correct==false){
            input = sc.nextLine();
                System.out.println("Genearting array...");
              if (StrNum(input) == true){
                int tam = Integer.parseInt(input);
                if (tam > 1){
                ArrayList<Integer> vec = new ArrayList<Integer>();
                for (int i = 0; i < tam; i++) {
                   Random r = new Random();
                   vec.add(r.nextInt(99999)); 
                }
                CorregirVector(vec);
                correct = true;
                System.out.println("Succesful");
                System.out.println("");
                System.out.println("Report: Array will be ordered from lowest to highest");
                System.out.println("Notice:");
                  System.out.println("Please select the ordering method");
                System.out.println("1. Megesort");
                System.out.println("2. Heapsort");
                System.out.println("3. Quicksort");
                System.out.println("4. Salir");
                boolean correct2 = false;
                while(correct2 == false){
                  input = sc.nextLine();
                    System.out.println("Selecting option...");
                if (input.equals("1") || input.equals("2") || input.equals("3")){
                    correct2 = true;
                    System.out.println("Succesful");
                    System.out.println("");
                    int opcion = Integer.parseInt(input);
                    int pivote = 0;
                    
                    if (input.equals("3")){
                        boolean correct3 = false;
                        System.out.println("Report: This algorithm requires a pivot");
                        System.out.println("Notice:");
                        System.out.println("Please select a pivot");
                        System.out.println("1. Leftmost value");
                        System.out.println("2. Rightmost value");
                        while(correct3 == false){
                            input = sc.nextLine();
                            correct3 = true;
                            System.out.println("Selecting pivot...");
                            if (input.equals("1") || input.equals("2")){
                                pivote = Integer.parseInt(input);
                                System.out.println("Succesful");
                            } else {
                                System.out.println("Failed");
                      System.out.println("Notice:");  
                      System.out.println("Please enter a valid option");
                      System.out.println("");
                            }
                        }
                    }              
                    System.out.println("Notice: ");
                    System.out.println("All data is going to be delivered to the Client, who will order the array and return it ordered.");
                    System.out.println("");
                    out.println(tam);
                    EnviarV(vec);
                    out.println(opcion);
                    out.println(pivote);
                    System.out.println("Succesful");
                    System.out.println("");
                    System.out.println("Notice:");
                    System.out.println("The original array is as follows");
                    ImprimirV(vec);
                    System.out.println("Starting the sort...");
                    System.out.println("");
                    boolean ordered = false;
                    while(ordered == false && (input = in.readLine()) != null){
                if(".".equals(input)){
                    System.out.println("Confirmed");
                    System.out.println("Array succesfully sorted");
                    System.out.println("Elapsed time: "+ in.readLine()+ " nanoseconds");
                    System.out.println("The sorted array is as follows: ");
                    RecibirV(vec);
                    ImprimirV(vec);
                    System.out.println("");
                    System.out.println("Thats all folk!");
                    ordered = true;
                    //stop();
                } else {
                    if ("vv".equals(input)){
                        RecibirV(vec);
                        ImprimirV(vec);
                    } else {
                      System.out.println(input);  
                    }
                
                }
            }
                } else {
                    if(input.equals("4")){
                        System.out.println("Succesful");
                        System.out.println("Thats all folk!");
                        System.exit(0);
                    } else {
                      System.out.println("Failed");
                      System.out.println("Notice:");  
                      System.out.println("Please enter a valid option");
                      System.out.println("");
                    }
                    
                } 
                
                }
                
              } else {
                      System.out.println("Failed");
                      System.out.println("Notice:");  
                      System.out.println("Please enter an array size bigger than 1");
                      System.out.println("");
              }  
                
            } else {
                     System.out.println("Failed");
                      System.out.println("Notice:");  
                      System.out.println("Please enter a valid size");
                      System.out.println("");
            }  
            }
            
            String cliente_input;
            while((cliente_input = in.readLine()) != null){
               out.println(cliente_input);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void ImprimirV(ArrayList<Integer> vector){
        for (int i=0; i<vector.size()-1; i++){
            System.out.print(vector.get(i)+ " --> ");
        }
        int x = vector.size()-1;
        System.out.print(vector.get(x));
        System.out.println("");
    }
    
    public void EnviarV(ArrayList<Integer> vector){
        for (int i=0; i<vector.size(); i++){
            out.println(vector.get(i));
        }
    }
    
    public void RecibirV(ArrayList<Integer> vector){
       for (int i=0; i<vector.size(); i++){
           try {
               vector.set(i, Integer.parseInt(in.readLine()));
           } catch (IOException ex) {
               
           }
        } 
    }

    public void stop(){
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Servidor l = new Servidor();
        l.start(5555);
    }
    
}
