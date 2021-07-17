package User.Controller;

import Chats.Controller.PVController;
import Chats.Controller.newChatController;
import Chats.Model.Chats;
import Chats.Model.Message;
import Chats.Model.PV;
import Chats.Model.newChat;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Utils.DataBaseUtils;
import Groups.Model.Group;
import Notification.Model.Notification;
import Twitt.Controller.TwittsController;
import Twitt.Model.Twitt;
import User.Exceptions.*;
import User.Model.Activity;
import User.Model.User;
import Utils.DateTime;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import ServerConstants.*;

public class ServerUserController {
    User user;
    public ServerUserController(User user) {
        this.user = user;
    }

    public void readAll() throws unsuccessfullReadDataFromDatabase, SQLException
             {
        readUserName();
        readfirstName();
        readlastName();
        readBio();
        readBirthDate();
        readEmail();
        readLastSeen();
        readLastSeenMode();
        readPassword();
        readStatus();
        readPrivacy();
        readActivities();
        readMutedUsers();
        readFollowing();
        readFollowers();
        readBlackList();
        readSavedTwitts();
        readLikes();
        readPhoneNumber();
        readTwitts();
        readProfilePic();
    }

    public void readAll(String userName) throws unsuccessfullReadDataFromDatabase, SQLException
    {
        readUserUUIDbyUsername(userName);
        readUserName();
        readfirstName();
        readlastName();
        readBio();
        readBirthDate();
        readEmail();
        readLastSeen();
        readLastSeenMode();
        readPassword();
        readStatus();
        readPrivacy();
        readActivities();
        readMutedUsers();
        readFollowing();
        readFollowers();
        readBlackList();
        readSavedTwitts();
        readLikes();
        readPhoneNumber();
        readTwitts();
        readProfilePic();
    }

    private void readUserUUIDbyUsername(String username) throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"UserUUID\" from  \"UsersTable\"  where  \"UserName\" = '%s';", username);
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setUserUUID(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive User Name");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive User Name");
        }
    }

    public void readUserName() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"UserName\" from  \"UsersTable\"  where  \"UserUUID\" = '%s';", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setUserName(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive User Name");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive User Name");
        }
    }

    public void readPassword() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"Pass\" from  \"UsersTable\"  where  \"UserUUID\" = '%s';", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setPassWord(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive User Name");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive User Name");
        }
    }

    public void readfirstName() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"Fname\" from  \"UsersTable\"  where  \"UserUUID\" = '%s';", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setfName(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive First Name");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive First Name");
        }
    }

    public void readlastName() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"Lname\" from  \"UsersTable\"  where  \"UserUUID\" = '%s';", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setlName(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive Last Name");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive Last Name");
        }
    }

    public void readEmail() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"Email\" from  \"UsersTable\"  where  \"UserUUID\" = '%s';", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setEmail(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive Email");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive Email");
        }
    }

    public void readPrivacy() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"Privacy\" from  \"UsersTable\"  where  \"UserUUID\" = '%s';", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setPrivacy(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive Privacy");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive Privacy");
        }
    }

    public void readBirthDate() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"BirthDate\" from  \"UsersTable\"  where  \"UserUUID\" = '%s'", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setBirthDate(rs.getDate(1) == null ? " " : rs.getDate(1).toString());
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive BirthDate");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive BirthDate");
        }
    }

    public void readPhoneNumber() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"PhoneNumber\" from  \"UsersTable\"  where  \"UserUUID\" = '%s'", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setPhoneNumber(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive PhoneNumber");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive PhoneNumber");
        }
    }

    public void readBio() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"Bio\" from  \"UsersTable\"  where  \"UserUUID\" = '%s'", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setBio(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive Bio");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive Bio");
        }
    }

    public void readStatus() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"Status\" from  \"UsersTable\"  where  \"UserUUID\" = '%s'", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setStatus(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive Status");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive Status");
        }
    }

    public void readLastSeen() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"LastSeen\" from  \"UsersTable\"  where  \"UserUUID\" = '%s'", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setLastSeen(rs.getDate(1).toString() + " " + rs.getTime(1).toString());
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive last seen");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive last seen");
        }
    }

    public void readLastSeenMode() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select \"LastSeenMode\" from  \"UsersTable\"  where  \"UserUUID\" = '%s'", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs != null) {
            if (rs.next()) {
                user.setLastSeenMode(rs.getString(1));
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not retrive LastSeenMode");
            }
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not retrive LastSeenMode");
        }
    }

    public void Follow(String dstUserUUID) throws SQLException, alreadyFollowedException, selfFollowException,unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        ServerUserController userController = new ServerUserController(user);
        DateTime dateTime = new DateTime();
        String now = dateTime.Now();

        if (user.getUserUUID().equals(dstUserUUID)) {
            System.out.println("You can not follow yourself :)");
            connectionToServer.Disconect();
            throw new selfFollowException("You can not follow yourself :)");
        }
        User dstuser = new User();
        dstuser.setUserUUID(dstUserUUID);
        ServerUserController desUserController = new ServerUserController(dstuser);

        if (!user.isFollowing(dstuser.getUserName())) {
            String sql = String.format("insert into \"User%sFollowing\" (\"UUID\",\"UserUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s');", user.getUserUUID(), dstuser.getUserUUID(), now);
            connectionToServer.executeUpdate(sql);
        } else {
            System.out.println("you already following this user!");
            connectionToServer.Disconect();
            throw new alreadyFollowedException("you already following this user!");
        }

        if (!dstuser.isFollowedBy(user.getUserName())) {
            String sql = String.format("insert into \"User%sFollowers\"(\"UUID\",\"UserUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s');", dstuser.getUserUUID(), user.getUserUUID(), now);
            connectionToServer.executeUpdate(sql);
        }
        userController.NewFollow(dstUserUUID);
        userController.readUserName();
        desUserController.sendNotification("@" + user.getUserName() + " Followed you.");
        System.out.println("User successfuly followed");
        connectionToServer.Disconect();
    }

    public void UnFollow(String FollowerUUID) throws SQLException, notFollowingUserException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        ServerUserController userController = new ServerUserController(user);

        if (this.user.isFollowing(FollowerUUID)) {
            User dstUser = new User();
            dstUser.setUserUUID(FollowerUUID);

            String deleteFromFollowingTableQuery = String.format("delete from \"User%sFollowing\" where \"UserUUIDs\" = '%s'; ", user.getUserUUID(), dstUser.getUserUUID());
            connectionToServer.executeUpdate(deleteFromFollowingTableQuery);

            String deleteFromFollowersTableQuery = String.format("delete from \"User%sFollowers\" where \"UserUUIDs\" = '%s'; ", dstUser.getUserUUID(), user.getUserUUID());
            connectionToServer.executeUpdate(deleteFromFollowersTableQuery);
            userController.readUserName();
            userController.sendNotification("@" + this.user.getUserName() + " unFollowed you.");
        } else {
            System.out.println("You are not following this user!");
            connectionToServer.Disconect();
            throw new notFollowingUserException("You are not following this user!");
        }

        userController.NewUnFollow(FollowerUUID);
        System.out.println("User successfuly unfollowd");
        connectionToServer.Disconect();
    }

    public String ChangeFollowOrunFollow(String username) throws SQLException,
            alreadyFollowedException, notFollowingUserException, selfFollowException,
            sendFollowRequestException, FileNotFoundException, unsuccessfullReadDataFromDatabase {

        User dstUser = new User();
        ServerUserController dstUserController = new ServerUserController(dstUser);
        dstUserController.readAll(username);

        if (dstUser.getPrivacy().equals("Private")) {
            System.out.println("this user has a private acount to follow he/she must accept your invitation");
            if (!this.user.isFollowing(username)) {
                SendFollowRequest(username);
                return "followRequestSent";
            } else {
                UnFollow(username);
                return "unfollowed";
            }

        } else {

            if (!user.isFollowing(dstUser.getUserUUID())) {
                Follow(dstUser.getUserUUID());
                return "followed";


            } else {
                UnFollow(dstUser.getUserUUID());
                return "unfollowed";
            }
        }
    }

    public void readFollowers() throws SQLException, unsuccessfullReadDataFromDatabase {
        user.setFollowers(new LinkedList<>());
        LinkedList<User> followers = new LinkedList<User>();
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        String followerTableAddress = "User" + user.getUserUUID() + "Followers";
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();

        if (!dataBaseUtils.isEmptyTable(followerTableAddress)) {

            String sql = String.format("select * from  \"%s\" ; ", followerTableAddress);
            ResultSet rs = connectionToServer.executeQuery(sql);
            connectionToServer.Disconect();
            if (rs != null) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserUUID(rs.getString(2));
                    ServerUserController userController = new ServerUserController(user);
                    userController.readUserName();
                    followers.add(user);
                }
                user.setFollowers(followers);
            } else {
                System.out.println("could not add followers");
            }
        }
        connectionToServer.Disconect();
    }

    public void readFollowing() throws SQLException, unsuccessfullReadDataFromDatabase {
        user.setFollowing(new LinkedList<>());
        LinkedList<User> followings = new LinkedList<>();
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        String followingTableAddress = "User" + user.getUserUUID() + "Following";
        if (!dataBaseUtils.isEmptyTable(followingTableAddress)) {
            String getFollowingQuery = String.format("select \"UserUUIDs\" from  \"%s\"; ", followingTableAddress);
            ResultSet rs = connectionToServer.executeQuery(getFollowingQuery);
            connectionToServer.Disconect();
            if (rs != null) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserUUID(rs.getString(1));
                    ServerUserController userController = new ServerUserController(user);
                    userController.readUserName();
                    followings.add(user);
                }
                user.setFollowing(followings);
            } else {
                System.out.println("could not add followers");
            }
        }
        connectionToServer.Disconect();
    }

    public void readBlackList() throws SQLException, unsuccessfullReadDataFromDatabase {
        user.getBlackList().clear();
        LinkedList<User> blackList = new LinkedList<>();
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        String blacklistTableAddress = "User" + user.getUserUUID() + "BlackList";

        if (!dataBaseUtils.isEmptyTable(blacklistTableAddress)) {
            String blacklistQuery = String.format("select \"UserUUIDs\" from  \"%s\" ", blacklistTableAddress);
            ResultSet rs = connectionToServer.executeQuery(blacklistQuery);
            connectionToServer.Disconect();
            if (rs != null) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserUUID(rs.getString(2));
                    ServerUserController userController = new ServerUserController(user);
                    userController.readUserName();
                    blackList.add(user);
                }
                user.setBlackList(blackList);
            } else {
                System.out.println("could not add black list");
            }
        }
        connectionToServer.Disconect();
    }

    public void ChangeBlockOrUnblock(String UserUUID) throws SQLException,
            successfullyUnBlockedException, notFollowingUserException,
            successfullyBlockedException, selfBlockException, unSuccessfullyUnBlockedException,
            unSuccessfullyBlockedException,
            unsuccessfullReadDataFromDatabase {

        if (!user.isBlocked(UserUUID)) {
            Block(UserUUID);
        } else {
            unBlock(UserUUID);
        }
    }

    public void Block(String UserUUID) throws SQLException, notFollowingUserException,
            selfBlockException, successfullyBlockedException, unSuccessfullyBlockedException,
             unsuccessfullReadDataFromDatabase {

        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();
        if (user.getUserUUID().equals(UserUUID)) {
            System.out.println("You can not block yourself :)");
            throw new selfBlockException("You can not block yourself :)");
        }
        User dstUser = new User();
        dstUser.setUserUUID(UserUUID);
        ServerUserController desUserController = new ServerUserController(dstUser);
        desUserController.readFollowing();

        readFollowing();
        if(user.isFollowing(UserUUID)){
            UnFollow(UserUUID);
        }
        if(dstUser.isFollowing(user.getUserUUID())){
            desUserController.UnFollow(user.getUserUUID());
        }
        readBlackList();
        if (!user.getBlackList().contains(UserUUID)) {
            String BlockQuery = String.format("insert into \"User%sBlackList\"(\"UUID\",\"UserUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s'); ", user.getUserUUID(), UserUUID, dateTime.Now());
            connectionToServer.executeUpdate(BlockQuery);
            connectionToServer.Disconect();
            connectionToServer.Disconect();
        } else {
            connectionToServer.Disconect();
            throw new unSuccessfullyBlockedException("Could not block");
        }
    }

    public void unBlock(String UserUUID) throws SQLException, successfullyUnBlockedException, unSuccessfullyUnBlockedException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        readBlackList();
        if (user.getBlackList().contains(UserUUID)) {
            String BlackListQuery = String.format("delete from \"User%sBlackList\" where \"UserUUIDs\" = '%s'", user.getUserUUID(), UserUUID);
            connectionToServer.executeUpdate(BlackListQuery);
            connectionToServer.Disconect();
            connectionToServer.Disconect();
        } else {
            connectionToServer.Disconect();
            throw new unSuccessfullyUnBlockedException("unSuccessfully unBlocked");
        }
    }

    public void readPendingFollowingRequest() throws SQLException, successfullPendingFollowRequestException,
            unSuccessfullPendingFollowRequestException {

        String pendingFollowingRequestTableAddress = "User" + user.getUserUUID() + "PendingFollowing";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        LinkedList<String> PendingFollowingRequest = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(pendingFollowingRequestTableAddress)) {
            String pendingFollowingQuery = String.format("select \"UserUUIDs\" from  \"%s\" ", pendingFollowingRequestTableAddress);
            ResultSet rs = connectionToServer.executeQuery(pendingFollowingQuery);
            connectionToServer.Disconect();
            if (rs != null) {
                while (rs.next()) {
                    PendingFollowingRequest.add(rs.getString(1));
                }
                user.setPendingFollowingRequest(PendingFollowingRequest);
                connectionToServer.Disconect();
                throw new successfullPendingFollowRequestException("invitation sent");
            } else {
                connectionToServer.Disconect();
                throw new unSuccessfullPendingFollowRequestException("could not send invitation");
            }
        }
        connectionToServer.Disconect();

    }

    public void removeFollowingRequest(String dstUserUUID) throws SQLException, removeFollowingRequestException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();

        String sql = String.format("delete from \"User%sPendingFollowingRequest\" where \"UserUUIDs\" = '%s';", user.getUserUUID(), dstUserUUID);
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
        //throw new removeFollowingRequestException("Following request removed");
    }

    public void SendFollowRequest(String dstUserUUID) throws SQLException, alreadyFollowedException, selfFollowException, sendFollowRequestException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();
        String now = dateTime.Now();
        if (user.getUserUUID().equals(dstUserUUID)) {
            System.out.println("You can not follow yourself :)");
            connectionToServer.Disconect();
            throw new selfFollowException("You can not follow yourself :)");
        }

        User dstuser = new User();
        dstuser.setUserUUID(dstUserUUID);
        ServerUserController dstUserController = new ServerUserController(dstuser);
        dstUserController.readFollowing();
        readFollowing();

        if (!user.getFollowing().contains(dstuser.getUserUUID())) {
            String sql = String.format("insert into \"User%sPendingFollowersRequest\" (\"UUID\",\"UserUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s');", dstuser.getUserUUID(), user.getUserUUID(), now);
            connectionToServer.executeUpdate(sql);

            String sql1 = String.format("insert into \"User%sPendingFollowingRequest\"(\"UUID\",\"UserUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s');", user.getUserUUID(), dstuser.getUserUUID(), now);
            connectionToServer.executeUpdate(sql1);
            connectionToServer.Disconect();
        } else {
            System.out.println("you already following this user!");
            connectionToServer.Disconect();
            throw new alreadyFollowedException("you already following this user!");
        }
        connectionToServer.Disconect();
        System.out.println("your invitation sent");
        throw new sendFollowRequestException("your invitation sent");
    }
    /*
    public void readPendingFollowersRequest() throws SQLException, unSuccessfullPendingFollowRequestException,
            unsuccessfullReadUserNameException, unsuccessfullReadDataFromDatabase {

        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        String pendingFollowersRequestTableAddress = "User" + user.getUserUUID() + "PendingFollowersRequest";
        LinkedList<PendingFollowersRequestNotif> pendingFollowersRequestList = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(pendingFollowersRequestTableAddress)) {
            String sql = String.format("select * from  \"%s\"; ", pendingFollowersRequestTableAddress);
            ResultSet rs = connectionToServer.executeQuery(sql);
            connectionToServer.Disconect();
            if (rs != null) {
                while (rs.next()) {
                    PendingFollowersRequestNotif pendingFollowersRequestNotif = new PendingFollowersRequestNotif();
                    User user = new User();
                    user.setUserUUID(rs.getString(2));
                    UserController userController = new UserController(user);
                    userController.readUserName();
                    pendingFollowersRequestNotif.setMsg("user " + user.getUserName() + " sent a follow request.");
                    pendingFollowersRequestNotif.setDate(rs.getString(3));
                    pendingFollowersRequestNotif.setReferenceUser(user);
                    pendingFollowersRequestNotif.setType("ACTION");
                    pendingFollowersRequestList.add(pendingFollowersRequestNotif);
                }
                user.setPendingFollowersRequestNotif(pendingFollowersRequestList);
            } else {
                System.out.println("could not add followers");
                throw new unSuccessfullPendingFollowRequest("Could not read pending follow requests");
            }
        }
        else {
            connectionToServer.Disconect();
        }


    }

     */

    public void removeFollowerRequest(String dstUserUUID) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("delete from \"User%sPendingFollowersRequest\" where \"UserUUIDs\" = '%s';", user.getUserUUID(), dstUserUUID);
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    /*
    public void readNewFollowers() throws SQLException, unsuccessfullReadUserNameException,
            unsuccessfullReadDataFromDatabase {

        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String NewFollowersTableAddress = "User" + user.getUserUUID() + "NewFollowers";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<NewFollowerNotif> NewFollowers = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(NewFollowersTableAddress)) {
            String newFollowersQuery = String.format("select * from  \"%s\" ; ", NewFollowersTableAddress);

            ResultSet rs = connectionToServer.executeQuery(newFollowersQuery);
            connectionToServer.Disconect();
            if (rs != null) {
                while (rs.next()) {
                    NewFollowerNotif newFollowerNotif = new NewFollowerNotif();

                    User user = new User();
                    user.setUserUUID(rs.getString(2));
                    UserController userController = new UserController(user);
                    userController.readUserName();

                    newFollowerNotif.setDate(rs.getString(3));
                    newFollowerNotif.setType("INFORM");
                    newFollowerNotif.setReferenceUser(user);

                    NewFollowers.add(newFollowerNotif);
                }
                user.setNewFollowers(NewFollowers);
            } else {
                connectionToServer.Disconect();
                System.out.println("could not add followers");

            }
            connectionToServer.Disconect();
        }
    }
     */

    public void NewFollow(String dstuserUUID) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();
        String sql = String.format("insert into \"User%sNewFollowers\" (\"UUID\",\"UserUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s');", dstuserUUID, user.getUserUUID(), dateTime.Now());
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void removeNewFollow(String UserUUID) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("delete from \"User%sNewFollowers\" where \"UserUUIDs\" = '%s';", user.getUserUUID(), UserUUID);
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }


    public void readNewUnFollowers() throws SQLException, unSuccessfullNewUnfollowers {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String NewUnFollowersTableAddress = "User" + user.getUserUUID() + "NewUnFollowers";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<String> NewUnFollowers = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(NewUnFollowersTableAddress)) {
            String NewUnFollowersQuery = String.format("select \"UserUUIDs\" from  \"%s\"; ", NewUnFollowersTableAddress);
            ResultSet rs = connectionToServer.executeQuery(NewUnFollowersQuery);
            if (rs != null) {
                while (rs.next()) {
                    NewUnFollowers.add(rs.getString(1));
                }
                user.setNewUnFollowers(NewUnFollowers);
                connectionToServer.Disconect();
            } else {
                System.out.println("could not add followers");
                connectionToServer.Disconect();
                throw new unSuccessfullNewUnfollowers("Could not read New UnFollowers data");
            }

        }
        connectionToServer.Disconect();

    }

    public void NewUnFollow(String dstuserUUID) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();
        String sql = String.format("insert into \"User%sNewUnFollowers\" (\"UUID\",\"UserUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s');", dstuserUUID, user.getUserUUID(), dateTime.Now());
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void readMutedUsers() throws SQLException, unsuccessfullReadDataFromDatabase {
        user.getMutedUsers().clear();
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String NewMutedTableAddress = "User" + user.getUserUUID() + "MutedUsers";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<String> MutedUsersList = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(NewMutedTableAddress)) {

            String MutedQuery = String.format("select \"UserUUIDs\" from  \"%s\"; ", NewMutedTableAddress);
            ResultSet rs = connectionToServer.executeQuery(MutedQuery);
            if (rs != null) {
                while (rs.next()) {
                    MutedUsersList.add(rs.getString(1));
                }
                user.setMutedUsers(MutedUsersList);
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read Muted Users");
            }
        }
        else {
            connectionToServer.Disconect();
        }

    }

    public void Mute(String UserUUID) throws SQLException, unsuccessfullReadDataFromDatabase, unsuccessfullMuteException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();
        readMutedUsers();
        if (!user.getMutedUsers().contains(UserUUID)) {

            String sql = String.format("insert into \"User%sMutedUsers\"(\"UUID\",\"UserUUIDs\",\"Date\") values (uuid_generate_v4(),'%s','%s'); ", user.getUserUUID(), UserUUID, dateTime.Now());
            connectionToServer.executeUpdate(sql);
            connectionToServer.Disconect();
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullMuteException("Could not mute user");
        }

    }

    public void unMute(String UserUUID) throws SQLException, unsuccessfullReadDataFromDatabase, unsuccessfullunMuteException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        readMutedUsers();
        if (user.getMutedUsers().contains(UserUUID)) {
            String sql = String.format("delete from \"User%sMutedUsers\" where \"UserUUIDs\" = '%s'", user.getUserUUID(), UserUUID);
            connectionToServer.executeUpdate(sql);
            connectionToServer.Disconect();
        } else {
            connectionToServer.Disconect();
            throw new unsuccessfullunMuteException("Unsuccessfull mute user");
        }

    }

    public void ChangeMuteOrunMute(String UserUUID) throws SQLException, unsuccessfullMuteException, unsuccessfullReadDataFromDatabase, unsuccessfullunMuteException {
        if (!user.isMuted(UserUUID)) {
            Mute(UserUUID);
        } else {
            unMute(UserUUID);
        }
    }

    public void removeNotification(String Text) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("delete from \"User%sNotifications\" where \"Text\" = '%s';", user.getUserUUID(), Text);
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void readTwitts() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String MutedTableAddress = "User" + user.getUserUUID() + "Twitts";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<Twitt> TwittsList = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(MutedTableAddress)) {
            String getTwittsQuery = String.format("select * from  \"%s\" ", MutedTableAddress);
            ResultSet rs = connectionToServer.executeQuery(getTwittsQuery);
            if (rs != null) {
                while (rs.next()) {
                    Twitt twitt = new Twitt();
                    twitt.setTwittUUID(rs.getString(1));
                    //TODO fillup
                    TwittsList.add(twitt);
                }
                user.setTwitts(TwittsList);
                connectionToServer.Disconect();
            } else {
                System.out.println("could not add followers");
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read Twitts");
            }

        }

    }

    public void readLikes() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String LikesTableAddress = "User" + user.getUserUUID() + "Likes";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<String> LikesList = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(LikesTableAddress)) {
            String LikesQuery = String.format("select \"TwittUUIDs\" from  \"%s\" ", LikesTableAddress);
            ResultSet rs = connectionToServer.executeQuery(LikesQuery);
            if (rs != null) {
                while (rs.next()) {
                    LikesList.add(rs.getString(1));
                }
                user.setLikes(LikesList);
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read likes");
            }
        }
    }

    public void readSavedTwitts() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String SavedTwittsTableAddress = "User" + user.getUserUUID() + "SavedTwitts";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<Twitt> SavedTwittsList = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(SavedTwittsTableAddress)) {

            String SavedTwittsQuery = String.format("select \"TwittUUIDs\" from  \"%s\" ", SavedTwittsTableAddress);
            ResultSet rs = connectionToServer.executeQuery(SavedTwittsQuery);
            if (rs != null) {
                while (rs.next()) {
                    Twitt twitt = new Twitt();
                    twitt.setTwittUUID(rs.getString(1));
                    TwittsController twittsController = new TwittsController(twitt);
                    twittsController.readText();
                    SavedTwittsList.add(twitt);
                }
                user.setSavedTwitts(SavedTwittsList);
                connectionToServer.Disconect();
            } else {
                System.out.println("could not add saved.");
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read saved Twitts");
            }
        }
        else {
            connectionToServer.Disconect();

        }

    }

    public void readReTwitts() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String ReTwittsTableAddress = "User" + user.getUserUUID() + "ReTwitts";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<String> ReTwittsList = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(ReTwittsTableAddress)) {

            String ReTwittsQuery = String.format("select \"TwittUUIDs\" from  \"%s\" ", ReTwittsTableAddress);
            ResultSet rs = connectionToServer.executeQuery(ReTwittsQuery);
            if (rs != null) {
                while (rs.next()) {
                    ReTwittsList.add(rs.getString(1));
                }
                user.setReTwitts(ReTwittsList);
                connectionToServer.Disconect();
            } else {
                System.out.println("could not add followers");
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read ReTwitts");
            }
        }
        else {
            connectionToServer.Disconect();

        }

    }

    public void readReplies() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String RepliesTableAddress = "User" + user.getUserUUID() + "Replies";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<String> RepliesList = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(RepliesTableAddress)) {

            String RepliesQuery = String.format("select \"TwittUUIDs\" from  \"%s\" ", RepliesTableAddress);
            ResultSet rs = connectionToServer.executeQuery(RepliesQuery);
            if (rs != null) {
                while (rs.next()) {
                    RepliesList.add(rs.getString(1));
                }
                user.setReplies(RepliesList);
                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read User Replies");
            }
        }

    }

    public void readChats() throws SQLException, unsuccessfullReadDataFromDatabase {

        user.getChats().clear();
        Chats newChats = new Chats();
        newChats.setUser(user);
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String ChatsTableAddress = "User" + user.getUserUUID() + "Chats";
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        LinkedList<String> ChatsList = new LinkedList<>();

        if (!dataBaseUtils.isEmptyTable(ChatsTableAddress)) {

            String ChatsQuery = String.format("select * from  \"%s\" ", ChatsTableAddress);
            ResultSet rs = connectionToServer.executeQuery(ChatsQuery);
            if (rs != null) {
                while (rs.next()) {
                    PV pv = new PV();

                    pv.setUser(user);

                    User contact = new User();
                    contact.setUserUUID(rs.getString(2));
                    ServerUserController contactController = new ServerUserController(contact);
                    contactController.readUserName();

                    pv.setContact(contact);

                    pv.setPVTableName(rs.getString(3));

                    user.addChat(pv);
                }

                connectionToServer.Disconect();
            } else {
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read Chats");
            }
        }

    }

    public void readActivities() throws SQLException {
        DateTime dateTime = new DateTime();
        user.getActivities().clear();
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("select * from \"User%sActivities\" where \"Date\" between '%s' AND '%s' ;", user.getUserUUID(), dateTime.Yesterday(), dateTime.Now());
        ResultSet rs = connectionToServer.executeQuery(sql);
        if (rs != null) {
            while (rs.next()) {
                Activity activity = new Activity();
                Twitt twitt = new Twitt();
                twitt.setTwittUUID(rs.getString(2));
                activity.setUUID(rs.getString(1));
                activity.setTwitt(twitt);
                activity.setDate(rs.getString(4));
                activity.setType(rs.getString(3));
                user.getActivities().add(activity);
            }
        }
        connectionToServer.Disconect();
    }

    public void addActivity(String TwittUUID, String Activity_Type) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();
        String sql = String.format("insert into \"User%sActivities\" (\"UUID\",\"TwittUUIDs\",\"activity_type\",\"Date\") values (uuid_generate_v4(),'%s','%s','%s');", user.getUserUUID(), TwittUUID, Activity_Type, dateTime.Now());
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void removeActivity(String TwittUUID, String Activity_Type) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("delete from \"User%sActivities\" where \"TwittUUIDs\" = '%s' and \"activity_type\" = '%s';", user.getUserUUID(), TwittUUID, Activity_Type);
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void readNotifications() throws SQLException, unsuccessfullReadDataFromDatabase {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        String NotificationsTableAddress = "User" + user.getUserUUID() + "Notifications";
        LinkedList<Notification> NotificationList = new LinkedList<>();
        if (!dataBaseUtils.isEmptyTable(NotificationsTableAddress)) {
            String NotifQuery = String.format("select * from  \"%s\"; ", NotificationsTableAddress);
            ResultSet rs = connectionToServer.executeQuery(NotifQuery);
            if (rs != null) {
                while (rs.next()) {
                    Notification notification = new Notification();
                    notification.setMsg(rs.getString(2));
                    notification.setDate(rs.getString(3));
                    notification.setType("INFORM");
                    NotificationList.add(notification);
                }
                user.setNotifications(NotificationList);
                connectionToServer.Disconect();
            } else {
                System.out.println("could not add notification");
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read Notifications");
            }
        }
    }

    public void sendNotification(String text) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();
        String sendNotifQuery = String.format("insert into \"User%sNotifications\" (\"UUID\",\"Text\",\"Date\") values(uuid_generate_v4(),'%s','%s');", user.getUserUUID(), text, dateTime.Now());
        connectionToServer.executeUpdate(sendNotifQuery);
        connectionToServer.Disconect();
    }

    public void RemoveNotification(String Text) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String rmvNotifQuery = String.format("delete from \"User%sNotifications\" where \"Text\" = '%s' ;", user.getUserUUID(), Text);
        connectionToServer.executeUpdate(rmvNotifQuery);
        connectionToServer.Disconect();
    }


    public void updateLastSeen() throws SQLException {
        DateTime dateTime = new DateTime();
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("update \"UsersTable\" set \"LastSeen\" = '%s' where \"UserUUID\" = '%s';", this.user.getUserUUID(), dateTime.Now());
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void readGroups() throws SQLException, unsuccessfullReadDataFromDatabase {
        user.getGroups().clear();

        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        String groupsTableAddress = "User" + user.getUserUUID() + "Groups";

        if (!dataBaseUtils.isEmptyTable(groupsTableAddress)) {
            String NotifQuery = String.format("select * from  \"%s\"; ", groupsTableAddress);
            ResultSet rs = connectionToServer.executeQuery(NotifQuery);
            if (rs != null) {
                while (rs.next()) {
                    Group group = new Group();

                    group.setgroupName(rs.getString(3));
                    group.setGroupTableAddress(rs.getString(2));
                    user.addGrouop(group);
                }
                connectionToServer.Disconect();
            } else {
                System.out.println("could not add notification");
                connectionToServer.Disconect();
                throw new unsuccessfullReadDataFromDatabase("Could not read Groups");
            }
        }
    }


    public void deleteAcount() throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("update \"UsersTable\" set \"UserName\" = '0(DELETED)' where \"UserUUID\" = '%s';", user.getUserUUID());
        connectionToServer.executeUpdate(sql);

        String sql1 = String.format("update \"UsersTable\" set \"Status\" = 'deActive' where \"UserUUID\" = '%s';", user.getUserUUID());
        connectionToServer.executeUpdate(sql1);

        connectionToServer.Disconect();

    }

    public void sendMessage(String UserUUID, String Msgtext) throws SQLException, FileNotFoundException {

        DateTime dateTime = new DateTime();
        String now = dateTime.Now();
        PV pv = new PV();
        PVController pvController = new PVController(pv);
        pv.setUser(user);
        //user.ReadDataFromDataBaseByUUID( UserUUID);
        pv.setContact(user);
        String PVName = pvController.getPVTableNameByUserUUID(UserUUID);
        LinkedList<PV> existingPVs = new LinkedList<>();
        if(PVName.equals("")){
            newChat newChat = new newChat( );
            newChatController newChatController = new newChatController(newChat);
            pv.setContact(user);
            pv = newChatController.createNewChat(UserUUID);
        }
        Message message = new Message();
        message.setText(Msgtext);
        message.setDate(now);

        message.setAuthor();
        pv.addMessage(message);

    }

    public void updateLastSeenMode(String mode) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("update \"UsersTable\" set \"LastSeenMode\" = '%s' where \"UserUUID\" = '%s';", mode, this.user.getUserUUID());
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void readProfilePic() throws SQLException, unsuccessfullReadDataFromDatabase {

        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();

        String query = String.format("select \"ProfilePic\" from  \"UsersTable\" where \"UserUUID\" = '%s'; ", user.getUserUUID());
        ResultSet rs = connectionToServer.executeQuery(query);
        if (rs != null) {
            while (rs.next()) {
                String path = rs.getString(1);
                user.setProfilePic(new ImageIcon(path));
                int a = 0;
            }
            connectionToServer.Disconect();
        } else {
            System.out.println("could not add notification");
            connectionToServer.Disconect();
            throw new unsuccessfullReadDataFromDatabase("Could not read Groups");
        }

    }

    public void changeProfilePic(String path) throws IOException, SQLException {

        ImageIcon myImage = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(ServerConstants.picWidth, ServerConstants.picHeight, Image.SCALE_DEFAULT));
        user.setProfilePic(myImage);
        BufferedImage bImage = null;

        File initialImage = new File(path);
        bImage = ImageIO.read(initialImage);

        int type = bImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bImage.getType();

        BufferedImage resizedBuffImg = new BufferedImage(ServerConstants.picWidth, ServerConstants.picHeight, type);
        Graphics2D g = resizedBuffImg.createGraphics();
        g.drawImage(bImage, 0, 0, ServerConstants.picWidth, ServerConstants.picHeight, null);
        g.dispose();

        String dstPath = String.format("../MyTwitterApp-Graphgics/src/src/main/resources/ProfilePics//%s.PNG", user.getUserUUID());

        ImageIO.write(resizedBuffImg, "PNG", new File(dstPath));

        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("update \"UsersTable\" set \"ProfilePic\" = '%s' where \"UserUUID\" = '%s'; ", dstPath, user.getUserUUID());
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void save(String twittUUID) throws SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("insert into \"User%sSavedTwitts\" (\"UUID\",\"TwittUUIDs\") values(uuid_generate_v4(),'%s');",user.getUserUUID(),twittUUID);
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }

    public void report(String twittUUID) throws SQLException, alreadyReportedException, unsuccessfullReadDataFromDatabase {
        Twitt twitt = new Twitt();
        TwittsController twittsController = new TwittsController(twitt);
        twitt.setTwittUUID( twittUUID);
        twittsController.readReports();
        if(twitt.getReports().contains(user.getUserUUID())){
            throw new alreadyReportedException("AlreadyReportedException");
        }
        if(twitt.getReports().size() >= ServerConstants.reportsNum){
            twittsController.deleteTwitt();
        }

        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("insert into \"Twitt%sReports\" (\"UUID\",\"UserUUIDs\") values(uuid_generate_v4(),'%s');",twittUUID,user.getUserUUID());
        connectionToServer.executeUpdate(sql);
        connectionToServer.Disconect();
    }
}
