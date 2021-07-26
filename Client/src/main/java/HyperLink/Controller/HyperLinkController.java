package HyperLink.Controller;

import Chats.PV.Controller.PVController;
import Chats.PV.Events.ClientPVViewEvent;
import Chats.PV.Listener.ClientPVViewListener;
import Chats.PV.Model.PV;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Server.ServerRequest;
import HyperLink.Model.Hyperlink;
import Twitt.Events.TwittViewEvent;
import Twitt.Model.Twitt;
import User.Controller.ClientUserController;
import User.Events.UserViewEvent;
import User.Listener.ClientUserViewListener;
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
            else if(hyperlink.getType().equals("Twitt")){
                Twitt twitt = serverRequest.getPayLoad().getTwitt();
                TwittViewEvent twittViewEvent = new TwittViewEvent(twitt,hyperlink.getMainPanel());
                hyperlink.getMainPanel().addTwittPanel(twittViewEvent);
            }
            else if(hyperlink.getType().equals("PV")){

                PV pv = serverRequest.getPayLoad().getPv();
                ClientPVViewEvent clientPVViewEvent = new ClientPVViewEvent(pv.getContact().getUserName());
                ClientPVViewListener clientPVViewListener = new ClientPVViewListener(hyperlink.getMainPanel());
                clientPVViewListener.listen(clientPVViewEvent);

            }
        }
        else {
            JOptionPane.showMessageDialog(hyperlink.getMainPanel(),"invalidLink or not allowed");
        }


        //TODO add other hyperLinks

    }
}
