package TimeLine.Listeners;

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
    TimeLinePanel timeLinePanel;
    Twitt twitt;
    User mainUser;

    public ClientRetwittListener(TimeLinePanel timeLinePanel) {
        this.timeLinePanel = timeLinePanel;
    }

    public void listen() throws CouldNotConnectToServerException, SQLException, IOException, ClassNotFoundException, ServerException {
        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        this.twitt = timeLinePanel.getTimeLine().getTwitts().get(timeLinePanel.getTwittNum());


        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        this.mainUser = mainUser;

        clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
        clientPayLoad.setTwitt(twitt);
        ClientRequest clientRequest = new ClientRequest("twitt",clientPayLoad,mainUser.getSession(),"retwitt",mainUser.getUserName(),mainUser.getPassWord());
        boolean res = clientConnection.executeBoolean(clientRequest);
        if(res == true){
            updateGraphics();
        }
        else {
            throw new ServerException("already retwitted");
        }

    }

    private void updateGraphics() {
            timeLinePanel.getLikeBtn().setText("removeLike");
            mainUser.getLikes().add(twitt.getTwittUUID());
            twitt.getLikes().add(mainUser.getUserName());
            timeLinePanel.addTwittLikes();
            timeLinePanel.addTwittRetwitts();
            timeLinePanel.getLikesLable().setText("Likes: " + twitt.getLikes().size());
            timeLinePanel.getReTwittsLable().setText("retwitts: " + twitt.getReTwitts().size());
            JOptionPane.showMessageDialog(timeLinePanel,"liked successfully");
    }
}
