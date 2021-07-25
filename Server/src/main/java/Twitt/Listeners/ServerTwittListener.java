package Twitt.Listeners;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import Twitt.Controller.ServerLikeController;
import Twitt.Controller.ServerRetwittController;
import Twitt.Controller.ServerTwittsController;
import Twitt.Exceptions.AlreadyRetwittedException;
import Twitt.Exceptions.TwittReadDataException;
import Twitt.Model.Twitt;
import User.Exceptions.unsuccessfullReadDataFromDatabase;

import java.io.IOException;
import java.sql.SQLException;

public class ServerTwittListener {
    ServerConnection serverConnection;
    public ServerTwittListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws Throwable {
        if(clientRequest.getCommand().equals("like")){
            ServerLikeController serverLikeController = new ServerLikeController(clientRequest);
            serverLikeController.change_Like_OR_noLike();

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"true",null);
            serverConnection.execute(serverRequest);
            serverLikeController.finalize();
        }
        else if (clientRequest.getCommand().equals("retwitt")) {
            ServerRetwittController serverRetwittController = new ServerRetwittController(clientRequest);
            ServerRequest serverRequest;
            try {
                serverRetwittController.retwitt();
                serverRequest = new ServerRequest(clientRequest.getUsername(),"true",null);
            } catch (AlreadyRetwittedException e) {
                serverRequest = new ServerRequest(clientRequest.getUsername(),"false",null);
            }

            serverConnection.execute(serverRequest);
            serverRetwittController.finalize();
        }
        else if(clientRequest.getCommand().equals("twittInfo")){
             Twitt twitt = new Twitt();
             String twittUUID = clientRequest.getClientPayLoad().getStringStringHashMap().get("twittUUID");
             twitt.setTwittUUID(twittUUID);

            ServerTwittsController serverTwittsController = new ServerTwittsController(twitt);
            serverTwittsController.readDataFromDataBaseByUUID();
            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setTwitt(twitt);

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"twittInfo",serverPayLoad);
            serverConnection.execute(serverRequest);
        }
    }
}
