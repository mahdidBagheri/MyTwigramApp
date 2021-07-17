package User.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerRequest;
import User.Controller.UserController;
import User.Exceptions.*;
import User.Model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ServerUserListener {
    ServerConnection serverConnection;
    public ServerUserListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws unsuccessfullReadDataFromDatabase,
            SQLException, IOException, sendFollowRequestException,
            selfFollowException, alreadyFollowedException, notFollowingUserException {

        if(clientRequest.getCommand().equals("followOrUnfollow")){
            User mainUser = new User();
            UserController mainUserController = new UserController(mainUser);
            mainUserController.readAll(clientRequest.getUsername());
            User user = new User();
            UserController userController = new UserController(user);
            userController.readAll(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
            String res = mainUserController.ChangeFollowOrunFollow(user.getUserUUID());

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),res,null);
            serverConnection.execute(serverRequest);
        }
    }
}
