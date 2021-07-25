package HyperLink.Controller;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Server.ServerRequest;
import HyperLink.Model.Hyperlink;
import User.Controller.ClientUserController;
import User.Events.UserViewEvent;
import User.Model.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class HyperLinkController {
    Hyperlink hyperlink;

    public HyperLinkController(Hyperlink hyperlink) {
        this.hyperlink = hyperlink;
    }

    public void goToLink() throws Throwable {
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("link",hyperlink.getLink());

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        clientUserController.finalize();

        ClientRequest clientRequest = new ClientRequest("hyperLink",clientPayLoad,mainUser.getSession(),"hyperLinkData",mainUser.getUserName(),mainUser.getPassWord());
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();

        if(serverRequest.getCommand().equals("validLink")){
            if(hyperlink.getType().equals("User")){
                User user = serverRequest.getPayLoad().getUser();
                UserViewEvent userViewEvent = new UserViewEvent(user,hyperlink.getMainPanel());
                hyperlink.getMainPanel().addUserPanel(userViewEvent);
            }
        }
        else {
            JOptionPane.showMessageDialog(hyperlink.getMainPanel(),"invalidLink");
        }


        //TODO add other hyperLinks

    }
}
