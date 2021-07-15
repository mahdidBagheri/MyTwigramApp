package ClientSearch.View;

import ClientSearch.Listeners.ViewUserListener;
import Config.ColorConfig.ColorConfig;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ResultPanel extends JPanel implements ActionListener {
    ViewUserListener viewUserListener;
    JButton goProfileBtn;
    JLabel userNameLable;
    User user;
    JLabel profilePicLable;
    JLabel nameLabel;
    JLabel bioLabel;

    public ResultPanel() throws IOException {
        this.setLayout(null);
        this.setBounds(20, 150, 300,350);
        ColorConfig colorConfig = new ColorConfig();

        this.setBackground(colorConfig.getColor02());
        this.setVisible(true);

    }


    public void setUser(User user) throws IOException {
        this.user = user;

        this.setLayout(null);
        this.setBounds(20, 150, 300,350);
        ColorConfig colorConfig = new ColorConfig();

        this.setBackground(colorConfig.getColor02());
        this.setVisible(true);

        userNameLable = new JLabel();
        userNameLable.setVisible(true);
        userNameLable.setBounds(70,150,200,100);
        userNameLable.setText("UserName : @" + user.getUserName());

        nameLabel = new JLabel();
        nameLabel.setVisible(true);
        nameLabel.setBounds(70,170,200,100);
        nameLabel.setText("Name : " + user.getfName() + " " + user.getlName());


        bioLabel = new JLabel();
        bioLabel.setVisible(true);
        bioLabel.setBounds(70,190,200,100);
        bioLabel.setText("Bio : " + user.getBio());


        goProfileBtn = new JButton();
        goProfileBtn.setVisible(true);
        goProfileBtn.setText("Visit");
        goProfileBtn.setBounds(200,300,80,40);
        goProfileBtn.addActionListener(this);

        profilePicLable = new JLabel(user.getProfilePic());
        profilePicLable.setVisible(true);
        profilePicLable.setBounds(70, 10, 150, 150);

        this.add(userNameLable);
        this.add(goProfileBtn);
        this.add(profilePicLable);
        this.add(nameLabel);
        this.add(bioLabel);

        this.repaint();
        this.revalidate();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void showNotFound() {
        userNameLable.setText("NOT FOUND!");
        this.repaint();
    }
}
