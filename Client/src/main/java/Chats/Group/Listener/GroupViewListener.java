package Chats.Group.Listener;

import Chats.Group.Controller.ClientGroupController;
import Chats.Group.Event.GroupViewEvent;
import Chats.Group.Model.Group;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import LocalDataBase.SyncLocalDataBase;
import MainFrame.View.MainPanel;
import User.Controller.ClientUserController;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class GroupViewListener {
    MainPanel mainPanel;
    public GroupViewListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(GroupViewEvent groupViewEvent) throws SQLException, IOException, ClassNotFoundException {

        try {
            SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
            syncLocalDataBase.syncGroups();
        } catch (CouldNotConnectToServerException e) {
            e.printStackTrace();
        }

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        Group selectedGroup = null;
        for (int i = 0; i < mainUser.getGroups().size(); i++) {
            if(mainUser.getGroups().get(i).getGroupName().equals(groupViewEvent.getGroupName())){
                ClientGroupController clientGroupController = new ClientGroupController(mainUser.getGroups().get(i));
                clientGroupController.readMessages();
                selectedGroup = mainUser.getGroups().get(i);
                break;
            }
        }

        mainPanel.addGroupPanel(selectedGroup);

    }
}
