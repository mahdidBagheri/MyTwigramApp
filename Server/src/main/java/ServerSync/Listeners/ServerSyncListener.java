package ServerSync.Listeners;

import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import Twitt.Exceptions.TwittReadDataException;
import User.Controller.ServerUserController;
import User.Exceptions.*;
import User.Model.User;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class ServerSyncListener {
    ServerConnection serverConnection;
    ConnectionToDataBase connectionToDataBase;

    public ServerSyncListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
        this.connectionToDataBase = new ConnectionToDataBase();
    }

    public void listen(ClientRequest clientRequest) throws TwittReadDataException,
            unsuccessfullReadDataFromDatabase, SQLException, FileNotFoundException,
            sendFollowRequestException, selfFollowException, alreadyFollowedException,
            notFollowingUserException {

        if(clientRequest.getCommand().equals("syncFollowings")){
            User mainUser = new User();
            ServerUserController serverMainUserController = new ServerUserController(mainUser);
            serverMainUserController.readAll(clientRequest.getUsername());

            for (String username : clientRequest.getClientPayLoad().getStringStringHashMap().keySet()) {
                if(!mainUser.isFollowing(username)){
                    serverMainUserController.ChangeFollowOrunFollow(username);
                }
            }
        }
        else if(clientRequest.getCommand().equals("syncUser")){
            User user = clientRequest.getClientPayLoad().getUser();

            ServerUserController serverUserController = new ServerUserController(user);
            serverUserController.saveUserInfo();

        }


    }

    public void finalize() throws Throwable {
        connectionToDataBase.Disconect();
        super.finalize();
    }
}
