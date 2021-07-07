import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Connection.Client.ClientThread;
import Constants.*;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(Constants.portNumber);

        while (true) {

            try {
                Thread.sleep(100);


                System.out.println("waiting for client ...");
                Socket socket = serverSocket.accept();
                System.out.println("client connected");
                Thread clientThread = new ClientThread(socket);
                clientThread.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
