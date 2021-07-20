package Chats.Chats;

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

public class ServerChatsListener {
    ServerConnection serverConnection;
    public ServerChatsListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws TwittReadDataException, SQLException, unsuccessfullReadDataFromDatabase, IOException {
        if(clientRequest.getCommand().equals("createNewChat")){
            ServerNewChatController serverNewChatController = new ServerNewChatController(serverConnection);
            serverNewChatController.listen(clientRequest);
        }
        if(clientRequest.getCommand().equals("readChats")){
            User mainUser = new User();
            ServerUserController mainUserController = new ServerUserController(mainUser);
            mainUserController.readUserUUIDbyUsername(clientRequest.getUsername());

            mainUserController.readChats();
            mainUserController.readGroups();

            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setUser(mainUser);

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"readChats",serverPayLoad);
            serverConnection.execute(serverRequest);
        }
    }
}
