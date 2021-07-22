package Chats.Group.Listener;

import Chats.Chats.View.ChatsPanel;
import Chats.Group.Model.Group;
import ClientLogin.Exceptions.EmptyFieldException;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import LocalDataBase.SyncLocalDataBase;
import User.Controller.ClientUserController;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class CreateNewGroupListener {
    ChatsPanel chatsPanel;

    public CreateNewGroupListener(ChatsPanel chatsPanel) {
        this.chatsPanel = chatsPanel;
    }

    public ChatsPanel getChatsPanel() {
        return chatsPanel;
    }

    public void listen() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException, EmptyFieldException {
        if(chatsPanel.getGroupNameField().getText().isEmpty()){
            throw new EmptyFieldException("group name must not be empty");
        }

        ClientPayLoad clientPayLoad = new ClientPayLoad();

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();

        Group group = new Group();
        group.getMemmbers().add(mainUser);
        group.setMainUser(mainUser);


        group.setGroupName(chatsPanel.getGroupNameField().getText());

        for (int i = 0; i < chatsPanel.getNewGroupMembersCombo().getItemCount(); i++) {
            User memmber = new User();
            String memmberUsername = chatsPanel.getNewGroupMembersCombo().getItemAt(i);
            memmber.setUserName(memmberUsername);
            group.getMemmbers().add(memmber);
        }

        clientPayLoad.setGroup(group);
        ClientConnection clientConnection = new ClientConnection();
        ClientRequest clientRequest = new ClientRequest("group",clientPayLoad,mainUser.getSession(),"createNewGroup",mainUser.getUserName(),mainUser.getPassWord());

        clientConnection.execute(clientRequest);

        SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
        syncLocalDataBase.syncGroups();

        ClientUserController clientUserController1 = new ClientUserController(chatsPanel.getMainUser());
        clientUserController1.setAsMain();
    }
}
