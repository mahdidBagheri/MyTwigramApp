package MainFrame.View;

import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;

import javax.swing.*;
import java.io.IOException;

public class WelcomePanel extends JPanel {

    JLabel welcomeText;
    public WelcomePanel() throws IOException {
        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor03());
        this.setLayout(null);
        this.setBounds(0,79,(int)(frameConfig.getWidth()),(int)frameConfig.getHeight()-80);

        welcomeText = new JLabel();
        welcomeText.setText("salam @ali hale@khoobe besalamati");
        welcomeText.setBounds(170,20,400,20);
        welcomeText.setVisible(true);



        this.add(welcomeText);
    }
}
