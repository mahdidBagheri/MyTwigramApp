package User.Listener;

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

public class UserViewListener {
    ServerConnection serverConnection;
    public UserViewListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws unsuccessfullReadDataFromDatabase, SQLException, IOException, TwittReadDataException {
        if(clientRequest.getCommand().equals("userInfo")) {
            User reqUser = new User();
            ServerUserController userController = new ServerUserController(reqUser);
            userController.readAll(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));

            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setUser(reqUser);

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(), "userInfo", serverPayLoad);
            serverConnection.execute(serverRequest);
        }
        else if(clientRequest.getCommand().equals("readTwitts")){
            User reqUser = new User();
            ServerUserController userController = new ServerUserController(reqUser);
            userController.readUserUUIDbyUsername(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
            userController.readTwitts();

            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setUser(reqUser);

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(), "userInfo", serverPayLoad);
            serverConnection.execute(serverRequest);
        }
    }
}
