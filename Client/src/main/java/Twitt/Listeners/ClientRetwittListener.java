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

public class ClientRetwittListener {
    Twitt twitt;
    User mainUser;


    public void listen(Twitt twitt) throws CouldNotConnectToServerException, SQLException, IOException, ClassNotFoundException, ServerException {
        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        this.twitt = twitt;


        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        this.mainUser = mainUser;

        clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
        clientPayLoad.setTwitt(twitt);
        ClientRequest clientRequest = new ClientRequest("twitt",clientPayLoad,mainUser.getSession(),"retwitt",mainUser.getUserName(),mainUser.getPassWord());
        boolean res = clientConnection.executeBoolean(clientRequest);
        if(res == true){

        }
        else {
            throw new ServerException("already retwitted");
        }

    }


}
