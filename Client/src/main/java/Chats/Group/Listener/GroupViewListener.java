package Chats.Group.Listener;

import Chats.Group.Controller.ClientGroupController;
import Chats.Group.Event.GroupViewEvent;
import Chats.Group.Model.Group;
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

    public void listen(GroupViewEvent groupViewEvent) throws Throwable {

        try {
            SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
            syncLocalDataBase.syncGroups();
            syncLocalDataBase.finalize();

        } catch (CouldNotConnectToServerException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        clientUserController.finalize();
        Group selectedGroup = null;
        for (int i = 0; i < mainUser.getGroups().size(); i++) {
            if(mainUser.getGroups().get(i).getGroupName().equals(groupViewEvent.getGroupName())){
                ClientGroupController clientGroupController = new ClientGroupController(mainUser.getGroups().get(i));
                clientGroupController.readMessages();
                clientGroupController.finalize();

                selectedGroup = mainUser.getGroups().get(i);
                break;
            }
        }

        mainPanel.addGroupPanel(selectedGroup);

    }
}
