package User.Listener;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Server.ServerRequest;
import MainFrame.View.MainPanel;
import User.Controller.UserController;
import User.Events.UserEvent;
import User.Exceptions.FollowException;
import User.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class ClientUserListener {
    MainPanel mainPanel;

    public ClientUserListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(UserEvent userEvent) throws Throwable {
        if(userEvent.getCommand().equals("followOrUnfollow")){
            User mainUser = new User();
            UserController mainUserController = new UserController(mainUser);
            mainUserController.setAsMain();

            ClientPayLoad clientPayLoad = new ClientPayLoad();
            clientPayLoad.getStringStringHashMap().put("username",userEvent.getUser().getUserName());

            ClientRequest clientRequest = new ClientRequest("onUserAction",clientPayLoad,mainUser.getSession(),"followOrUnfollow",mainUser.getUserName(),mainUser.getPassWord());
            ClientConnection clientConnection = new ClientConnection();
            clientConnection.execute(clientRequest);


            ClientWaitForInput.waitForInput(clientConnection.getSocket());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
            ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();

            if(serverRequest.getCommand().equals("followed")){
                //pass
            }
            else {
                throw new FollowException(serverRequest.getCommand());
            }



        }
    }
}
