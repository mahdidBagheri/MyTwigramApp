package Chats.Listeners;

import Chats.Controller.ServerNewChatController;
import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Twitt.Exceptions.TwittReadDataException;
import User.Exceptions.unsuccessfullReadDataFromDatabase;

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
    }
}
