package Chats.Group.Listener;

import Chats.Common.Message.Events.SendMessageEvent;
import Chats.Group.Model.Group;
import Chats.Group.View.GroupPanel;
import Chats.PV.Exceptions.MessageSavedAndNotSent;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import LocalDataBase.ConnectionToLocalDataBase;
import User.Controller.ClientUserController;
import User.Model.User;
import Utils.DateTime;

import java.io.IOException;
import java.sql.SQLException;

public class SendMessageListener {
    GroupPanel groupPanel;
    Group group;
    public SendMessageListener(GroupPanel groupPanel) {
        this.groupPanel = groupPanel;
        this.group = groupPanel.getGroup();
    }

    public void listen(SendMessageEvent sendMessageEvent) throws MessageSavedAndNotSent, SQLException, IOException, ClassNotFoundException {
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        DateTime dateTime = new DateTime();
        String date = dateTime.Now();

        clientPayLoad.getStringStringHashMap().put("groupTableAddress",group.getGroupTableAddress());
        clientPayLoad.getStringStringHashMap().put("username",group.getMainUser().getUserName());
        clientPayLoad.getStringStringHashMap().put("text",sendMessageEvent.getMsg());
        clientPayLoad.getStringStringHashMap().put("imageAddress",sendMessageEvent.getImageAddress());
        clientPayLoad.getStringStringHashMap().put("date",date);

        ClientConnection clientConnection = null;
        try {
            clientConnection = new ClientConnection();
            clientPayLoad.getStringStringHashMap().put("sync","true");
            saveToLocalDataBase(clientPayLoad);

        } catch (CouldNotConnectToServerException e) {
            clientPayLoad.getStringStringHashMap().put("sync","false");
            System.out.println("message saved to send later");
            saveToLocalDataBase(clientPayLoad);
            e.printStackTrace();
            throw new MessageSavedAndNotSent("saved but not sent");
        }

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();

        ClientRequest clientRequest = new ClientRequest("group",clientPayLoad,mainUser.getSession(),"sendMessage",mainUser.getUserName(),mainUser.getPassWord());
        clientConnection.execute(clientRequest);
    }

    private void saveToLocalDataBase(ClientPayLoad clientPayLoad) throws SQLException, IOException, ClassNotFoundException {
        String text = clientPayLoad.getStringStringHashMap().get("text");
        String author = clientPayLoad.getStringStringHashMap().get("username");
        String groupTableAddress = clientPayLoad.getStringStringHashMap().get("groupTableAddress");
        String imageAddress = clientPayLoad.getStringStringHashMap().get("imageAddress");
        String sync = clientPayLoad.getStringStringHashMap().get("sync");
        String date = clientPayLoad.getStringStringHashMap().get("date");
        String sql;
        if(imageAddress != null){
            sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"ImageAddress\",\"Date\",\"sync\") values ('%s','%s','%s','%s','%s');",groupTableAddress,text,author,imageAddress,date,sync);
        }
        else {
            sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"Date\",\"sync\") values ('%s','%s','%s','%s');",groupTableAddress,text,author,date,sync);
        }
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();

        connectionToLocalDataBase.executeUpdate(sql);
        connectionToLocalDataBase.Disconect();
    }
}
