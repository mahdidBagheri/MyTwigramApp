package Chats.Controller;

import Chats.Model.PV;
import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Twitt.Exceptions.TwittReadDataException;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ServerNewChatController {
    ServerConnection serverConnection;

    public ServerNewChatController(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws TwittReadDataException, unsuccessfullReadDataFromDatabase, SQLException, IOException {
        PV pv = new PV();
        ServerPVController serverPVController = new ServerPVController(serverConnection,pv);

        User user1 = new User();
        ServerUserController serverUser1Controller = new ServerUserController(user1);
        serverUser1Controller.readAll(clientRequest.getClientPayLoad().getStringStringHashMap().get("username1"));

        User user2 = new User();
        ServerUserController serverUser2Controller = new ServerUserController(user2);
        serverUser2Controller.readAll(clientRequest.getClientPayLoad().getStringStringHashMap().get("username2"));

        serverPVController.createNewPV(user1, user2);
    }
}
