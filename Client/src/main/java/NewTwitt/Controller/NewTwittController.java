package NewTwitt.Controller;

import Config.PathConfig.PathConfig;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Constants.Constants;
import LocalDataBase.ConnectionToLocalDataBase;
import NewTwitt.Events.NewTwittEvent;
import Twitt.Model.Twitt;
import User.Controller.ClientUserController;
import User.Model.User;
import Utils.DateTime;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class NewTwittController {
    Twitt newTwitt;

    public void newTwitt(NewTwittEvent newTwittEvent) throws SQLException, IOException, ClassNotFoundException {
        newTwitt = new Twitt();

        setNewTwittUUID();
        newTwitt.setText(newTwittEvent.getText());
        setPath(newTwittEvent.getPicPath());
        setNewTwittAuthor();
        setNewTwittDate();
        setType(newTwittEvent.getType());

        sendToServer();
        saveToLocalDataBase();
    }

    private void saveToLocalDataBase() throws SQLException, IOException, ClassNotFoundException {
        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();
        String sql = String.format("insert into \"twitts\"(\"TwittUUID\",\"text\",\"Author\",\"sync\") values ('%s','%s','%s','%s');",newTwitt.getTwittUUID(),newTwitt.getText(),newTwitt.getAuthorUUID(),"true");
        connectionToLocalDataBase.executeUpdate(sql);
    }

    private void sendToServer() throws IOException {
        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.setTwitt(newTwitt);

        if(newTwitt.getPicAddress() != null){
            clientPayLoad.setFile(new File(newTwitt.getPicAddress()));
        }

        ClientRequest clientRequest = new ClientRequest("newTwitt",clientPayLoad,newTwitt.getAuthor().getSession(),"newTwitt",newTwitt.getAuthor().getUserName(),newTwitt.getAuthor().getPassWord());
        clientConnection.execute(clientRequest);
    }

    public void setPath(String path) throws IOException {
        if(path!= null) {
            PathConfig pathConfig = new PathConfig();
            ImageIcon myImage = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(Constants.picWidth, Constants.picHeight, Image.SCALE_DEFAULT));
            newTwitt.setPic(myImage);
            BufferedImage bImage = null;

            File initialImage = new File(path);
            bImage = ImageIO.read(initialImage);

            int type = bImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bImage.getType();

            BufferedImage resizedBuffImg = new BufferedImage(Constants.picWidth, Constants.picHeight, type);
            Graphics2D g = resizedBuffImg.createGraphics();
            g.drawImage(bImage, 0, 0, Constants.picWidth, Constants.picHeight, null);
            g.dispose();

            String dstPath = String.format(pathConfig.getTwittsPicsPath() + "//%s.PNG", newTwitt.getTwittUUID());

            ImageIO.write(resizedBuffImg, "PNG", new File(dstPath));
            newTwitt.setPicAddress(dstPath);
        }
    }

    public void setType(String type){
        newTwitt.setType(type);
    }
    private void setNewTwittDate() {
        DateTime dateTime = new DateTime();
        String now  = dateTime.Now();
        newTwitt.setDate(now);
    }

    private void setNewTwittUUID() {
        String uuid = UUID.randomUUID().toString();
        newTwitt.setTwittUUID(uuid);
    }

    public void setNewTwittAuthor() throws SQLException, IOException, ClassNotFoundException {
        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();
        newTwitt.setAuthor(mainUser);
        newTwitt.setAuthorUUID(mainUser.getUserUUID());
    }


}
