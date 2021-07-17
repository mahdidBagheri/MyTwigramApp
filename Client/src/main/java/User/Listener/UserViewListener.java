package User.Listener;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Server.ServerRequest;
import MainFrame.View.MainPanel;
import User.Events.UserViewEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class UserViewListener {
    MainPanel mainPanel;

    public UserViewListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(UserViewEvent userViewEvent) throws IOException, ClassNotFoundException, SQLException {
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("userView",userViewEvent.getUser().getUserName());
        ClientRequest clientRequest = new ClientRequest("userView",clientPayLoad,userViewEvent.getMainUser().getSession(),"userView",userViewEvent.getUser().getUserName(),userViewEvent.getUser().getPassWord());
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();

        UserViewEvent userViewEvent1 = new UserViewEvent(serverRequest.getPayLoad().getUser(), mainPanel);
        mainPanel.addUserPanel(userViewEvent1);

    }
}
