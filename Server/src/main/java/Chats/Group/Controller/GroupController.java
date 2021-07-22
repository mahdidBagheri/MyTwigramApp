package Chats.Group.Controller;

import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;
import Utils.DateTime;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class GroupController {
    ServerConnection serverConnection;

    public GroupController(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
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
}
