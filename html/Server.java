package HTML.Main.src.html;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("---Main service is activated--- PORT: " + PORT);

        List<String> quotes = new ArrayList<>();

        while(true){
            Socket s = serverSocket.accept();
            System.out.println("Request from client");
            ServerThread serverThread = new ServerThread(s, quotes);
            new Thread(serverThread).start();
        }
    }
}