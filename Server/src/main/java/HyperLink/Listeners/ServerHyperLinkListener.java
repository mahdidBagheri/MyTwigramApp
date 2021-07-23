package HyperLink.Listeners;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import Twitt.Exceptions.TwittReadDataException;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ServerHyperLinkListener {
    ServerConnection serverConnection;

    public ServerHyperLinkListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws TwittReadDataException, unsuccessfullReadDataFromDatabase, SQLException, IOException {
        String link = clientRequest.getClientPayLoad().getStringStringHashMap().get("link");
        if(link.startsWith("@Twitt")){

        }
        else {
            User reqUser = new User();
            reqUser.setUserName(link.substring(1));
            ServerUserController serverUserController = new ServerUserController(reqUser);
            if(serverUserController.isExist()){
                serverUserController.readAll(reqUser.getUserName());
                ServerPayLoad serverPayLoad = new ServerPayLoad();
                serverPayLoad.setUser(reqUser);
                ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"validLink",serverPayLoad);
                serverConnection.execute(serverRequest);
            }
            else {
                ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"invalidLink",null);
                serverConnection.execute(serverRequest);
            }
        }
    }
}
