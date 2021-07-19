package ClientLogin.Listeners;

import ClientLogin.Controller.LoginController;
import ClientLogin.Events.LoginEvent;
import ClientLogin.Exceptions.EmptyFieldException;
import Config.DataBaseConfig.DataBaseConfig;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Connection.Exceptions.UserPassNotMatchException;
import MainFrame.View.MainPanel;
import User.Controller.UserController;
import User.Model.User;

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
        // read data

        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        dataBaseConfig.changeAcount(loginEvent.getUsername());

        User mainUser = new User();
        UserController userController = new UserController(mainUser);
        userController.setAsMain();

        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
        ClientRequest clientRequest = new ClientRequest("profile",clientPayLoad,mainUser.getSession(),"profileData",mainUser.getUserName(),mainUser.getPassWord());
        clientConnection.execute(clientRequest);

        SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
        syncLocalDataBase.syncAll();
        mainPanel.addProfilePanel(mainUser);
    }
}
