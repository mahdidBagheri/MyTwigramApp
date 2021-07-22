package Chats.Group.Controller;

import Chats.Common.Message.Model.Message;
import Chats.Group.Model.Group;
import LocalDataBase.ConnectionToLocalDataBase;
import User.Model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientGroupController {
    Group group;

    public ClientGroupController(Group group) {
        this.group = group;
    }

    public void readMessages() throws SQLException, IOException, ClassNotFoundException {
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = String.format("select * from \"%s\";", group.getGroupTableAddress());
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);

        if (rs != null) {
            while (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString(3));
                String text = rs.getString(2);
                String picAddress = rs.getString(4);
                String date = rs.getString(5);
                Message message;
                if (picAddress != null) {
                    message = new Message(user, text, date, picAddress);
                } else {
                    message = new Message(user, text, date);
                }
                group.getMessages().add(message);
            }
        }
        connectionToLocalDataBase.Disconect();
    }
}
