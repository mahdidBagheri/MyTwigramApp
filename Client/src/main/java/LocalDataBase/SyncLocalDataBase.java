package LocalDataBase;

import Chats.Common.Message.Model.Message;
import Chats.Group.Model.Group;
import Chats.PV.Model.PV;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Server.ServerRequest;
import User.Controller.UserController;
import User.Model.User;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SyncLocalDataBase {
    ConnectionToLocalDataBase connectionToLocalDataBase;
    User mainUser;

    public SyncLocalDataBase() throws Throwable {
        this.connectionToLocalDataBase = new ConnectionToLocalDataBase();
        User mainUser = new User();
        UserController mainUserController = new UserController(mainUser);
        mainUserController.setAsMain();
        mainUserController.finalize();

        this.mainUser = mainUser;
    }

    public void syncAll() throws Throwable {
        syncFollowers();
        syncMutes();
        syncBlackList();

        syncFollowings();
        syncChats();
        syncTwitts();

    }


    public void syncFollowers() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        ClientConnection clientConnection = new ClientConnection();
        ClientRequest clientRequest = null;
        String deleteFollowingListQuery = String.format("delete from followers;");
        connectionToLocalDataBase.executeUpdate(deleteFollowingListQuery);

        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username", mainUser.getUserName());
        clientRequest = new ClientRequest("userInfo", clientPayLoad, mainUser.getSession(), "userInfo", mainUser.getUserName(), mainUser.getPassWord());

        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User mainUser = serverRequest.getPayLoad().getUser();

        String addfollowings;

        for (User user : mainUser.getFollowers()) {
            addfollowings = String.format("insert into followers ( \"UUID\" , \"Username\" , \"sync\") values ('%s','%s','%s');", user.getUserUUID(), user.getUserName(), "true");
            connectionToLocalDataBase.executeUpdate(addfollowings);
        }
    }

    public void syncFollowings() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        ClientConnection clientConnection = new ClientConnection();
        ClientRequest clientRequest = null;
        String deleteFollowingListQuery = String.format("delete from followings;");
        connectionToLocalDataBase.executeUpdate(deleteFollowingListQuery);

        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username", mainUser.getUserName());
        clientRequest = new ClientRequest("userInfo", clientPayLoad, mainUser.getSession(), "userInfo", mainUser.getUserName(), mainUser.getPassWord());

        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User mainUser = serverRequest.getPayLoad().getUser();

        String addfollowings;

        for (User user : mainUser.getFollowing()) {
            addfollowings = String.format("insert into followings ( \"UUID\" , \"Username\" , \"sync\") values ('%s','%s','%s');", user.getUserUUID(), user.getUserName(), "true");
            connectionToLocalDataBase.executeUpdate(addfollowings);
        }
    }

    public void syncMutes() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        ClientConnection clientConnection = new ClientConnection();

        ClientRequest clientRequest = null;
        String deleteFollowingListQuery = String.format("delete from mutes;");
        connectionToLocalDataBase.executeUpdate(deleteFollowingListQuery);


        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username", mainUser.getUserName());
        clientRequest = new ClientRequest("userInfo", clientPayLoad, mainUser.getSession(), "userInfo", mainUser.getUserName(), mainUser.getPassWord());


        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User mainUser = serverRequest.getPayLoad().getUser();

        String addfollowings;

        for (User user : mainUser.getFollowers()) {
            addfollowings = String.format("insert into mutes ( \"UUID\" , \"Username\" , \"sync\") values ('%s','%s','%s');", user.getUserUUID(), user.getUserName(), "true");
            connectionToLocalDataBase.executeUpdate(addfollowings);
        }
    }

    public void syncBlackList() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        ClientConnection clientConnection = new ClientConnection();

        ClientRequest clientRequest = null;
        String deleteFollowingListQuery = String.format("delete from blacklist;");
        connectionToLocalDataBase.executeUpdate(deleteFollowingListQuery);


        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username", mainUser.getUserName());
        clientRequest = new ClientRequest("userInfo", clientPayLoad, mainUser.getSession(), "userInfo", mainUser.getUserName(), mainUser.getPassWord());


        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User mainUser = serverRequest.getPayLoad().getUser();

        String addfollowings;

        for (User user : mainUser.getBlackList()) {
            addfollowings = String.format("insert into blacklist ( \"UUID\" , \"Username\" , \"sync\") values ('%s','%s','%s');", user.getUserUUID(), user.getUserName(), "true");
            connectionToLocalDataBase.executeUpdate(addfollowings);
        }
    }

    public void syncChats() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        ClientConnection clientConnection = new ClientConnection();


        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username", mainUser.getUserName());
        ClientRequest clientRequest = new ClientRequest("chats", clientPayLoad, mainUser.getSession(), "readChats", mainUser.getUserName(), mainUser.getPassWord());

        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User mainUser = serverRequest.getPayLoad().getUser();

        String addChat;

        String existingPVs = String.format("select * from \"ChatsTable\";");
        ResultSet rs = connectionToLocalDataBase.executeQuery(existingPVs);

        boolean isExist = false;
        for (PV pv : mainUser.getChats()) {
            isExist = false;
            if(rs != null){
                while (rs.next()){
                    if(pv.getPVTableName().equals(rs.getString(1))){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    DateTime dateTime = new DateTime();
                    addChat = String.format("insert into \"ChatsTable\" ( \"ChatAddress\" , \"Username\" , \"Date\",\"sync\") values ('%s','%s','%s','%s');", pv.getPVTableName(), pv.getContact().getUserName(),dateTime.Now(), "true");
                    createPVTable(pv.getPVTableName());
                    connectionToLocalDataBase.executeUpdate(addChat);
                }
            }
        }

    }

    private void createPVTable(String pvTableName) throws SQLException {
        String sql = String.format("create table \"%s\"(\"ID\" BIGSERIAL NOT NULL PRIMARY KEY,\"Message\" text,\"Author\" character varying (50), \"ImageAddress\" character varying (200) ,\"Date\" timestamp without time zone,\"sync\" character varying (6));",pvTableName);
        connectionToLocalDataBase.executeUpdate(sql);

    }

    public void syncPV(String PVTableName) throws Throwable {
        sendPVQueuedMessages(PVTableName);
        requestChatFromServer(PVTableName);
    }



    private void sendPVQueuedMessages(String PVTableName) throws Throwable {
        String sql = String.format("select * from \"%s\";",PVTableName);
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        clientUserController.finalize();

        if(rs != null){
            while (rs.next()){
                if(rs.getString(6).equals("false")){
                    ClientConnection clientConnection = new ClientConnection();
                    ClientPayLoad clientPayLoad = new ClientPayLoad();
                    clientPayLoad.getStringStringHashMap().put("PVAddress",PVTableName);
                    clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
                    clientPayLoad.getStringStringHashMap().put("text",rs.getString(2));
                    clientPayLoad.getStringStringHashMap().put("date",rs.getString(5));
                    if(rs.getString(4) != null){
                        clientPayLoad.getStringStringHashMap().put("picAddress",rs.getString(4));
                        clientPayLoad.setFile(new File(rs.getString(4)));
                    }
                    ClientRequest clientRequest = new ClientRequest("PV",clientPayLoad,mainUser.getSession(),"sendMessage",mainUser.getUserName(),mainUser.getPassWord());
                    clientConnection.execute(clientRequest);
                    String updateMessage = String.format("update \"%s\" set sync = true where \"Date\" = '%s' and \"Author\" = '%s';",PVTableName,rs.getString(5),rs.getString(3));
                    connectionToLocalDataBase.executeUpdate(updateMessage);
                }
            }
        }
    }

    private void requestChatFromServer(String PVTableName) throws Throwable {
        ClientConnection clientConnection = new ClientConnection();

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        clientUserController.finalize();

        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("PVTableName",PVTableName);

        ClientRequest clientRequest = new ClientRequest("pv",clientPayLoad,mainUser.getSession(),"requestPV",mainUser.getUserName(),mainUser.getPassWord());
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        PV pv = serverRequest.getPayLoad().getPv();

        String sql;
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        sql = String.format("delete from \"%s\";",pv.getPVTableName());
        connectionToLocalDataBase.executeUpdate(sql);

        sql = String.format("ALTER SEQUENCE \"%s_ID_seq\" RESTART WITH 1;",PVTableName);
        connectionToLocalDataBase.executeUpdate(sql);

        for(Message message:pv.getMessages()){
            if(message.getPic() != null){
                // TODO pic address
                sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"ImageAddress\",\"Date\",\"sync\") values ('%s','%s','%s','%s','true');",pv.getPVTableName(),message.getText(),message.getAuthor().getUserName(),"picaddress",message.getDate());
                connectionToLocalDataBase.executeUpdate(sql);
            }
            else {
                sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"Date\",\"sync\") values ('%s','%s','%s','true');",pv.getPVTableName(),message.getText(),message.getAuthor().getUserName(),message.getDate());
                connectionToLocalDataBase.executeUpdate(sql);
            }
        }

    }

    public void syncGroups() throws CouldNotConnectToServerException, IOException, ClassNotFoundException, SQLException {
        ClientConnection clientConnection = new ClientConnection();


        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username", mainUser.getUserName());
        ClientRequest clientRequest = new ClientRequest("group", clientPayLoad, mainUser.getSession(), "readGroups", mainUser.getUserName(), mainUser.getPassWord());

        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User mainUser = serverRequest.getPayLoad().getUser();

        String addChat;

        String existingPVs = String.format("select * from \"GroupsTable\";");
        ResultSet rs = connectionToLocalDataBase.executeQuery(existingPVs);

        boolean isExist = false;
        for (Group group : mainUser.getGroups()) {
            isExist = false;
            if(rs != null){
                while (rs.next()){
                    if(group.getGroupTableAddress().equals(rs.getString(1))){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    DateTime dateTime = new DateTime();
                    addChat = String.format("insert into \"GroupsTable\" ( \"ChatAddress\" , \"GroupName\" , \"Date\",\"sync\") values ('%s','%s','%s','%s');", group.getGroupTableAddress(), group.getGroupName(),dateTime.Now(), "true");
                    createGroupTable(group.getGroupTableAddress());
                    connectionToLocalDataBase.executeUpdate(addChat);
                }
            }
        }

    }

    private void createGroupTable(String groupTableAddress) throws SQLException {
        //groupMemmbersTables
        String sql = String.format("create table \"%s\"(\"ID\" BIGSERIAL NOT NULL PRIMARY KEY,\"Memmbers\" character varying (50),\"sync\" character varying (6));",groupTableAddress + "Memmbers");
        connectionToLocalDataBase.executeUpdate(sql);

        //groupMessagesTable
        String sql1 = String.format("create table \"%s\"(\"ID\" BIGSERIAL NOT NULL PRIMARY KEY,\"Message\" text,\"Author\" character varying (50), \"ImageAddress\" character varying (200) ,\"Date\" timestamp without time zone,\"sync\" character varying (6));",groupTableAddress);
        connectionToLocalDataBase.executeUpdate(sql1);
    }

    public void syncGroupMessagesAndMemmbers(String groupTableName) throws Throwable {
        sendGroupQueuedMessages(groupTableName);
        requestGroupFromServer(groupTableName);
    }

    private void sendGroupQueuedMessages(String groupTableName) throws Throwable {
        String sql = String.format("select * from \"%s\";",groupTableName);
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        clientUserController.finalize();

        if(rs != null){
            while (rs.next()){
                if(rs.getString(6).equals("false")){
                    ClientConnection clientConnection = new ClientConnection();
                    ClientPayLoad clientPayLoad = new ClientPayLoad();
                    clientPayLoad.getStringStringHashMap().put("PVAddress",groupTableName);
                    clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
                    clientPayLoad.getStringStringHashMap().put("text",rs.getString(2));
                    clientPayLoad.getStringStringHashMap().put("picAddress",rs.getString(4));
                    clientPayLoad.getStringStringHashMap().put("date",rs.getString(5));
                    clientPayLoad.setFile(new File(rs.getString(4)));
                    ClientRequest clientRequest = new ClientRequest("PV",clientPayLoad,mainUser.getSession(),"sendMessage",mainUser.getUserName(),mainUser.getPassWord());
                    clientConnection.execute(clientRequest);
                    String updateMessage = String.format("update \"%s\" set sync = true where \"Date\" = '%s' and \"Author\" = '%s';",groupTableName,rs.getString(5),rs.getString(3));
                    connectionToLocalDataBase.executeUpdate(updateMessage);
                }
            }
        }
    }

    private void requestGroupFromServer(String groupTableName) throws Throwable {
        ClientConnection clientConnection = new ClientConnection();

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        clientUserController.finalize();

        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("PVTableName",groupTableName);

        ClientRequest clientRequest = new ClientRequest("pv",clientPayLoad,mainUser.getSession(),"requestPV",mainUser.getUserName(),mainUser.getPassWord());
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        Group group = serverRequest.getPayLoad().getGroup();

        String sql;
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        sql = String.format("delete from \"%s\";",group.getGroupTableAddress());
        connectionToLocalDataBase.executeUpdate(sql);

        sql = String.format("ALTER SEQUENCE \"%s_ID_seq\" RESTART WITH 1;",groupTableName);
        connectionToLocalDataBase.executeUpdate(sql);

        for(Message message:group.getMessages()){
            if(message.getPic() != null){
                // TODO pic address
                sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"ImageAddress\",\"Date\",\"sync\") values ('%s','%s','%s','%s','true');",group.getGroupTableAddress(),message.getText(),message.getAuthor().getUserName(),"picaddress",message.getDate());
                connectionToLocalDataBase.executeUpdate(sql);
            }
            else {
                sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"Date\",\"sync\") values ('%s','%s','%s','true');",group.getGroupTableAddress(),message.getText(),message.getAuthor().getUserName(),message.getDate());
                connectionToLocalDataBase.executeUpdate(sql);
            }
        }

    }

    public void syncTwitts() throws Throwable {
        ClientConnection clientConnection = new ClientConnection();

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        clientUserController.finalize();

        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());

        ClientRequest clientRequest = new ClientRequest("userInfo",clientPayLoad,mainUser.getSession(),"readTwitts",mainUser.getUserName(),mainUser.getPassWord());
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User user = serverRequest.getPayLoad().getUser();

        String sql;
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        sql = String.format("delete from \"twitts\";");
        connectionToLocalDataBase.executeUpdate(sql);

        for(Twitt twitt:user.getTwitts()){
            NewTwittController newTwittController = new NewTwittController(twitt);
            newTwittController.saveToLocalDataBase();
        }

    }

    public void syncTimeLine() {
        //TODO
    }


    public void finalize() throws Throwable {
        connectionToLocalDataBase.Disconect();
        super.finalize();
    }

}
