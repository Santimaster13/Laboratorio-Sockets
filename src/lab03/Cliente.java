/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab03;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Santi Mercado
 */
public class Cliente {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<Integer> vector;
    private ArrayList<Integer> resultado;
    
    public ArrayList<Integer> GetResultado(){
        return this.vector;
    }

    public void Connect_to_Server(String ip, int port){
        try {
                    InetAddress address = InetAddress.getByName(ip);
            clientSocket = new Socket(address, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            
        }
    }
    
    public void Operacion(){
        try {
            
            vector = new ArrayList<Integer>();
            String tam = in.readLine();
            int tamaño = Integer.parseInt(tam);
            for (int i=0; i<tamaño; i++){
                    vector.add(Integer.parseInt(in.readLine()));
            }
            int opcion = Integer.parseInt(in.readLine());
            int pivote = Integer.parseInt(in.readLine());
            System.out.println("Connection Succesful");
            long startTime = System.nanoTime();      
            if(opcion == 1){
                Mergesort(0, vector.size()-1);
            }
            if (opcion == 2){
                Heapsort();
            } 
            if(opcion == 3){
                if (pivote == 1){
                  QuicksortL(vector.size()-1, 0);   
                } else {
                   QuicksortR(vector.size()-1, 0);  
                }
            }
            long estimatedTime = System.nanoTime() - startTime;
            sendMessage(".");
            sendMessage(Long.toString(estimatedTime));
            EnviarV(vector);
            stop();
        } catch (Exception e){
            System.out.println("Connection Failure");
        }
        
    }
    
    public void Mergesort(int izq, int der){
        if (izq < der){
            int centro=(izq+der)/2;
            Mergesort(izq, centro);
            Mergesort(centro+1, der);                                                                                
            Merge(izq, centro, der);                                                                                 
    }
    }
    
    public void Merge(int izq, int centro, int der){
       int i, j, k;
   int [] vec2 = new int[vector.size()]; 
   for (i=izq; i<=der; i++){
       vec2[i] = vector.get(i);
   }                                           

   i=izq; j=centro+1; k=izq;
     
   while (i<=centro && j<=der){
      if (vec2[i]<=vec2[j]){
          if (k != i){
              sendMessage("Confirmed");
         sendMessage("Replacing the value at position " + k + " to " + vec2[i]);
          sendMessage("Succesful");
          sendMessage("");
          vector.set(k, vec2[i]);
          }
             
             i++;
             k++;
          } else{
          if (k != j){
             vector.set(k, vec2[j]);
          sendMessage("Confirmed");
          sendMessage("Replacing the value at position " + k + " to " + vec2[j]);
          sendMessage("Succesful");
          sendMessage(""); 
          }
          
          k++;
          j++; 
          }
   }                                    
              
   while (i<=centro) {
       if (k != i){
               vector.set(k, vec2[i]);
      sendMessage("Confirmed");
          sendMessage("Replacing the value at position " + k + " to " + vec2[i]);
          sendMessage("Succesful");
          sendMessage("");  
       }
      k++;
      i++;
   }          
        
    }
    
    public void Heapsort(){
        for (int i = (vector.size()/2) - 1; i >= 0; i--) {
        Heapify(vector.size(), i);
    }
    
    for (int i = vector.size() - 1; i >= 0; i--) {
        int temp = vector.get(0);
        vector.set(0, vector.get(i));
        vector.set(i, temp);
        Heapify(i, 0);
    }
       
    }
    
    public void Heapify(int tam, int a){
      int izq = (2 * a)+ 1;
    int der = (2 * a) + 2;
    int mayor = a;
    if (izq < tam && vector.get(izq) > vector.get(mayor)) {
        mayor = izq;
    }
    if (der < tam && vector.get(der) > vector.get(mayor)) {
        mayor = der;
    }
    if (mayor != a) {
        int temp = vector.get(a);
        vector.set(a, vector.get(mayor));
        vector.set(mayor, temp);
        sendMessage("Confirmed");
         sendMessage("Swapping the value at positions " + a + " and " + mayor);
          sendMessage("Succesful");
          sendMessage("");
        Heapify(tam, mayor);
    }  
    }
    
    public void QuicksortL(int der, int izq){
        int pivote = vector.get(izq);
        int i=izq;    
        int j=der;         
 
  while(i < j){
        while(vector.get(i) <= pivote && i < j){ //<=
         i++;
     } 
     while(vector.get(j) > pivote){
         j--;
     }     
     
             
     if (i < j) { 
         int temp = vector.get(i);
         vector.set(i, vector.get(j));
         vector.set(j, temp);
         sendMessage("Confirmed");
         sendMessage("Swapping the value at positions " + i + " and " + j);
          sendMessage("Succesful");
          sendMessage("");
     }
   }
  
  if (izq != j){
     vector.set(izq, vector.get(j));
   vector.set(j, pivote); 
      sendMessage("Confirmed");
         sendMessage("Swapping the value at position " + izq + " and " +  j);
          sendMessage("Succesful");
          sendMessage(""); 
  }
     
             if(izq < j-1){
     QuicksortL(j-1, izq);      
   }
            
   if(j+1 < der){
     QuicksortL(der, j+1);   
   }  
    }
    
    public void QuicksortR(int der, int izq){
        int pivote = vector.get(der);
        int i=izq;    
        int j=der;         
 
  while(i < j){
        while(vector.get(i) < pivote && i < j){ //<=
         i++;
     } 
     while(vector.get(j) >= pivote && i < j){
         j--;
     }     
     
             
     if (i < j) { 
         int temp = vector.get(i);
         vector.set(i, vector.get(j));
         vector.set(j, temp);
         sendMessage("Confirmed");
         sendMessage("Swapping the value at positions " + i + " and " + j);
          sendMessage("Succesful");
          sendMessage("");
     }
   }
  if (der != i){
     vector.set(der, vector.get(i));
   vector.set(i, pivote); 
      sendMessage("Confirmed");
         sendMessage("Swapping the value at position " + der + " and " +  j);
          sendMessage("Succesful");
          sendMessage("");
  }
     
             if(izq < j-1){
     QuicksortR(j-1, izq);      
   }
            
   if(j+1 < der){
     QuicksortR(der, j+1);   
   }  
    }

    public void sendMessage(String msg){
        try {
            out.println(msg);
        } catch (Exception e) {
            System.out.println("error de impresion");
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args){
        Cliente c = new Cliente();
        String ip;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the server's IP address");
        ip = sc.nextLine();
        c.Connect_to_Server(ip, 5555);
        System.out.println("Establishing connection...");
        c.Operacion();
    }
}
