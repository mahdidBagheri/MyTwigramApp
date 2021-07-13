package ClientLogin.Listeners;

import ClientLogin.Controller.LoginController;
import ClientLogin.Events.LoginEvent;
import ClientLogin.Exceptions.EmptyFieldException;
import Connection.Exceptions.UserPassNotMatchException;
import MainFrame.View.MainPanel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginListener {
    MainPanel mainPanel;

    public LoginListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(LoginEvent loginEvent) throws IOException, EmptyFieldException, ClassNotFoundException, SQLException, UserPassNotMatchException {
        LoginController loginController = new LoginController();

        loginController.validateLogin(loginEvent);
        loginController.loginUser(loginEvent);
    }
}
