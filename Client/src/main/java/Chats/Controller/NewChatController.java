package Chats.Controller;

import Chats.Model.PV;
import Chats.Model.newChat;
import LocalDataBase.ConnectionToLocalDataBase;
import Utils.DateTime;

import java.io.IOException;
import java.sql.SQLException;

public class NewChatController {
    ConnectionToLocalDataBase connectionToLocalDataBase;

    public NewChatController() throws SQLException, IOException, ClassNotFoundException {
        this.connectionToLocalDataBase = new ConnectionToLocalDataBase();
    }

    public PV createNewChat(String userUUID) {
        return new PV();
    }

    public void saveChatToLocalDataBase(String chatAddress, String username) throws SQLException, IOException, ClassNotFoundException {
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        DateTime dateTime = new DateTime();

        String sql = String.format("insert into \"ChatsTable\" (\"ChatAddress\",\"Username\",\"Date\",\"sync\") values ('%s','%s','%s','%s')",chatAddress,username,dateTime.Now(),"true");
        connectionToLocalDataBase.executeUpdate(sql);

        String create_pv_table = String.format("create table \"%s\"(\"ID\" BIGSERIAL NOT NULL PRIMARY KEY,\"Message\" text,\"Author\" character varying (50), \"ImageAddress\" character varying (200) ,\"Date\" timestamp without time zone);", chatAddress);
        connectionToLocalDataBase.executeUpdate(create_pv_table);

    }

    public void finalize() throws SQLException {
        connectionToLocalDataBase.Disconect();
    }
}
