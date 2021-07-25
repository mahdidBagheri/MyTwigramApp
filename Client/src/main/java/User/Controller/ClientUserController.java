package User.Controller;

import Chats.Group.Model.Group;
import Chats.PV.Controller.PVController;
import Chats.PV.Model.PV;
import Config.PathConfig.PathConfig;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Constants.Constants;
import LocalDataBase.ConnectionToLocalDataBase;
import Twitt.Model.Twitt;
import User.Model.User;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserController {
    User user;

    public UserController(User user) {
        this.user = user;
    }


    public void setAsMain() throws SQLException, IOException, ClassNotFoundException {
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = String.format("select * from \"UserInfo\" ;");

        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        if(rs != null){
            if(rs.next()){
                user.setUserUUID(rs.getString(1));
                user.setUserName(rs.getString(2));
                user.setPassWord(rs.getString(3));
                user.setfName(rs.getString(4));
                user.setlName(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setPrivacy(rs.getString(7));
                user.setBirthDate(rs.getString(8));
                user.setPhoneNumber(rs.getString(9));
                user.setBio(rs.getString(10));
                user.setStatus(rs.getString(11));
                user.setLastSeen(rs.getString(12));
                user.setLastSeenMode(rs.getString(13));
                user.setProfilePic(new ImageIcon(rs.getString(16)));
                user.setSession(rs.getString(17));
                user.setSync(rs.getString(18));
            }
        }

        readFollowers();
        readFollowings();
        readMutes();
        readBlackList();
        readPVs();
        readGroups();
        readTwitt();
        connectionToLocalDataBase.Disconect();
    }

    private void readTwitt() throws SQLException, IOException, ClassNotFoundException {
        user.getTwitts().clear();
        String sql = String.format("select * from \"twitts\";");
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        if(rs != null){
            while (rs.next()){
                Twitt twitt = new Twitt();
                twitt.setTwittUUID(rs.getString(1));
                twitt.setText(rs.getString(2));
                user.getTwitts().add(twitt);
            }
        }

    }

    private void readGroups() throws SQLException, IOException, ClassNotFoundException {
        user.getGroups().clear();
        String sql = String.format("select * from \"GroupsTable\";");
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);

        if(rs != null){
            while (rs.next()){
                Group group = new Group();
                group.setMainUser(user);
                group.setGroupTableAddress(rs.getString(1));
                group.setGroupName(rs.getString(2));
                user.getGroups().add(group);
            }
        }
        for(Group group:user.getGroups()){
            String memmberQuery = String.format("select * from \"%s\";",group.getGroupTableAddress() + "Memmbers");
            ResultSet rs1 = connectionToLocalDataBase.executeQuery(memmberQuery);
            if(rs1 != null){
                while (rs1.next()) {
                    User member = new User();
                    member.setUserName(rs1.getString(2));
                    group.getMemmbers().add(member);
                }
            }

        }
        connectionToLocalDataBase.Disconect();
    }

    public void readPVs() throws Throwable {
        user.getChats().clear();
        String sql = String.format("select * from \"ChatsTable\";");
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);

        if(rs != null){
            while (rs.next()){
                PV pv = new PV();
                pv.setUser(user);

                User contact = new User();
                contact.setUserName(rs.getString(2));
                pv.setContact(contact);

                pv.setPVTableName(rs.getString(1));
                PVController pvController = new PVController(pv);
                pvController.readMessages();
                pvController.finalize();
                user.addChat(pv);
            }
        }
        connectionToLocalDataBase.Disconect();
    }


    private void readFollowers() throws SQLException, IOException, ClassNotFoundException {
        user.setFollowers(new LinkedList<User>());

        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = "select * from \"followers\";";
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        if(rs != null){
            while (rs.next()){
                User flwer = new User();
                flwer.setUserUUID(rs.getString(1));
                flwer.setUserName(rs.getString(2));
                user.getFollowers().add(flwer);
            }
        }
        connectionToLocalDataBase.Disconect();
    }

    private void readFollowings() throws SQLException, IOException, ClassNotFoundException {
        user.setFollowing(new LinkedList<User>());

        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = "select * from \"followings\";";
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        if(rs != null){
            while (rs.next()){
                User flwing = new User();
                flwing.setUserUUID(rs.getString(1));
                flwing.setUserName(rs.getString(2));
                user.getFollowing().add(flwing);
            }
        }
        connectionToLocalDataBase.Disconect();

    }
    private void readMutes() throws SQLException, IOException, ClassNotFoundException {
        user.setMutedUsers(new LinkedList<User>());

        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = "select * from \"mutes\";";
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        if(rs != null){
            while (rs.next()){
                User mutedUser = new User();
                mutedUser.setUserUUID(rs.getString(1));
                mutedUser.setUserName(rs.getString(2));
                user.getMutedUsers().add(mutedUser);
            }
        }
        connectionToLocalDataBase.Disconect();

    }

    private void readBlackList() throws SQLException, IOException, ClassNotFoundException {
        user.setBlackList(new LinkedList<User>());

        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = "select * from \"blacklist\";";
        ResultSet rs = connectionToLocalDataBase.executeQuery(sql);
        if(rs != null){
            while (rs.next()){
                User blockedUser = new User();
                blockedUser.setUserUUID(rs.getString(1));
                blockedUser.setUserName(rs.getString(2));
                user.getBlackList().add(blockedUser);
            }
        }
        connectionToLocalDataBase.Disconect();

    }

    public void changeProfilePic(String path) throws IOException, SQLException, ClassNotFoundException, CouldNotConnectToServerException {
        PathConfig pathConfig = new PathConfig();
        ImageIcon myImage = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(Constants.picWidth, Constants.picHeight, Image.SCALE_DEFAULT));
        user.setProfilePic(myImage);
        BufferedImage bImage = null;

        File initialImage = new File(path);
        bImage = ImageIO.read(initialImage);

        int type = bImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bImage.getType();

        BufferedImage resizedBuffImg = new BufferedImage(Constants.picWidth, Constants.picHeight, type);
        Graphics2D g = resizedBuffImg.createGraphics();
        g.drawImage(bImage, 0, 0, Constants.picWidth, Constants.picHeight, null);
        g.dispose();

        String dstPath = String.format(pathConfig.getUsersPicsPath() + "//%s.PNG", user.getUserUUID());

        ImageIO.write(resizedBuffImg, "PNG", new File(dstPath));

        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.setUser(user);
        clientPayLoad.setFile(new File(dstPath));
        clientPayLoad.getStringStringHashMap().put("type",Integer.toString(type));

        ClientRequest clientRequest = new ClientRequest("profile",clientPayLoad,user.getSession(),"changeProfilePic",user.getUserName(),user.getPassWord());
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.execute(clientRequest);

        saveProfilePathToLocalDataBase(String.format(pathConfig.getUsersPicsPath() + "//%s.PNG", user.getUserUUID()));

    }

    public void saveProfilePathToLocalDataBase(String path) throws SQLException, IOException, ClassNotFoundException {
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = String.format("update \"UserInfo\" set \"ProfilePic\" = '%s';",path);
        connectionToLocalDataBase.executeUpdate(sql);
        connectionToLocalDataBase.Disconect();
        super.finalize();
    }
}
