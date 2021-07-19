package LocalDataBase;

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
import java.util.LinkedList;

public class SyncLocalDataBase {
    ConnectionToLocalDataBase connectionToLocalDataBase;
    ClientConnection clientConnection;
    User mainUser;

    public SyncLocalDataBase() throws SQLException, IOException, ClassNotFoundException {
        this.connectionToLocalDataBase = new ConnectionToLocalDataBase();
        this.clientConnection = new ClientConnection();
        User mainUser = new User();
        UserController mainUserController = new UserController(mainUser);
        mainUserController.setAsMain();

        this.mainUser = mainUser;
    }

    public void syncAll() throws SQLException, IOException, ClassNotFoundException {
        syncFollowings();
    }


    public void syncFollowers() throws SQLException, IOException, ClassNotFoundException {
        syncTablesPointToUser("followers");
    }

    public void syncFollowings() throws SQLException, IOException, ClassNotFoundException {
        syncTablesPointToUser("followings");
    }

    public void syncMutes() throws SQLException, IOException, ClassNotFoundException {
        syncTablesPointToUser("mutes");
    }

    public void syncBlackList() throws SQLException, IOException, ClassNotFoundException {
        syncTablesPointToUser("blackList");
    }

    public void syncChats() {
        //TODO
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

    public void syncTablesPointToUser(String tableName) throws SQLException, IOException, ClassNotFoundException {
        updateServerDataBase(tableName);
        updateLocalDataBase(tableName);

    }

    private void updateLocalDataBase(String tableName) throws SQLException, IOException, ClassNotFoundException {
        ClientRequest clientRequest = null;
        String deleteFollowingListQuery = String.format("delete from followings;");
        connectionToLocalDataBase.executeUpdate(deleteFollowingListQuery);

        if (tableName.equals("followings")) {
            ClientPayLoad clientPayLoad = new ClientPayLoad();
            clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
            clientRequest = new ClientRequest("userInfo",clientPayLoad,mainUser.getSession(),"userInfo",mainUser.getUserName(),mainUser.getPassWord());
        }

        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());

        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        User mainUser = serverRequest.getPayLoad().getUser();

        String addfollowings;
        for (User user : mainUser.getFollowing()){
            addfollowings = String.format("insert into followings ( \"UUID\" , \"Username\" , \"sync\") values ('%s','%s','%s');",user.getUserUUID(),user.getUserName(),"true");
            connectionToLocalDataBase.executeUpdate(addfollowings);
        }

    }

    private void updateServerDataBase(String tableName) throws SQLException, IOException, ClassNotFoundException {
        String sql = null;
        if(tableName.equals("followers") || tableName.equals("blackList") || tableName.equals("mutes")){
            if (tableName.equals("followers")) {
                sql = String.format("select * from followers;");
            } else if (tableName.equals("blackList")) {
                sql = String.format("select * from blacklist;");
            } else if (tableName.equals("mutes")) {
                sql = String.format("select * from mutes;");
            }

            ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
            ClientPayLoad clientPayLoad = new ClientPayLoad();
            if (rs != null) {
                while (rs.next()) {
                    clientPayLoad.getStringStringHashMap().put("username", rs.getString(2));
                }
            }

            ClientRequest clientRequest = null;
            if (tableName.equals("followers")) {
                clientRequest = new ClientRequest("onUserAction", clientPayLoad, mainUser.getSession(), "syncFollowers", mainUser.getUserName(), mainUser.getPassWord());
            } else if (tableName.equals("blackList")) {
                clientRequest = new ClientRequest("onUserAction", clientPayLoad, mainUser.getSession(), "syncBlackList", mainUser.getUserName(), mainUser.getPassWord());
            } else if (tableName.equals("mutes")) {
                clientRequest = new ClientRequest("onUserAction", clientPayLoad, mainUser.getSession(), "syncMutes", mainUser.getUserName(), mainUser.getPassWord());
            }
            clientConnection.execute(clientRequest);

        }

    }

    public void syncTablesPointToTwitt(String tableName) {

    }

    public void syncTablesPointToMessage(String tableName) {

    }

}
