package ServerSignup.Controller;

import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import Connection.Utils.DataBaseUtils;
import User.Model.User;
import Utils.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ServerSignupController {
    DateTime dateTime = new DateTime();

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


    protected void finalize() throws SQLException {
        serverConnection.getConnectionToDataBase().Disconect();
    }


    public void signUpUser(User user) throws SQLException {
        String nowString = dateTime.Now();
        String sqlQuery;
        String defaultProfilePath = "../MyTwitterApp-Graphgics/src/src/main/resources/ProfilePics//defaultProfilePic.PNG";

        if(user.getBirthDate() != null){
            sqlQuery = String.format("insert into \"UsersTable\" (\"UserUUID\", " +
                            "\"UserName\", " +
                            "\"Pass\"," +
                            "\"Fname\", " +
                            "\"Lname\"," +
                            "\"Email\"," +
                            "\"BirthDate\","+
                            "\"Bio\","+
                            "\"PhoneNumber\","+
                            "\"DateJoined\","+
                            "\"Privacy\","+
                            "\"Status\","+
                            "\"LastSeenMode\","+
                            "\"LastSeen\","+
                            "\"ProfilePic\")"+
                            "values (uuid_generate_v4(),"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'Public',"+
                            "'Active',"+
                            "'EveryOne',"+
                            "'%s'," +
                            "'%s');",
                    user.getUserName(),
                    user.getPassWord(),
                    user.getfName(),
                    user.getlName(),
                    user.getEmail(),
                    user.getBirthDate(),
                    user.getBio(),
                    user.getPhoneNumber(),
                    nowString,
                    nowString,
                    defaultProfilePath);

        }
        else {
            sqlQuery = String.format("insert into \"UsersTable\" (\"UserUUID\", " +
                            "\"UserName\", " +
                            "\"Pass\"," +
                            "\"Fname\", " +
                            "\"Lname\"," +
                            "\"Email\"," +
                            "\"Bio\","+
                            "\"PhoneNumber\","+
                            "\"DateJoined\","+
                            "\"Privacy\","+
                            "\"Status\","+
                            "\"LastSeenMode\","+
                            "\"LastSeen\"," +
                            "\"ProfilePic\")"+
                            "values (uuid_generate_v4(),"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'Public',"+
                            "'Active',"+
                            "'EveryOne',"+
                            "'%s'," +
                            "'%s');",
                    user.getUserName(),
                    user.getPassWord(),
                    user.getfName(),
                    user.getlName(),
                    user.getEmail(),
                    user.getBio(),
                    user.getPhoneNumber(),
                    nowString,
                    nowString,
                    defaultProfilePath);

        }

        serverConnection.getConnectionToDataBase().executeUpdate(sqlQuery);
        String UserUUID = ReadUUID(user.getUserName(),serverConnection.getConnectionToDataBase());
        CreateAdditionalTables(UserUUID,serverConnection.getConnectionToDataBase());
        System.out.println("successfully signed up");
        serverConnection.getConnectionToDataBase().Disconect();
    }

    public String ReadUUID(String UserName, ConnectionToDataBase connectionToDataBase) throws SQLException {
        ResultSet rs = null;
        String getUid = String.format("select * from \"UsersTable\" where \"UserName\" = '%s'", UserName);
        rs = connectionToDataBase.executeQuery(getUid);
        String UserUUID = "";
        if(rs.next()){
            UserUUID = rs.getString(1);
        }
        else {
            System.out.println("could not add user");
        }
        return UserUUID;
    }


    public void CreateAdditionalTables(String UserUUID,ConnectionToDataBase connectionToDataBase) throws SQLException {
        CreateTablesPontToUser(UserUUID, connectionToDataBase);
        CreateTablesPontToTwitt(UserUUID, connectionToDataBase);
        CreateNotificationTable(UserUUID, connectionToDataBase);
        CreateChatTable(UserUUID, connectionToDataBase);
        CreateGroupTable(UserUUID, connectionToDataBase);
    }

    public void CreateTablesPontToUser(String UserUUID,ConnectionToDataBase connectionToDataBase) throws SQLException {

        LinkedList<String> TablesPontToUser = new LinkedList<>();
        TablesPontToUser.add("Followers");
        TablesPontToUser.add("Following");
        TablesPontToUser.add("PendingFollowersRequest");
        TablesPontToUser.add("PendingFollowingRequest");
        TablesPontToUser.add("NewFollowers");
        TablesPontToUser.add("NewUnFollowers");
        TablesPontToUser.add("BlackList");
        TablesPontToUser.add("MutedUsers");

        for (int i = 0; i < TablesPontToUser.size(); i++) {
            String CreateFollowersTable = String.format("create table \"User%s%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"UserUUIDs\" uuid references \"UsersTable\"(\"UserUUID\"), \"Date\" timestamp without time zone);", UserUUID, TablesPontToUser.get(i));
            connectionToDataBase.executeUpdate(CreateFollowersTable);
            String UpdateUserFields = String.format("UPDATE \"UsersTable\" SET \"%s\" = 'User%s%s' WHERE \"UserUUID\" = '%s';",TablesPontToUser.get(i), UserUUID, TablesPontToUser.get(i),UserUUID );
            connectionToDataBase.executeUpdate(UpdateUserFields);
        }
    }

    public void CreateTablesPontToTwitt (String UserUUID, ConnectionToDataBase connectionToDataBase) throws SQLException {

        LinkedList<String> TablesPontToTwitt = new LinkedList<>();
        TablesPontToTwitt.add("Twitts");
        TablesPontToTwitt.add("Likes");
        TablesPontToTwitt.add("ReTwitts");
        TablesPontToTwitt.add("Replies");
        TablesPontToTwitt.add("Qoutes");
        TablesPontToTwitt.add("SavedTwitts");
        TablesPontToTwitt.add("Activities");

        for (int i = 0; i < TablesPontToTwitt.size(); i++) {
            String CreateFollowersTable = String.format("create table \"User%s%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY,\"TwittUUIDs\" uuid references \"TwittsTable\"(\"TwittUUID\"), \"Date\" timestamp without time zone);", UserUUID, TablesPontToTwitt.get(i));
            if (TablesPontToTwitt.get(i).equals("Activities")){
                CreateFollowersTable = String.format("create table \"User%s%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY,\"TwittUUIDs\" uuid references \"TwittsTable\"(\"TwittUUID\"),Activity_type character varying(50), \"Date\" timestamp without time zone);", UserUUID, TablesPontToTwitt.get(i));
            }
            connectionToDataBase.executeUpdate(CreateFollowersTable);
            String UpdateUserFields = String.format("UPDATE \"UsersTable\" SET \"%s\" = 'User%s%s' WHERE \"UserUUID\" = '%s';",TablesPontToTwitt.get(i), UserUUID, TablesPontToTwitt.get(i),UserUUID );
            connectionToDataBase.executeUpdate(UpdateUserFields);
        }


    }

    public void CreateTablesPontToTwittAndUser(String UserUUID, ConnectionToDataBase connectionToDataBase) throws SQLException {
        LinkedList<String> TablesPontToTwittAndUser = new LinkedList<>();
        TablesPontToTwittAndUser.add("Reports");

        for (int i = 0; i < TablesPontToTwittAndUser.size(); i++) {
            String CreateFollowersTable = String.format("create table \"user%s%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY,\"ReporterUUID\" uuid references \"UsersTable\"(\"UserUUID\"),\"Date\" timestamp without time zone);", UserUUID, TablesPontToTwittAndUser.get(i));
            connectionToDataBase.executeUpdate(CreateFollowersTable);
            String UpdateUserFields = String.format("UPDATE \"UsersTable\" SET \"%s\" = 'User%s%s' WHERE \"UserUUID\" = '%s';",TablesPontToTwittAndUser.get(i), UserUUID, TablesPontToTwittAndUser.get(i), UserUUID );
            connectionToDataBase.executeUpdate(UpdateUserFields);
        }

    }

    public void CreateNotificationTable(String UserUUID, ConnectionToDataBase connectionToDataBase) throws SQLException {

        String sql = String.format("create table \"User%sNotifications\" (\"UUID\" uuid NOT NULL PRIMARY KEY,\"Text\" text, \"Date\" timestamp without time zone);",UserUUID);
        connectionToDataBase.executeUpdate(sql);

        String sql1 = String.format("update \"UsersTable\" set \"Notifications\" = '%s' where \"UserUUID\" = '%s'; ", "User" + UserUUID + "Notifications" , UserUUID);
        connectionToDataBase.executeUpdate(sql1);

    }

    public void CreateChatTable(String UserUUID, ConnectionToDataBase connectionToDataBase) throws SQLException {

        String sql = String.format("create table \"User%sChats\" (\"UUID\" uuid NOT NULL PRIMARY KEY,\"UserUUID\" uuid references \"UsersTable\"(\"UserUUID\") ,\"PVTableName\" character varying (50), \"Date\" timestamp without time zone);",UserUUID);
        connectionToDataBase.executeUpdate(sql);

        String sql1 = String.format("update \"UsersTable\" set \"Chats\" = '%s' where \"UserUUID\" = '%s'; ", "User" + UserUUID + "Chats" , UserUUID);
        connectionToDataBase.executeUpdate(sql1);

    }

    public void CreateGroupTable(String UserUUID, ConnectionToDataBase connectionToDataBase) throws SQLException {

        String sql = String.format("create table \"User%sGroups\" (\"UUID\" uuid NOT NULL PRIMARY KEY,\"GroupTableAddress\" character varying (50),\"GroupTableName\" character varying (50), \"Date\" timestamp without time zone);",UserUUID);
        connectionToDataBase.executeUpdate(sql);

        String sql1 = String.format("update \"UsersTable\" set \"Groups\" = '%s' where \"UserUUID\" = '%s'; ", "User" + UserUUID + "Groups" , UserUUID);
        connectionToDataBase.executeUpdate(sql1);
    }


}
