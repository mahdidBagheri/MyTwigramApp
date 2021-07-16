package User.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import User.Controller.UserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class UserViewListener {
    ServerConnection serverConnection;
    public UserViewListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws unsuccessfullReadDataFromDatabase, SQLException, IOException {

        User reqUser = new User();
        UserController userController = new UserController(reqUser);
        userController.readAll(clientRequest.getClientPayLoad().getStringStringHashMap().get("userView"));

        ServerPayLoad serverPayLoad = new ServerPayLoad();
        serverPayLoad.setUser(reqUser);

        ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"userView",serverPayLoad);
        serverConnection.execute(serverRequest);
    }
}
