package MainFrame.View;

import ClientLogin.View.LoginPanel;
import ClientSearch.View.SearchPanel;
import ClientSingup.View.SignupPanel;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import Options.View.OptionPanel;
import Profile.View.ProfilePanel;
import User.Model.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

public class MainPanel extends JPanel {

    LinkedList<JPanel> panels = new LinkedList<>();
    LinkedList<JPanel> panelsTail = new LinkedList<>();


    public static MainPanel mainPanel = null;

    public MainPanel() throws IOException {
        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor02());
        this.setLayout(null);
        this.setBounds(0,0,(int)(frameConfig.getWidth()),(int)frameConfig.getHeight());

        mainPanel = this;
    }



    public void addWelcomePanel() throws IOException {
        this.clear();
        this.add(new WelcomePanel());
        this.add(new TopPanel(mainPanel));
    }

    public void addLoginPanel() throws IOException {
        this.clear();
        this.add(new TopPanel(mainPanel));
        this.add(new LoginPanel(mainPanel));
    }

    public void addSignupPanel() throws IOException {
        this.clear();
        this.add(new TopPanel(mainPanel));
        this.add(new SignupPanel(mainPanel));
    }

    public void addProfilePanel(User user) throws IOException {
        this.clear();
        this.add(new OptionPanel(mainPanel));
        int a = 0;
        this.add(new ProfilePanel(mainPanel, user));
        int b = 0;
    }

    private void clear() {
        for (int i = 0; i < panels.size(); i++){
            if (panels.get(i) != null){
                this.remove(panels.get(i));
                panels.remove(panels.get(i));
                this.repaint();
                i--;
            }
        }

    }

    public void add(JPanel jPanel){
        super.add(jPanel);
        this.repaint();
        panels.add(jPanel);
    }


    public void addSearchPanel() throws IOException {
        this.clear();
        this.add(new OptionPanel(mainPanel));
        int a = 0;
        this.add(new SearchPanel(mainPanel));
        int b = 0;
    }
}
