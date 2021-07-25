package Twitt.Events;

import MainFrame.View.MainPanel;
import Twitt.Model.Twitt;
import User.Controller.ClientUserController;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class TwittViewEvent {
    Twitt twitt;
    MainPanel mainPanel;
    User mainUser;

    public TwittViewEvent(Twitt twitt, MainPanel mainPanel) throws Throwable {
        this.twitt = twitt;
        this.mainPanel = mainPanel;

        User mainUser = new User();
        ClientUserController mainUserController = new ClientUserController(mainUser);
        mainUserController.setAsMain();
        mainUserController.finalize();

        this.mainUser = mainUser;

    }

    public Twitt getTwitt() {
        return twitt;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setTwitt(Twitt twitt) {
        this.twitt = twitt;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }
}
