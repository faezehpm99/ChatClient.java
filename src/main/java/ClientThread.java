import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private Scanner scanner;
    private Socket socket;
    private PrintWriter writer;
    private JTextArea jTextArea;


    public ClientThread(){
        try {
            this.socket= new Socket("localhost", 5678);
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            jTextArea = new JTextArea();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    @Override
    public void run() {
        while (true) {
            String massage = scanner.nextLine();
            jTextArea.append(massage +'\n');
        }
    }
}
