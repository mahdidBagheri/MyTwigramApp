package User.Listener;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Server.ServerRequest;
import MainFrame.View.MainPanel;
import User.Controller.ClientUserController;
import User.Events.UserViewEvent;
import User.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class ClientUserViewListener {
    MainPanel mainPanel;

    public ClientUserViewListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(UserViewEvent userViewEvent) throws IOException, ClassNotFoundException, SQLException {
        User user = userViewEvent.getUser();
        ClientUserController clientUserController = new ClientUserController(user);
        clientUserController.readAllByUsername();


        UserViewEvent userViewEvent1 = new UserViewEvent(user, mainPanel);
        mainPanel.addUserPanel(userViewEvent1);

    }
}
