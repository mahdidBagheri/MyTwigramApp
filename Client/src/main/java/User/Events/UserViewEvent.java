package User.Events;

import MainFrame.View.MainPanel;
import User.Controller.UserController;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class UserViewEvent {
    User user;
    User mainUser;
    MainPanel mainPanel;

    public UserViewEvent(User user, MainPanel mainPanel) throws SQLException, IOException, ClassNotFoundException {
        this.user = user;
        User mainUser = new User();
        UserController userController = new UserController(mainUser);
        userController.setAsMain();
        this.mainUser = mainUser;
        this.mainPanel = mainPanel;
    }

    public User getUser() {
        return user;
    }

    public User getMainUser() {
        return mainUser;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
