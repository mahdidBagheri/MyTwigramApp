package TimeLine.Listeners;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Server.ServerRequest;
import MainFrame.View.MainPanel;
import TimeLine.View.TimeLinePanel;
import Twitt.Model.Twitt;
import User.Controller.ClientUserController;
import User.Events.UserViewEvent;
import User.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class ClientGoAuthorListener {
    TimeLinePanel timeLinePanel;
    MainPanel mainPanel;
    public ClientGoAuthorListener(TimeLinePanel timeLinePanel, MainPanel mainPanel) {
        this.timeLinePanel = timeLinePanel;
        this.mainPanel = mainPanel;
    }

    public void listen() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        int idx = timeLinePanel.getTwittNum();
        Twitt twitt = timeLinePanel.getTimeLine().getTwitts().get(idx);

        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username",twitt.getAuthor().getUserName());

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();

        ClientRequest clientRequest = new ClientRequest("userInfo",clientPayLoad,mainUser.getSession(),"userInfo",mainUser.getUserName(),mainUser.getPassWord());
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();

        User user = serverRequest.getPayLoad().getUser();
        UserViewEvent userViewEvent = new UserViewEvent(user,mainPanel);
        mainPanel.addUserPanel(userViewEvent);

    }
}
