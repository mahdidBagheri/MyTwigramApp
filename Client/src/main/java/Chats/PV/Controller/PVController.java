package Chats.PV.Controller;

import Chats.Common.Message.Model.Message;
import Chats.PV.Model.PV;
import LocalDataBase.ConnectionToLocalDataBase;
import User.Model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PVController {
    PV pv;
    public PVController(PV pv) {
        this.pv = pv;
    }

    public String getPVTableNameByUserUUID(String userUUID) {
        //
        //
        //
        return "to be determined";
    }

    public void readMessages() throws SQLException, IOException, ClassNotFoundException {
        pv.getMessages().clear();
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = String.format("select * from \"%s\";",pv.getPVTableName());
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);

        if(rs != null){
            while (rs.next()){
                User user = new User();
                user.setUserName(rs.getString(3));
                String text  = rs.getString(2);
                String picAddress = rs.getString(4);
                String date = rs.getString(5);
                Message message;
                if(picAddress != null){
                    message = new Message(user,text,date,picAddress);
                }
                else {
                    message = new Message(user,text,date);
                }
                pv.addMessage(message);
            }
        }
        connectionToLocalDataBase.Disconect();
    }
}
