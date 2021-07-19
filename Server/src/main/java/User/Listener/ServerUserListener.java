package User.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerRequest;
import Twitt.Exceptions.TwittReadDataException;
import User.Controller.ServerUserController;
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
            selfFollowException, alreadyFollowedException, notFollowingUserException, TwittReadDataException {

        if(clientRequest.getCommand().equals("followOrUnfollow")){
            User mainUser = new User();
            ServerUserController mainUserController = new ServerUserController(mainUser);
            mainUserController.readAll(clientRequest.getUsername());
            User user = new User();
            ServerUserController userController = new ServerUserController(user);
            userController.readAll(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
            String res = mainUserController.ChangeFollowOrunFollow(user.getUserName());

            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),res,null);
            serverConnection.execute(serverRequest);
        }
    }
}
