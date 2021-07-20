package Chats.PV.Listener;

import Chats.PV.Events.ClientPVViewEvent;
import Chats.PV.Model.PV;
import MainFrame.View.MainPanel;
import User.Controller.ClientUserController;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ClientPVViewListener {
    MainPanel mainPanel;

    public ClientPVViewListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(ClientPVViewEvent clientPVViewEvent) throws SQLException, IOException, ClassNotFoundException {
        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();

        for (PV pv :mainUser.getChats()){
            if(pv.getContact().getUserName().equals(clientPVViewEvent.getUsername())){
                mainPanel.addPVPanel(pv);
                break;
            }
        }

    }
}
