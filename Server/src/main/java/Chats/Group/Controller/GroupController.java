package Chats.Group.Controller;

import Chats.Common.Message.Model.Message;
import Chats.Group.Model.Group;
import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;
import Utils.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class GroupController {
    ServerConnection serverConnection;
    Group group;

    public GroupController(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public GroupController(Group group) {
        this.group = group;
    }

    public void createNewGroup(LinkedList<User> memmbers, String groupName) throws SQLException, unsuccessfullReadDataFromDatabase {

        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();

        UUID uuid = UUID.randomUUID();
        String GroupTableAddress = "Group" + uuid.toString();


        String sql1 = String.format("create table \"%s\" (\"ID\" SERIAL PRIMARY KEY, \"Members\" character varying (50));", GroupTableAddress + "Memmbers");
        connectionToServer.executeUpdate(sql1);

        String sql2 = String.format("create table \"%s\"(\"ID\" BIGSERIAL NOT NULL PRIMARY KEY,\"Message\" text,\"Author\" character varying (50) ,\"ImageAddress\" character varying (300) ,\"Date\" timestamp without time zone);", GroupTableAddress + "Messages");
        connectionToServer.executeUpdate(sql2);

        for (User memmber : memmbers) {
            ServerUserController memmberController = new ServerUserController(memmber);
            memmberController.readUserUUIDbyUsername(memmber.getUserName());
            String sql = String.format("insert into \"User%sGroups\" (\"UUID\",\"GroupTableAddress\",\"GroupTableName\",\"Date\") values (uuid_generate_v4(),'%s','%s','%s');", memmber.getUserUUID(), GroupTableAddress, groupName, dateTime.Now());
            connectionToServer.executeUpdate(sql);
        }

        String query;
        for (int i = 0; i < memmbers.size(); i++) {
            User memmber = memmbers.get(i);
            ServerUserController memmberController = new ServerUserController(memmber);
            memmberController.readUserUUIDbyUsername(memmber.getUserName());
            query = String.format("insert into \"%s\" (\"Members\") values ('%s');", GroupTableAddress + "Memmbers", memmber.getUserName());
            connectionToServer.executeUpdate(query);
        }

        connectionToServer.Disconect();

    }

    public void sendMessage(ClientRequest clientRequest) throws SQLException {
        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
        String groupAddressTable = clientRequest.getClientPayLoad().getStringStringHashMap().get("groupTableAddress");
        String username = clientRequest.getClientPayLoad().getStringStringHashMap().get("username");
        String text = clientRequest.getClientPayLoad().getStringStringHashMap().get("text");
        String picAddress = clientRequest.getClientPayLoad().getStringStringHashMap().get("imageAddress");
        String date = clientRequest.getClientPayLoad().getStringStringHashMap().get("date");
        String sql;
        if(picAddress != null){
            sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"ImageAddress\",\"Date\") values ('%s','%s','%s','%s');",groupAddressTable + "Messages",text,username,picAddress,date);
        }
        else {
            sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"Date\") values ('%s','%s','%s');",groupAddressTable + "Messages",text,username,date);

        }
        connectionToDataBase.executeUpdate(sql);
        connectionToDataBase.Disconect();
    }

    public void readMessages() throws SQLException {
        group.getMessages().clear();

        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
        String sql = String.format("select * from \"%s\" ORDER BY \"Date\" ASC;",group.getGroupTableAddress() + "Messages");
        ResultSet rs = connectionToDataBase.executeQuery(sql);

        if(rs != null){
            while (rs.next()){
                String text = rs.getString(2);
                String date = rs.getString(5);

                User author = new User();
                author.setUserName(rs.getString(3));

                String picAddress ;

                Message message;
                if(rs.getString(4) != null){
                    picAddress = rs.getString(4);
                    message = new Message(author,text,date,picAddress);
                }
                else {
                    message = new Message(author,text,date);
                }

                group.getMessages().add(message);

            }
        }
    }

    public void readMemmbers() throws SQLException, unsuccessfullReadDataFromDatabase {
        group.getMemmbers().clear();

        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
        String sql = String.format("select * from \"%s\";",group.getGroupTableAddress() + "Memmbers");
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null) {
            while (rs.next()) {
                User memmber = new User();
                memmber.setUserName(rs.getString(2));
                ServerUserController serverUserController = new ServerUserController(memmber);
                serverUserController.readUserUUIDbyUsername(memmber.getUserName());

                group.getMemmbers().add(memmber);
            }
        }

    }
}
