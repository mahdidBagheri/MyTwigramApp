package ServerNewTwitt.Controller;

import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import ServerConfig.PathConfig.PathConfig;
import Twitt.Model.Twitt;
import User.Model.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import ServerConstants.*;

public class ServerNewTwittController {
    Twitt newTwitt;
    ClientRequest clientRequest;
    ConnectionToDataBase connectionToDataBase;

    public ServerNewTwittController(ClientRequest clientRequest) {
        this.clientRequest = clientRequest;
        connectionToDataBase = new ConnectionToDataBase();
    }

    public void setTwitt(Twitt newTwitt) {
        this.newTwitt = newTwitt;
    }

    public void newTwitt() throws SQLException, IOException {
        String newTwittQuery;
        User mainUser = clientRequest.getClientPayLoad().getTwitt().getAuthor();

        if(clientRequest.getClientPayLoad().getTwitt().getType().equals("Twitt")){
            newTwittQuery = String.format("insert into \"TwittsTable\" (\"TwittUUID\", text, \"Author\",\"Date\",\"Type\") values ('%s','%s','%s','%s','%s');", newTwitt.getTwittUUID(),newTwitt.getText(), mainUser.getUserUUID(),newTwitt.getDate(), newTwitt.getType());
        }
        //else if(newTwittEvent.getType().equals("Reply")){
        else {
            newTwittQuery = String.format("insert into \"TwittsTable\" (\"TwittUUID\", text, \"Author\",\"Date\",\"Type\",\"ParentReply\") values ('%s','%s','%s','%s','%s','%s');",newTwitt.getTwittUUID(), newTwitt.getText(), mainUser.getUserUUID(),newTwitt.getDate(), newTwitt.getType(),newTwitt.getParent());
        }
        connectionToDataBase.executeUpdate(newTwittQuery);
        String twittUUID = clientRequest.getClientPayLoad().getTwitt().getTwittUUID();
        insertTwittToUserTwitts();
        CreateAdditionalTables(twittUUID);
        InsertToActivities(twittUUID,mainUser, newTwitt.getType(),newTwitt.getDate());

        if(clientRequest.getClientPayLoad().getTwitt().getPicAddress() != null){
            setImage();
        }

        if(newTwitt.getType().equals("reply")){
            InsertIntoParentTwitt();
        }
        connectionToDataBase.Disconect();
    }


    private void insertTwittToUserTwitts() throws SQLException {
        if(newTwitt.getType().equals("Twitt")) {
            String insertTwittToUserTwittsQuery = String.format("insert into \"User%sTwitts\" (\"UUID\", \"TwittUUIDs\",\"Date\") values (uuid_generate_v4(), '%s', '%s');",newTwitt.getAuthor().getUserUUID(), newTwitt.getTwittUUID(),newTwitt.getDate());
            connectionToDataBase.executeUpdate(insertTwittToUserTwittsQuery);
        }
        else if(newTwitt.getType().equals("Reply")){
            String insertTwittToUserTwittsQuery = String.format("insert into \"User%sReplies\" (\"UUID\", \"TwittUUIDs\",\"Date\") values (uuid_generate_v4(), '%s', '%s');",newTwitt.getAuthor().getUserUUID(), newTwitt.getTwittUUID(),newTwitt.getDate());
            connectionToDataBase.executeUpdate(insertTwittToUserTwittsQuery);
        }
    }

    private void CreateAdditionalTables(String twittUUID) throws SQLException {
        CreatTablesPointToUsers(twittUUID);
        CreatTablesPointToTwitts(twittUUID);
    }

    private void CreatTablesPointToUsers(String twittUUID) throws SQLException {

        LinkedList<String> tablesPointToUsers = new LinkedList<String>();
        tablesPointToUsers.add("Likes");
        tablesPointToUsers.add("Reports");

        for (int i = 0; i < tablesPointToUsers.size(); i++) {
            String CreatetablesPointToUsersQuery = String.format("create table \"Twitt%s%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"Username\" character varying (50), \"Date\" timestamp without time zone);", twittUUID, tablesPointToUsers.get(i));
            connectionToDataBase.executeUpdate(CreatetablesPointToUsersQuery);
        }
    }

    private void CreatTablesPointToTwitts(String twittUUID) throws SQLException {
        LinkedList<String> Tables = new LinkedList<String>();

        Tables.add("Replies");
        Tables.add("QuoteTwitts");
        Tables.add("Retwitts");

        for (int i = 0; i < Tables.size(); i++) {
            String CreateFollowersTable = String.format("create table \"Twitt%s%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"ChildTwittUUID\" uuid references \"TwittsTable\"(\"TwittUUID\"));",twittUUID, Tables.get(i));
            connectionToDataBase.executeUpdate(CreateFollowersTable);

            String UpdateUserFieldsQuery = String.format("UPDATE \"TwittsTable\" SET \"%s\" = 'Twitt%s%s' WHERE \"TwittUUID\" = '%s';",Tables.get(i), twittUUID, Tables.get(i),twittUUID );
            connectionToDataBase.executeUpdate(UpdateUserFieldsQuery);

        }
    }

    public void InsertToActivities(String TwittUUID,User mainUser, String Type, String date) throws SQLException {
        String sql = String.format("insert into \"User%sActivities\"(\"UUID\", \"TwittUUIDs\",activity_type,\"Date\") values (uuid_generate_v4(),'%s','%s', '%s') ",mainUser.getUserUUID(),TwittUUID,Type,date);
        connectionToDataBase.executeUpdate(sql);
    }

    public void InsertIntoParentTwitt() throws SQLException {
        String sql = String.format("insert into \"Twitt%sReplies\"(\"UUID\", \"ChildTwittUUID\") values (uuid_generate_v4(),'%s') ",newTwitt.getParent(),newTwitt.getTwittUUID());
        connectionToDataBase.executeUpdate(sql);
    }

    public void setImage() throws IOException, SQLException {
        PathConfig pathConfig = new PathConfig();

        BufferedImage bImage = null;

        File initialImage = clientRequest.getClientPayLoad().getFile();
        bImage = ImageIO.read(initialImage);

        int type = bImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bImage.getType();

        BufferedImage resizedBuffImg = new BufferedImage(ServerConstants.picWidth, ServerConstants.picHeight, type);
        Graphics2D g = resizedBuffImg.createGraphics();
        g.drawImage(bImage, 0, 0, ServerConstants.picWidth, ServerConstants.picHeight, null);
        g.dispose();

        String dstPath = String.format(pathConfig.getTwittsPicPath()+"//%s.PNG",newTwitt.getTwittUUID());

        ImageIO.write(resizedBuffImg, "PNG", new File(dstPath));

        String sql = String.format("update \"TwittsTable\" set \"PicAddress\" = '%s' where \"TwittUUID\" = '%s'; ", dstPath, newTwitt.getTwittUUID());
        connectionToDataBase.executeUpdate(sql);

    }

    public void finalize() throws Throwable {
        connectionToDataBase.Disconect();
        super.finalize();
    }



}
