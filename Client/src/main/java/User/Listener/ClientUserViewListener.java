package User.Listener;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Server.ServerRequest;
import MainFrame.View.MainPanel;
import User.Controller.ClientUserController;
import User.Events.UserViewEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class ClientUserViewListener {
    MainPanel mainPanel;

    public ClientUserViewListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(UserViewEvent userViewEvent) throws IOException, ClassNotFoundException, SQLException {
        ClientUserController clientUserController = new ClientUserController(userViewEvent.getUser());
        clientUserController.readAllByUsername();


        UserViewEvent userViewEvent1 = new UserViewEvent(userViewEvent.getUser(), mainPanel);
        mainPanel.addUserPanel(userViewEvent1);

    }
}
