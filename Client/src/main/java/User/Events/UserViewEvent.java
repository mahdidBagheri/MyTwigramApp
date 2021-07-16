package User.Events;

import User.Controller.UserController;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class UserViewEvent {
    User user;
    User mainUser;

    public UserViewEvent(User user) throws SQLException, IOException, ClassNotFoundException {
        this.user = user;
        User mainUser = new User();
        UserController userController = new UserController(mainUser);
        userController.setAsMain();
        this.mainUser = mainUser;
    }

    public User getUser() {
        return user;
    }

    public User getMainUser() {
        return mainUser;
    }
}
