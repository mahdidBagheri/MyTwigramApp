package ServerLogin.Controller;

import Connection.DataBaseConnection.ConnectionToDataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ServerLoginController {

    public boolean checkUserPass(String username, String password) throws SQLException {
        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
        String sql = String.format("select * from \"UsersTable\" where \"UserName\" = '%s' and \"Pass\" = '%s' ;",username,password);
        ResultSet rs = connectionToDataBase.executeQuery(sql);

        if(rs != null){
            if(rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public String createAndSaveSession(String username) throws SQLException {
        String session = UUID.randomUUID().toString();
        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
        String sql = String.format("update \"UsersTable\" set \"Session\" = '%s' where \"UserName\" = '%s' ;",username,session);
        connectionToDataBase.executeUpdate(sql);
        return session;
    }
}
