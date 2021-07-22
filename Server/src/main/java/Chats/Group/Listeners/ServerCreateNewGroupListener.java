package Chats.Group.Listeners;

import Chats.Group.Controller.GroupController;
import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import User.Exceptions.unsuccessfullReadDataFromDatabase;

import java.sql.SQLException;

public class ServerCreateNewGroupListener {
    ServerConnection serverConnection;
    public ServerCreateNewGroupListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws SQLException, unsuccessfullReadDataFromDatabase {
        GroupController groupController = new GroupController(serverConnection);
        groupController.createNewGroup(clientRequest.getClientPayLoad().getGroup().getMemmbers(),clientRequest.getClientPayLoad().getGroup().getGroupName());
    }
}
