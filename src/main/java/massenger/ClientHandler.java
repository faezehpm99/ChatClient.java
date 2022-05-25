package massenger;

import encrypt.MerkleHelman;
import utilities.Time;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private ServerSocket serverSocket;
    private Socket socket;
    private Scanner scaner;
    private PrintWriter writer;
    private Time time;
    private MerkleHelman merkleHelman = new MerkleHelman();
    private JTextArea jTextArea;

    public ClientHandler(ServerSocket serverSocket, Socket socket,
                         Scanner scaner, PrintWriter writer,
                         Time time, MerkleHelman merkleHelman,
                         JTextArea jTextArea  ) {
        this.serverSocket = serverSocket;
        this.socket = socket;
        this.scaner = scaner;
        this.writer = writer;
        this.time = time;
        this.merkleHelman = merkleHelman;
        this.jTextArea = jTextArea;
        // clientHandlers.add(this);
    }
    @Override
    public void run() {
            time = new Time();
            String massage = jTextArea.getText();
           // String encryptedMassage=merkleHelman.encryptMsg(time.getTime()+massage);
            writer.println(merkleHelman.encryptMsg(time.getTime()+massage));
          // printToAllClients(encryptedMassage);
            jTextArea.setText(" ");
    }
//sending to many clients
    /*private void printToAllClients(String massage) {
        for (ClientHandler ch:clientHandlers) {
            ch.writer.println(merkleHelman.encryptMsg(time.getTime()+massage));
        }
    }*/

}



