package Twitt.Controller;

import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Twitt.Exceptions.AlreadyRetwittedException;
import Twitt.Exceptions.TwittReadDataException;
import Twitt.Model.Twitt;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;
import Utils.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ServerRetwittController {
    Twitt parentTwitt;
    User user;
    String now;
    String RetwittUUID;
    ConnectionToDataBase connectionToDataBase;

    public ServerRetwittController(ClientRequest clientRequest) throws TwittReadDataException, unsuccessfullReadDataFromDatabase, SQLException {
        this.parentTwitt = clientRequest.getClientPayLoad().getTwitt();
        connectionToDataBase = new ConnectionToDataBase();

        User mainUser = new User();
        mainUser.setUserName(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
        ServerUserController mainUserController = new ServerUserController(mainUser);
        mainUserController.readUserUUIDbyUsername(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
        mainUserController.readReTwitts();
        mainUserController.readActivities();

        this.user = mainUser;

        DateTime dateTime = new DateTime();
        now = dateTime.Now();
    }

    public void retwitt() throws SQLException, TwittReadDataException, AlreadyRetwittedException, unsuccessfullReadDataFromDatabase {
        ServerTwittsController parentTwittController = new ServerTwittsController(parentTwitt);
        parentTwittController.readReTwitts();
        if (hasIntersection(user.getReTwitts(), parentTwitt.getReTwitts())) {
            throw new AlreadyRetwittedException("Already Retwitted");
        }
        addRetwittToTwittsTable();
        addTwittToTwittRetwitts();
        addRetwittToUserRetwitts();


        ServerUserController mainUserController = new ServerUserController(user);
        mainUserController.addActivity(parentTwitt.getTwittUUID(),"ReTwitt");

        User author = parentTwitt.getAuthor();
        author.setUserUUID(parentTwitt.getAuthorUUID());
        ServerUserController authorController = new ServerUserController(author);
        mainUserController.readUserName();

        ServerTwittsController twittsController = new ServerTwittsController(parentTwitt);
        parentTwittController.readText();

        authorController.sendNotification( String.format("User @%s Retwitted your twitt : \n %s",user.getUserName(),parentTwitt.getText()));

    }

    public boolean hasIntersection(LinkedList<String > list1, LinkedList<String> list2){
        for(int i =0; i<list1.size();i++){
            for(int j = 0; j<list2.size();j++){
                if(list1.get(i).equals(list2.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

    public void addRetwittToTwittsTable() throws SQLException {
        String sql = String.format("insert into \"TwittsTable\" (\"TwittUUID\", text, \"Author\",\"ParentRetwitt\",\"Date\",\"Type\") values (uuid_generate_v4(),'%s','%s','%s','%s','ReTwitt');", parentTwitt.getText(), user.getUserUUID(), parentTwitt.getTwittUUID(), now);
        connectionToDataBase.executeUpdate(sql);

        sql = (String.format("select \"TwittUUID\" from \"TwittsTable\" where \"Author\" = '%s' and \"Date\" = '%s' ;", user.getUserUUID(), now));
        ResultSet rs1 = connectionToDataBase.executeQuery(sql);
        if (rs1 != null && rs1.next()) {
            this.setRetwittUUID(rs1.getString(1));
        } else {
            System.out.println("could not get twitt uuid");
        }

        CreatAdditionalTables();
        InsertToActivities();

    }

    public void addTwittToTwittRetwitts() throws SQLException {
        String sql = String.format("insert into \"Twitt%sRetwitts\" (\"UUID\", \"ChildTwittUUID\") values (uuid_generate_v4(),'%s');", parentTwitt.getTwittUUID(), RetwittUUID);
        connectionToDataBase.executeUpdate(sql);
    }

    public void addRetwittToUserRetwitts() throws SQLException {
        String sql = String.format("insert into \"User%sReTwitts\" (\"UUID\", \"TwittUUIDs\") values (uuid_generate_v4(),'%s');", user.getUserUUID(), RetwittUUID);
        connectionToDataBase.executeUpdate(sql);

    }

    public void CreatAdditionalTables() throws SQLException {
        CreatTablesPointToUsers();
        CreatTablesPointToTwitts();
    }

    private void CreatTablesPointToUsers() throws SQLException {

        LinkedList<String> tablesPointToUsers = new LinkedList<String>();
        tablesPointToUsers.add("Likes");
        tablesPointToUsers.add("Reports");

        for (int i = 0; i < tablesPointToUsers.size(); i++) {
            String CreatetablesPointToUsersQuery = String.format("create table \"Twitt%s%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"UserUUIDs\" uuid references \"UsersTable\"(\"UserUUID\"), \"Date\" timestamp without time zone);", RetwittUUID, tablesPointToUsers.get(i));
            connectionToDataBase.executeUpdate(CreatetablesPointToUsersQuery);
        }
    }

    private void CreatTablesPointToTwitts() throws SQLException {
        LinkedList<String> Tables = new LinkedList<String>();

        Tables.add("Replies");
        Tables.add("QuoteTwitts");
        Tables.add("Retwitts");

        for (int i = 0; i < Tables.size(); i++) {
            String CreateFollowersTable = String.format("create table \"Twitt%s%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"ChildTwittUUID\" uuid references \"TwittsTable\"(\"TwittUUID\"));",RetwittUUID, Tables.get(i));
            connectionToDataBase.executeUpdate(CreateFollowersTable);

            String UpdateUserFieldsQuery = String.format("UPDATE \"TwittsTable\" SET \"%s\" = 'Twitt%s%s' WHERE \"TwittUUID\" = '%s';",Tables.get(i), RetwittUUID, Tables.get(i),RetwittUUID );
            connectionToDataBase.executeUpdate(UpdateUserFieldsQuery);

        }
    }

    public void InsertToActivities() throws SQLException {
        String sql = String.format("insert into \"User%sActivities\"(\"UUID\", \"TwittUUIDs\",activity_type,\"Date\") values (uuid_generate_v4(),'%s','%s', '%s') ",user.getUserUUID(),RetwittUUID,"ReTwitt",now);
        connectionToDataBase.executeUpdate(sql);
    }






    public String getRetwittUUID() {
        return RetwittUUID;
    }

    public void setRetwittUUID(String retwittUUID) {
        RetwittUUID = retwittUUID;
    }
}
