package MainFrame.View;

import Config.FrameConfig.FrameConfig;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrameView extends JFrame {
    private static  MainFrameView instance = null;
    private MainPanel mainPanel = new MainPanel();

    public MainFrameView() throws IOException {
        FrameConfig frameConfig = new FrameConfig();
        this.setTitle("Twigram");
        this.setVisible(true);
        this.setSize(new Dimension(frameConfig.getWidth(),frameConfig.getHeight()));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLocation(frameConfig.getFrameXLoc(),frameConfig.getFrameYLoc());
        instance = this;
        this.add(mainPanel);
        mainPanel.addWelcomePanel();



    }
}
