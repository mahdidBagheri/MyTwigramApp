package Chats.Group.Listeners;

import Chats.Group.Controller.GroupController;
import Chats.Group.Model.Group;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
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
        else if(clientRequest.getCommand().equals("requestGroup")){
            Group group = new Group();
            group.setGroupTableAddress(clientRequest.getClientPayLoad().getStringStringHashMap().get("groupTableAddress"));
            GroupController groupController = new GroupController(group);
            groupController.readMessages();
            groupController.readMemmbers();

            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setGroup(group);

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"readGroups",serverPayLoad);
            serverConnection.execute(serverRequest);

        }
        else if(clientRequest.getCommand().equals("sendMessage")){
            GroupController groupController = new GroupController(serverConnection);
            groupController.sendMessage(clientRequest);

        }
    }
}
