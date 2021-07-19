package Twitt.Controller;

import Config.PathConfig.PathConfig;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Constants.Constants;
import Twitt.Model.Twitt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TwittsController {
    Twitt twitt;
    public TwittsController(Twitt twitt) {
        this.twitt = twitt;
    }

    public void readText() {
    }

    public void readReports() {
    }

    public void deleteTwitt() {
    }

    public void saveAndSetStreamedPic() throws IOException {
        if(twitt.getPic() != null) {

            PathConfig pathConfig = new PathConfig();

            BufferedImage bImage = null;

            File initialImage = twitt.getPicFile();
            bImage = ImageIO.read(initialImage);

            int type = bImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bImage.getType();

            BufferedImage resizedBuffImg = new BufferedImage(Constants.picWidth, Constants.picHeight, type);
            Graphics2D g = resizedBuffImg.createGraphics();
            g.drawImage(bImage, 0, 0, Constants.picWidth, Constants.picHeight, null);
            g.dispose();

            String dstPath = String.format(pathConfig.getTwittsPicsPath() + "/%s.PNG", twitt.getTwittUUID());

            ImageIO.write(resizedBuffImg, "PNG", new File(dstPath));
            File file = new File(dstPath);
            while (!file.exists()){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            }
            twitt.setPicAddress(dstPath);
            twitt.setPicFile(file);
            twitt.setPic(new ImageIcon(dstPath));
            int a = 0;
        }
    }
}
