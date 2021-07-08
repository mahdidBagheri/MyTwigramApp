package Connection;

import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Server.ServerRequest;
import Config.NetworkConfig.NetworkConfig;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import Constants.Constants;

public class ClientConnection {
    Socket socket;

    public ClientConnection() {
        NetworkConfig networkConfig = new NetworkConfig();

        try {
            this.socket = new Socket(Constants.IP, Constants.portNumber);
        } catch (IOException e) {

            try {
                this.socket = new Socket("localhost", networkConfig.getPort());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        System.out.println(socket);
    }

    public boolean executeBoolean(ClientRequest clientRequest) throws IOException, ClassNotFoundException, CouldNotConnectToServerException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(clientRequest);
        os.flush();
        oos.flush();

        ClientWaitForInput.waitForInput(socket);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();

        System.out.println(serverRequest.getCommand());
        if (serverRequest.getCommand().equals("true")) {
            return true;
        } else {
            return false;
        }

    }

    public void execute(ClientRequest clientRequest) throws IOException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(clientRequest);
        os.flush();
        oos.flush();
    }

    public Socket getSocket() {
        return socket;
    }
}
