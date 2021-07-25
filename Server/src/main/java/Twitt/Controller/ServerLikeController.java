package Twitt.Controller;

import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Twitt.Exceptions.TwittReadDataException;
import Twitt.Model.Twitt;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;
import Utils.DateTime;

import java.sql.SQLException;

public class ServerLikeController {

    Twitt twitt;
    User user;
    String now;
    String LikeLable;
    ConnectionToDataBase connectionToDataBase;

    public ServerLikeController(ClientRequest clientRequest) throws TwittReadDataException, unsuccessfullReadDataFromDatabase, SQLException {
        this.twitt = clientRequest.getClientPayLoad().getTwitt();
        connectionToDataBase = new ConnectionToDataBase();

        User mainUser = new User();
        mainUser.setUserName(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
        ServerUserController mainUserController = new ServerUserController(mainUser);
        mainUserController.readUserUUIDbyUsername(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
        mainUserController.readLikes();

        this.user = mainUser;

        DateTime dateTime = new DateTime();
        now = dateTime.Now();
    }

    public void like() throws SQLException {

        addUserToTwittLikes();
        addTwittToUserLikes();

        ServerUserController mainUserController = new ServerUserController(user);
        mainUserController.addActivity(twitt.getTwittUUID(),"Like");

        User author = twitt.getAuthor();
        author.setUserUUID(twitt.getAuthorUUID());
        ServerUserController authorController = new ServerUserController(author);

        authorController.sendNotification( String.format("User @%s liked your twitt : \n %s",user.getUserName(),twitt.getText()));

    }

    public void addUserToTwittLikes() throws SQLException {
        //Username ro avaz kardam bejay uuid
        String sql = String.format("insert into \"Twitt%sLikes\"(\"UUID\",\"Username\",\"Date\") values (uuid_generate_v4(),'%s','%s');",twitt.getTwittUUID(),user.getUserName(),now);
        connectionToDataBase.executeUpdate(sql);
    }

    public void addTwittToUserLikes() throws SQLException {
        String sql = String.format("insert into \"User%sLikes\"(\"UUID\",\"TwittUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s');",user.getUserUUID(),twitt.getTwittUUID(),now);
        connectionToDataBase.executeUpdate(sql);
    }

    public void removeLike() throws SQLException {
        removeUserfromTwittLikes();
        removeTwittFromUserLikes();
    }

    public void removeUserfromTwittLikes() throws SQLException {
        String sql = String.format("delete from \"Twitt%sLikes\" where \"Username\" = '%s' ;",twitt.getTwittUUID() ,user.getUserName());
        connectionToDataBase.executeUpdate(sql);
    }

    public void removeTwittFromUserLikes() throws SQLException {
        String sql = String.format("delete from \"User%sLikes\" where \"TwittUUIDs\" ='%s';",user.getUserUUID(),twitt.getTwittUUID());
        connectionToDataBase.executeUpdate(sql);
    }

    public void change_Like_OR_noLike() throws SQLException {
        if(user.getLikes().contains(twitt.getTwittUUID())){
            removeLike();
            LikeLable = "Like";
        }
        else {
            like();
            LikeLable = "remove like";
        }
    }

    public Twitt getTwitt() {
        return twitt;
    }

    public void setTwitt(Twitt twitt) {
        this.twitt = twitt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getLikeLable() {
        return LikeLable;
    }

    public void setLikeLable(String likeLable) {
        LikeLable = likeLable;
    }

    public void finalize() throws Throwable {
        connectionToDataBase.Disconect();
        super.finalize();
    }
}
