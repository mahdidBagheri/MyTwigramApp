package ServerNewTwitt.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerRequest;
import ServerNewTwitt.Controller.ServerNewTwittController;
import Twitt.Controller.ServerLikeController;
import Twitt.Controller.ServerTwittsController;
import Twitt.Exceptions.TwittReadDataException;
import Twitt.Model.Twitt;
import User.Exceptions.unsuccessfullReadDataFromDatabase;

import java.io.IOException;
import java.sql.SQLException;

public class ServerNewTwittListener {
    ServerConnection serverConnection;

    public ServerNewTwittListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws SQLException, IOException, unsuccessfullReadDataFromDatabase, TwittReadDataException {
        if(clientRequest.getCommand().equals("newTwitt")){
            ServerNewTwittController serverNewTwittController = new ServerNewTwittController(clientRequest);
            serverNewTwittController.setTwitt(clientRequest.getClientPayLoad().getTwitt());
            serverNewTwittController.newTwitt();
        }


    }
}
