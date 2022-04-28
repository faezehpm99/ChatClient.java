
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author faezehpirmohammadi
 */
public class Client {
    private static final String ip ="127.0.0.1";
      public static void main(String[] args) {
          try {
              Socket socket = new Socket("localhost",5678);
              Scanner fromConsole = new Scanner(System.in);
              Scanner fromServer = new Scanner(socket.getInputStream());
              PrintWriter fromClient = new PrintWriter(socket.getOutputStream(), true);
              String input, output;
              while (true){
                  System.out.println("client");
                  input= fromConsole.nextLine();
                  if(input.equals("exit")){
                      break;
                  }
                  fromClient.println(input);
                  System.out.print("server");
                  output=fromServer.nextLine();
                  System.out.println(output);

              }
              socket.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
    }
}
