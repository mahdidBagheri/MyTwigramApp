package Connection.Client;

import Connection.Server.ServerConnection;
import Connection.Server.ServerWaitForInput;
import ServerSignup.Listener.ServerSignupListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ClientThread extends Thread{
    private ServerConnection serverConnection;

    public ClientThread(Socket socket) {
        this.serverConnection = new ServerConnection(socket);
    }


    @Override
    public void run() {
        try {
            ServerWaitForInput serverWaitForInput = new ServerWaitForInput();
            serverWaitForInput.waitForInput(serverConnection.getSocket());
            ObjectInputStream objectInputStream = new ObjectInputStream(serverConnection.getSocket().getInputStream());
            ClientRequest clientRequest = (ClientRequest) objectInputStream.readObject();


            if (clientRequest.getSource().equals("signup")) {
                ServerSignupListener signupListener = new ServerSignupListener(serverConnection);
                signupListener.listen(clientRequest);
            }
        }catch (IOException | ClassNotFoundException | SQLException e){
            try {
                serverConnection.getSocket().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
