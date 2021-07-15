package ServerProfile.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import ServerConfig.PathConfig.PathConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import ServerConstants.*;

public class ServerProfileListener {
    ServerConnection serverConnection;
    public ServerProfileListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws IOException {
        if(clientRequest.getCommand().equals("changeProfilePic")){

            PathConfig pathConfig = new PathConfig();
            BufferedImage bImage = null;

            bImage = ImageIO.read(clientRequest.getClientPayLoad().getFile());

            ImageIO.write(bImage, "PNG", new File(pathConfig.getUsersPicPath() +"//"+ clientRequest.getClientPayLoad().getUser().getUserUUID()+".PNG"));

        }
    }
}
