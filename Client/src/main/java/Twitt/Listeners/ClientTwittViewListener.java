package Twitt.Listeners;

import Connection.Exceptions.CouldNotConnectToServerException;
import MainFrame.View.MainPanel;
import TimeLine.View.TimeLinePanel;
import Twitt.Controller.TwittsController;
import Twitt.Events.TwittViewEvent;
import Twitt.Model.Twitt;
import User.Controller.ClientUserController;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ClientTwittViewListener {
    MainPanel mainPanel;
    Twitt twitt;
    public ClientTwittViewListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(TwittViewEvent twittViewEvent) throws ClassNotFoundException, SQLException, CouldNotConnectToServerException, IOException {
        this.twitt = twittViewEvent.getTwitt();
        TwittsController twittsController = new TwittsController(twitt);
        twittsController.readAllByUUID();


        TwittViewEvent twittViewEvent1 = new TwittViewEvent(twittsController.getTwitt(), mainPanel);
        mainPanel.addTwittPanel(twittViewEvent1);

    }
}
