package Chats.Group.Listeners;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ServerGroupListener {
    ServerConnection serverConnection;
    public ServerGroupListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws SQLException, unsuccessfullReadDataFromDatabase, IOException {
        if(clientRequest.getCommand().equals("createNewGroup")){
            ServerCreateNewGroupListener serverCreateNewGroupListener = new ServerCreateNewGroupListener(serverConnection);
            serverCreateNewGroupListener.listen(clientRequest);
        }
        else if(clientRequest.getCommand().equals("readGroups")){
            User mainUser = new User();
            mainUser.setUserName(clientRequest.getUsername());
            ServerUserController serverUserController = new ServerUserController(mainUser);
            serverUserController.readUserUUIDbyUsername(clientRequest.getUsername());
            serverUserController.readGroups();

            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setUser(mainUser);

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"readGroups",serverPayLoad);
            serverConnection.execute(serverRequest);
        }
    }
}
