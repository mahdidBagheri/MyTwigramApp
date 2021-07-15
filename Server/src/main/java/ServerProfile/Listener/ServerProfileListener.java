package ServerProfile.Listener;

import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import ServerConfig.PathConfig.PathConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import ServerConstants.*;

public class ServerProfileListener {
    ServerConnection serverConnection;
    public ServerProfileListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws IOException, SQLException {
        if(clientRequest.getCommand().equals("changeProfilePic")){

            PathConfig pathConfig = new PathConfig();
            BufferedImage bImage = null;

            bImage = ImageIO.read(clientRequest.getClientPayLoad().getFile());
            String path = pathConfig.getUsersPicPath() +"//"+ clientRequest.getClientPayLoad().getUser().getUserUUID()+".PNG";
            ImageIO.write(bImage, "PNG", new File(path));
            saveNewProfilePathToDataBase(clientRequest.getUsername(),path);

        }
    }

    private void saveNewProfilePathToDataBase(String username, String path) throws SQLException {
        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
        String sql = String.format("update \"UsersTable\" set \"ProfilePic\" = '%s' where \"UserName\" = '%s';",path,username);
        connectionToDataBase.executeUpdate(sql);
    }
}
