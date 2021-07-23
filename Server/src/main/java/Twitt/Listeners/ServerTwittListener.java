package Twitt.Listeners;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerRequest;
import Twitt.Controller.ServerLikeController;
import Twitt.Exceptions.TwittReadDataException;
import User.Exceptions.unsuccessfullReadDataFromDatabase;

import java.io.IOException;
import java.sql.SQLException;

public class ServerTwittListener {
    ServerConnection serverConnection;
    public ServerTwittListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws SQLException, unsuccessfullReadDataFromDatabase, TwittReadDataException, IOException {
        if(clientRequest.getCommand().equals("like")){
            ServerLikeController serverLikeController = new ServerLikeController(clientRequest);
            serverLikeController.change_Like_OR_noLike();

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"true",null);
            serverConnection.execute(serverRequest);
        }
    }
}
