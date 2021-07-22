package LocalDataBase;

import Chats.Common.Message.Model.Message;
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

    public SyncLocalDataBase() throws SQLException, IOException, ClassNotFoundException {
        this.connectionToLocalDataBase = new ConnectionToLocalDataBase();
        User mainUser = new User();
        UserController mainUserController = new UserController(mainUser);
        mainUserController.setAsMain();

        this.mainUser = mainUser;
    }

    public void syncAll() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        syncFollowers();
        //syncMutes();
        //syncBlackList();

        //syncFollowings();
        syncChats();

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
        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();

        String followingsQuery = String.format("select * from followings;");
        ResultSet rs = connectionToLocalDataBase.executeQuery(followingsQuery);

        ClientPayLoad clientPayLoad = new ClientPayLoad();
        if(rs != null){
            while (rs.next()){
                clientPayLoad.getStringStringHashMap().put(rs.getString(2),"username");
            }
        }

        ClientConnection clientConnection = new ClientConnection();
        ClientRequest clientRequest = new ClientRequest("sync",clientPayLoad,mainUser.getSession(),"syncFollowings",mainUser.getUserName(),mainUser.getPassWord());
        clientConnection.execute(clientRequest);

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

        for (User user : mainUser.getFollowers()) {
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

        //TODO sync groups

    }

    private void createPVTable(String pvTableName) throws SQLException {
        String sql = String.format("create table \"%s\"(\"ID\" BIGSERIAL NOT NULL PRIMARY KEY,\"Message\" text,\"Author\" character varying (50), \"ImageAddress\" character varying (200) ,\"Date\" timestamp without time zone,\"sync\" character varying (6));",pvTableName);
        connectionToLocalDataBase.executeUpdate(sql);

    }

    public void syncPV(String PVTableName) throws SQLException, CouldNotConnectToServerException, IOException, ClassNotFoundException {
        sendQueuedMessages(PVTableName);
        requestChatFromServer(PVTableName);
    }



    private void sendQueuedMessages(String PVTableName) throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        String sql = String.format("select * from \"%s\";",PVTableName);
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();

        if(rs != null){
            while (rs.next()){
                if(rs.getString(6).equals("false")){
                    ClientConnection clientConnection = new ClientConnection();
                    ClientPayLoad clientPayLoad = new ClientPayLoad();
                    clientPayLoad.getStringStringHashMap().put("PVAddress",PVTableName);
                    clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
                    clientPayLoad.getStringStringHashMap().put("text",rs.getString(2));
                    clientPayLoad.getStringStringHashMap().put("picAddress",rs.getString(4));
                    clientPayLoad.getStringStringHashMap().put("date",rs.getString(5));
                    clientPayLoad.setFile(new File(rs.getString(4)));
                    ClientRequest clientRequest = new ClientRequest("PV",clientPayLoad,mainUser.getSession(),"sendMessage",mainUser.getUserName(),mainUser.getPassWord());
                    clientConnection.execute(clientRequest);
                    String updateMessage = String.format("update \"%s\" set sync = true where \"Date\" = '%s' and \"Author\" = '%s';",PVTableName,rs.getString(5),rs.getString(3));
                    connectionToLocalDataBase.executeUpdate(updateMessage);
                }
            }
        }
    }

    private void requestChatFromServer(String PVTableName) throws CouldNotConnectToServerException, SQLException, IOException, ClassNotFoundException {
        ClientConnection clientConnection = new ClientConnection();

        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
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

        connectionToLocalDataBase.Disconect();
    }

    public void syncGroups() {
        //TODO
    }

    public void syncTwitts() {
        //TODO
    }

    public void syncTimeLine() {
        //TODO
    }



    public void syncTablesPointToTwitt(String tableName) {

    }

    public void syncTablesPointToMessage(String tableName) {

    }

    public void finalize() throws Throwable {
        connectionToLocalDataBase.Disconect();
        super.finalize();
    }

}
