package Chats.PV.Controller;

import Chats.Common.Message.Model.Message;
import Chats.PV.Model.PV;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import User.Model.User;
import Utils.DateTime;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerPVController {
    PV pv;
    ConnectionToDataBase connectionToDataBase;
    ServerConnection serverConnection;

    public ServerPVController(ServerConnection serverConnection,PV pv) {
        this.pv = pv;
        this.serverConnection = serverConnection;
        connectionToDataBase = new ConnectionToDataBase();
    }

    public ServerPVController(PV pv) {
        this.pv = pv;
        connectionToDataBase = new ConnectionToDataBase();
    }

    public void createNewPV(User mainUser, User dstUser) throws SQLException, IOException {
        DateTime dateTime = new DateTime();
        String now = dateTime.Now();

        String addtouser1chats = String.format("insert into \"User%sChats\" (\"UUID\",\"UserUUID\",\"Date\") values (uuid_generate_v4(),'%s','%s');", mainUser.getUserUUID(), dstUser.getUserUUID(), now);
        connectionToDataBase.executeUpdate(addtouser1chats);

        String TableUUID = "";
        String table_uuid_query = String.format("select \"UUID\" from \"User%sChats\" where \"UserUUID\" = '%s';", mainUser.getUserUUID(), dstUser.getUserUUID());
        ResultSet rs = connectionToDataBase.executeQuery(table_uuid_query);
        if (rs != null) {
            if (rs.next()) {
                TableUUID = rs.getString(1);
            }
        } else {
            System.out.println("could not select");
        }


        String PVTableName = "PV" + TableUUID;
        pv.setPVTableName(PVTableName);
        String update_table_name = String.format("update \"User%sChats\" set \"PVTableName\" = '%s' where \"UserUUID\" = '%s' ;", mainUser.getUserUUID(), PVTableName, dstUser.getUserUUID());
        connectionToDataBase.executeUpdate(update_table_name);


        String addtouser2chats = String.format("insert into \"User%sChats\" (\"UUID\",\"UserUUID\",\"PVTableName\",\"Date\") values (uuid_generate_v4(),'%s','%s','%s');", dstUser.getUserUUID(), mainUser.getUserUUID(), PVTableName, now);
        connectionToDataBase.executeUpdate(addtouser2chats);


        String create_pv_table = String.format("create table \"%s\"(\"ID\" BIGSERIAL NOT NULL PRIMARY KEY,\"Message\" text,\"Author\" character varying (50), \"ImageAddress\" character varying (200) ,\"Date\" timestamp without time zone);", PVTableName);
        connectionToDataBase.executeUpdate(create_pv_table);


        String add_to_chats_table = String.format("insert into \"ChatTable\" (\"UUID\",\"User1UUID\",\"User2UUID\",\"DateStarted\",\"PVTableName\") values (uuid_generate_v4(),'%s','%s','%s','%s')", mainUser.getUserUUID(), dstUser.getUserUUID(), now, PVTableName);
        connectionToDataBase.executeUpdate(add_to_chats_table);

        pv.setContact(dstUser);
        pv.setUser(mainUser);

        ServerPayLoad serverPayLoad = new ServerPayLoad();
        serverPayLoad.getStringStringHashMap().put("chatAddress",PVTableName);
        ServerRequest serverRequest = new ServerRequest(mainUser.getUserName(),"chatAddress",serverPayLoad);
        serverConnection.execute(serverRequest);

    }


    public String getPVTableName(User user1, User user2) throws SQLException {
        String PVTableName = "";

        String sql = String.format("select \"PVTableName\" from \"User%sChats\" where \"UserUUID\" = '%s';",user1.getUserUUID(),user2.getUserUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null){
            if(rs.next()){
                PVTableName = rs.getString(1);
            }
        }
        else {
            System.out.println("could not get PV table name by User UUID");
        }
        pv.setPVTableName(PVTableName);
        return PVTableName;
    }

    public void readMessages() throws SQLException {

        String sql = String.format("select * from \"%s\";",pv.getPVTableName());
        ResultSet rs = connectionToDataBase.executeQuery(sql);

        if(rs != null){
            while (rs.next()){
                User author;
                if(rs.getString(3).equals(pv.getUser().getUserName())){
                    author = pv.getUser();
                }
                else {
                    author = pv.getContact();
                    author.setUserName(rs.getString(3));
                }
                String text = rs.getString(2);
                String picAddress = rs.getString(4);
                String date = rs.getString(5);
                Message message;
                if(picAddress != null){
                    message = new Message(author,text,date,picAddress);
                }
                else {
                    message = new Message(author,text,date);

                }
                pv.addMessage(message);
            }
        }
    }



    public void finalize() throws Throwable {
        connectionToDataBase.Disconect();
        super.finalize();
    }
}
