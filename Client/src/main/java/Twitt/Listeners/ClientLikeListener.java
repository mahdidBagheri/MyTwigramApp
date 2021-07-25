package Twitt.Listeners;

import CommonClasses.Exceptions.ServerException;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import TimeLine.View.TimeLinePanel;
import Twitt.Model.Twitt;
import User.Controller.ClientUserController;
import User.Model.User;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class ClientLikeListener {
    User mainUser;

    public ClientLikeListener() {
    }

    public void listen(Twitt twitt) throws Throwable {
        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad clientPayLoad = new ClientPayLoad();

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        clientUserController.finalize();

        this.mainUser = mainUser;

        clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
        clientPayLoad.setTwitt(twitt);
        ClientRequest clientRequest = new ClientRequest("twitt",clientPayLoad,mainUser.getSession(),"like",mainUser.getUserName(),mainUser.getPassWord());
        boolean res = clientConnection.executeBoolean(clientRequest);
        if(res == true){

        }
        else {
            throw new ServerException("server Error");
        }

    }

}
