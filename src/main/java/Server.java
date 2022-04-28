
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author faezehpirmohammadi
 */
public class Server {
    private static ArrayList<ClientHandler>clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);


    public static void main(String[] args) throws IOException {

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
