package ServerSignup.Controller;

import Connection.Server.ServerConnection;
import Connection.Utils.DataBaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerSignupController {

    ServerConnection serverConnection;

    public ServerSignupController(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public boolean validateEmail(String email) throws SQLException {
        return (isEmailExist(email));
    }

    public boolean isEmailExist(String email) throws SQLException, SQLException {
        String sql = "select \"Email\" from \"UsersTable\";";
        ResultSet rs = serverConnection.getConnectionToDataBase().executeQuery(sql);
        if (!DataBaseUtils.isEmptyTable("UsersTable")) {
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getString(1).equals(email)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean validateUserName(String userName) throws SQLException {
        return isUserNameExist(userName);
    }

    public boolean isUserNameExist(String userName) throws SQLException {
        String sql = "select \"UserName\" from \"UsersTable\";";
        ResultSet rs = serverConnection.getConnectionToDataBase().executeQuery(sql);
        if (!DataBaseUtils.isEmptyTable("UsersTable")) {
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getString(1).equals(userName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
    public void signupUser(User user) throws SQLException {
        DateTime dateTime = new DateTime();
        String now = dateTime.Now();
        String sql = String.format("insert into \"UsersTable\" (\"UserUUID\",\"UserName\",\"Password\",\"Email\",\"DateJoined\",\"Wins\",\"Loose\",\"Score\") values (uuid_generate_v4(),'%s','%s','%s','%s','0','0','0');",
                user.getUsername(),
                user.getPassword(),
                user.getPassword(),
                now);

        serverConnection.getConnectionToDataBase().executeUpdate(sql);
        int a = 0;

    }

     */

    protected void finalize() throws SQLException {
        serverConnection.getConnectionToDataBase().Disconect();
    }



}
