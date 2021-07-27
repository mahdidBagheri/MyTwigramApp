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
            Group group = new Group();
            group.setGroupName(groupViewEvent.getGroupName());
            ClientGroupController clientGroupController = new ClientGroupController(group);

            SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
            syncLocalDataBase.syncGroups();

            clientGroupController.readGroupAddressByGroupName();
            syncLocalDataBase.syncGroupMessagesAndMemmbers(group.getGroupTableAddress());
            clientGroupController.readMessages();

            syncLocalDataBase.finalize();

            User mainUser = new User();
            ClientUserController clientUserController = new ClientUserController(mainUser);
            clientUserController.setAsMain();
            clientUserController.finalize();

            group.setMainUser(mainUser);

            mainPanel.addGroupPanel(group);


        } catch (CouldNotConnectToServerException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }
}
