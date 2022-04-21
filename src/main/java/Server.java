
import java.io.IOException;
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
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5678);
            Socket socket = serverSocket.accept();
            Scanner fromConsole= new Scanner(System.in);
            Scanner fromClient = new Scanner(socket.getInputStream());
            PrintWriter fromServer = new PrintWriter(socket.getOutputStream());
            String inputFromServer, outputFromConsol;
            while (true){
                inputFromServer = fromClient.nextLine();
                System.out.println("client"+inputFromServer);
                if(inputFromServer.equals("exit")){
                    break;
                }
                
                System.out.print("server");
                outputFromConsol=fromConsole.nextLine();
                fromServer.println(outputFromConsol);
                fromServer.flush();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
