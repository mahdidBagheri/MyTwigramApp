package ServerNewTwitt.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import ServerNewTwitt.Controller.ServerNewTwittController;
import Twitt.Model.Twitt;

import java.io.IOException;
import java.sql.SQLException;

public class ServerNewTwittListener {
    ServerConnection serverConnection;

    public ServerNewTwittListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws SQLException, IOException {
        if(clientRequest.getCommand().equals("newTwitt")){
            ServerNewTwittController serverNewTwittController = new ServerNewTwittController(clientRequest);
            serverNewTwittController.setTwitt(clientRequest.getClientPayLoad().getTwitt());
            serverNewTwittController.newTwitt();
        }

    }
}
