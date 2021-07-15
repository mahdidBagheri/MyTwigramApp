package User.Controller;

import Config.PathConfig.PathConfig;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Constants.Constants;
import LocalDataBase.ConnectionToLocalDataBase;
import User.Model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    }

    public void changeProfilePic(String path) throws IOException, SQLException, ClassNotFoundException {
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
    }
}
