package MainFrame.View;

import Chats.Group.Model.Group;
import Chats.Group.View.GroupPanel;
import Chats.PV.Model.PV;
import Chats.Chats.View.ChatsPanel;
import Chats.PV.View.PVPanel;
import ClientLogin.View.LoginPanel;
import ClientSearch.View.SearchPanel;
import ClientSingup.View.SignupPanel;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import Options.View.OptionPanel;
import Profile.View.ProfilePanel;
import NewTwitt.View.NewTwittPanel;
import Settings.View.SettingPanel;
import TimeLine.Model.TimeLine;
import TimeLine.View.TimeLinePanel;
import Twitt.Events.TwittViewEvent;
import Twitt.View.TwittPanel;
import User.Events.UserViewEvent;
import User.Model.User;
import User.View.UserPanel;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class MainPanel extends JPanel {

    LinkedList<JPanel> panels = new LinkedList<>();
    LinkedList<JPanel> panelsTrace = new LinkedList<>();


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
        this.panelsTrace.clear();
        this.add(new WelcomePanel(mainPanel));
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
    private void clearPanels() {
        for (int i = 0; i < panels.size(); i++){
            if (panels.get(i) != null){
                this.remove(panels.get(i));
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
        this.add(new SearchPanel(mainPanel));
    }

    public void addUserPanel(UserViewEvent userViewEvent) throws IOException {
        if(panels.size() == 2){
            panelsTrace.add(panels.get(0));
            panelsTrace.add(panels.get(1));
        }
        else {
            panelsTrace.add(panels.getLast());
        }

        this.clear();
        this.add(new UserPanel(userViewEvent));
    }

    public void addTwittPanel(TwittViewEvent twittViewEvent) throws IOException {
        if(panels.size() == 2){
            panelsTrace.add(panels.get(0));
            panelsTrace.add(panels.get(1));
        }
        else {
            panelsTrace.add(panels.getLast());
        }

        this.clear();
        this.add(new TwittPanel(twittViewEvent));
    }


    public void back() {
        clear();
        if(panelsTrace.size() == 2){
            this.add(panelsTrace.pollLast());
            this.add(panelsTrace.pollLast());
        }
        else {
            this.add(panelsTrace.getLast());
            panelsTrace.removeLast();
        }
    }

    public void addNewTwittPanel() throws IOException, SQLException, ClassNotFoundException {
        this.clear();
        this.add(new OptionPanel(mainPanel));
        this.add(new NewTwittPanel(mainPanel));
    }

    public void addTimeLinePanel(TimeLine timeLine, User mainUser) throws IOException {
        this.clear();
        this.add(new OptionPanel(mainPanel));
        this.add(new TimeLinePanel(mainPanel, timeLine, mainUser));
    }

    public void addChatsPanel(User mainUser) throws IOException {
        this.clear();
        this.add(new OptionPanel(mainPanel));
        this.add(new ChatsPanel(mainPanel,mainUser ));
    }

    public void addPVPanel(PV pv) throws Throwable {
        if(panels.size() == 2){
            panelsTrace.add(panels.get(0));
            panelsTrace.add(panels.get(1));
        }
        else {
            panelsTrace.add(panels.getLast());
        }

        this.clear();
        this.add(new PVPanel(pv, mainPanel));
    }

    public void addGroupPanel(Group group) throws Throwable {
        if(panels.size() == 2){
            panelsTrace.add(panels.get(0));
            panelsTrace.add(panels.get(1));
        }
        else {
            panelsTrace.add(panels.getLast());
        }

        this.clear();
        this.add(new GroupPanel(group, mainPanel));
    }


    public void addSettingsPanel(User user) throws IOException {
        if(panels.size() == 2){
            panelsTrace.add(panels.get(0));
            panelsTrace.add(panels.get(1));
        }
        else {
            panelsTrace.add(panels.getLast());
        }

        this.clear();
        this.add(new OptionPanel(mainPanel));
        this.add(new SettingPanel(user,mainPanel));
    }
}
