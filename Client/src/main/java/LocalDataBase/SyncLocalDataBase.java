package LocalDataBase;

import Chats.PV.Model.PV;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Server.ServerRequest;
import User.Controller.UserController;
import User.Model.User;

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

    public void syncAll() throws SQLException, IOException, ClassNotFoundException {
        syncFollowers();
        //syncMutes();
        //syncBlackList();

        //syncFollowings();
        syncChats();

    }


    public void syncFollowers() throws SQLException, IOException, ClassNotFoundException {
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

    public void syncFollowings() throws SQLException, IOException, ClassNotFoundException {
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

    public void syncMutes() throws SQLException, IOException, ClassNotFoundException {
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

    public void syncBlackList() throws SQLException, IOException, ClassNotFoundException {
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

    public void syncChats() throws SQLException, IOException, ClassNotFoundException {
        ClientConnection clientConnection = new ClientConnection();


        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username", mainUser.getUserName());
        ClientRequest clientRequest = new ClientRequest("chats", clientPayLoad, mainUser.getSession(), "readChats", mainUser.getUserName(), mainUser.getPassWord());

        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User mainUser = serverRequest.getPayLoad().getUser();

        String addfollowings;

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
                    addfollowings = String.format("insert into \"ChatsTable\" ( \"ChatAddress\" , \"Username\" , \"Date\",\"sync\") values ('%s','%s','%s','%s');", pv.getPVTableName(), pv.getContact().getUserName(),null, "true");
                    connectionToLocalDataBase.executeUpdate(addfollowings);
                }
            }
        }

        //TODO sync groups


    }

    public void syncPV(String username){

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

}
