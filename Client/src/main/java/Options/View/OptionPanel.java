package Options.View;

import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import MainFrame.View.MainPanel;
import Options.Listener.OptionsListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class OptionPanel extends JPanel implements ActionListener {
    MainPanel mainPanel;

    JButton settingBotton;
    JButton notificationsBtn;
    JButton chatsBtn;
    JButton timeLineBotton;
    JButton newTwittBotton;
    JButton profileBotton;
    JButton searchBotton;
    JButton logOut;
    JButton exitBtn;

    OptionsListener optionsListener;

    public OptionPanel(MainPanel mainPanel) throws IOException {
        this.mainPanel = mainPanel;
        optionsListener = new OptionsListener(mainPanel);

        FrameConfig frameConfig = new FrameConfig();
        ColorConfig colorConfig = new ColorConfig();

        this.setLayout(null);
        this.setBounds(0,0,150,frameConfig.getHeight());
        this.setBackground(colorConfig.getColor03());

        profileBotton = new JButton("profileBotton");
        profileBotton.setText("Profile");
        profileBotton.setBounds(5,10,140,40);
        profileBotton.setVisible(true);
        profileBotton.addActionListener(this);

        timeLineBotton = new JButton("timeLineBotton");
        timeLineBotton.setText("TimeLine");
        timeLineBotton.setBounds(5,60,140,40);
        timeLineBotton.setVisible(true);
        timeLineBotton.addActionListener(this);

        settingBotton = new JButton("settingBotton");
        settingBotton.setText("Setting");
        settingBotton.setBounds(5,110,140,40);
        settingBotton.setVisible(true);
        settingBotton.addActionListener(this);

        notificationsBtn = new JButton("Notification");
        notificationsBtn.setText("Notification");
        notificationsBtn.setBounds(5,160,140,40);
        notificationsBtn.setVisible(true);
        notificationsBtn.addActionListener(this);

        chatsBtn = new JButton("Chats");
        chatsBtn.setText("Chats");
        chatsBtn.setBounds(5,210,140,40);
        chatsBtn.setVisible(true);
        chatsBtn.addActionListener(this);

        newTwittBotton = new JButton("newTwittBotton");
        newTwittBotton.setText("New Twitt");
        newTwittBotton.setBounds(5,260,140,40);
        newTwittBotton.setVisible(true);
        newTwittBotton.addActionListener(this);

        searchBotton = new JButton("searchButton");
        searchBotton.setText("Search");
        searchBotton.setBounds(5,310,140,40);
        searchBotton.setVisible(true);
        searchBotton.addActionListener(this);


        logOut = new JButton("LogOut");
        logOut.setText("LogOut");
        logOut.setBounds(5,450,140,40);
        logOut.setVisible(true);
        logOut.addActionListener(this);

        exitBtn = new JButton("exitBtn");
        exitBtn.setText("Exit");
        exitBtn.setBounds(5,500,140,40);
        exitBtn.setVisible(true);
        exitBtn.addActionListener(this);


        this.add(profileBotton);
        this.add(timeLineBotton);
        this.add(settingBotton);
        this.add(notificationsBtn);
        this.add(chatsBtn);
        this.add(searchBotton);
        this.add(newTwittBotton);
        this.add(logOut);
        this.add(exitBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == profileBotton){
            try {
                optionsListener.listen("profileBtn");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }
}
