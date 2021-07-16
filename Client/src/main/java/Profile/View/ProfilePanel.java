package Profile.View;

import Twitt.Listeners.TwittViewListener;
import User.Listener.UserViewListener;
import Config.ColorConfig.ColorConfig;
import MainFrame.View.MainPanel;
import Profile.Listener.ChangeProfileListener;
import User.Controller.UserController;
import User.Model.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ProfilePanel extends JPanel implements ActionListener {
    MainPanel mainPanel;
    User user;

    private static ProfilePanel instance = null;
    JLabel nameLabel;
    JLabel userNameLabel;
    JLabel emailLabel;
    JLabel bioLabel;
    JLabel phoneNumber;

    JComboBox<String> followersCombo;
    JComboBox<String> followingCombo;
    JComboBox<String> blackListCombo;
    JComboBox<String> savedListCombo;
    JComboBox<String> twittsCombo;

    JButton followersViewBtn;
    JButton followingViewBtn;
    JButton twittListViewBtn;
    JButton blackListViewBtn;
    JButton savedListViewBtn;


    JButton changePicBotton;
    JLabel profilePicLable;

    ImageIcon profilePic;

    JFileChooser picChooser;

    UserViewListener userViewListener;
    TwittViewListener twittViewListener;
    ChangeProfileListener changeProfileListener = new ChangeProfileListener();

    public ProfilePanel(MainPanel mainPanel, User user) throws IOException {
        this.mainPanel = mainPanel;
        this.user = user;

        this.userViewListener = new UserViewListener(mainPanel);
        this.twittViewListener = new TwittViewListener();
        this.changeProfileListener = new ChangeProfileListener();

        ColorConfig colorConfig = new ColorConfig();

        this.setLayout(null);
        this.setBounds(150, 0, 450, 600);
        this.setBackground(colorConfig.getColor01());
        this.setVisible(true);

        nameLabel = new JLabel();
        nameLabel.setText("Name: " + user.getfName() + " " + user.getlName());
        nameLabel.setBounds(10, 35, 300, 20);
        nameLabel.setVisible(true);

        userNameLabel = new JLabel();
        userNameLabel.setText("User Name: " + user.getUserName());
        userNameLabel.setBounds(10, 65, 300, 20);
        userNameLabel.setVisible(true);

        emailLabel = new JLabel();
        emailLabel.setText("Email: " + user.getEmail());
        emailLabel.setBounds(10, 95, 300, 20);
        emailLabel.setVisible(true);

        bioLabel = new JLabel();
        bioLabel.setText("Bio: " + user.getBio());
        bioLabel.setBounds(10, 125, 300, 20);
        bioLabel.setVisible(true);

        phoneNumber = new JLabel();
        phoneNumber.setText("Phone : " + user.getPhoneNumber());
        phoneNumber.setBounds(10, 165, 300, 20);
        phoneNumber.setVisible(true);


        changePicBotton = new JButton("ChangePicBotton");
        changePicBotton.setText("ChangePicBotton");
        changePicBotton.setBounds(160, 175, 150, 40);
        changePicBotton.setVisible(true);
        changePicBotton.addActionListener(this);

        followersCombo = new JComboBox<>();
        getFollowersItems(user);
        followersCombo.setBounds(5, 230, 100, 50);
        followersCombo.setVisible(true);

        followersViewBtn = new JButton("View");
        followersViewBtn.setText("flwer View");
        followersViewBtn.setBounds(5, 280, 100, 15);
        followersViewBtn.setVisible(true);
        followersViewBtn.addActionListener(this);

        blackListCombo = new JComboBox<>();
        getBlackListItems(user);
        blackListCombo.setBounds(215, 230, 100, 50);
        blackListCombo.setVisible(true);

        blackListViewBtn = new JButton("View");
        blackListViewBtn.setText("Blocked View");
        blackListViewBtn.setBounds(215, 280, 100, 15);
        blackListViewBtn.setVisible(true);
        blackListViewBtn.addActionListener(this);


        followingCombo = new JComboBox<>();
        getFollowingItems(user);
        followingCombo.setBounds(110, 230, 100, 50);
        followingCombo.setVisible(true);

        followingViewBtn = new JButton("View");
        followingViewBtn.setText("flwing View");
        followingViewBtn.setBounds(110, 280, 100, 15);
        followingViewBtn.setVisible(true);
        followingViewBtn.addActionListener(this);


        twittsCombo = new JComboBox<>();
        getTwittsItems(user);
        twittsCombo.setBounds(70, 305, 200, 85);
        twittsCombo.setVisible(true);

        twittListViewBtn = new JButton("View");
        twittListViewBtn.setText("Twitts View");
        twittListViewBtn.setBounds(70, 390, 100, 15);
        twittListViewBtn.setVisible(true);
        twittListViewBtn.addActionListener(this);

        savedListCombo = new JComboBox<>();
        getSavedTwittItems(user);
        savedListCombo.setBounds(70, 430, 200, 85);
        savedListCombo.setVisible(true);

        savedListViewBtn = new JButton("View");
        savedListViewBtn.setText("save twitt View");
        savedListViewBtn.setBounds(70, 515, 100, 15);
        savedListViewBtn.setVisible(true);
        savedListViewBtn.addActionListener(this);


        profilePicLable = new JLabel(user.getProfilePic());
        profilePicLable.setVisible(true);
        profilePicLable.setBounds(160, 20, 150, 150);


        this.add(changePicBotton);
        this.add(nameLabel);
        this.add(userNameLabel);
        this.add(emailLabel);
        this.add(bioLabel);
        this.add(followersCombo);
        this.add(followingCombo);
        this.add(twittsCombo);
        this.add(followersViewBtn);
        this.add(followingViewBtn);
        this.add(twittListViewBtn);
        this.add(profilePicLable);
        this.add(blackListCombo);
        this.add(blackListViewBtn);
        this.add(savedListViewBtn);
        this.add(savedListCombo);
        this.add(phoneNumber);

        instance = this;

    }


    private void getSavedTwittItems(User user) {
        for (int i = 0; i < user.getSavedTwitts().size(); i++) {
            savedListCombo.addItem(user.getSavedTwitts().get(i).getText());
        }
    }

    private void getTwittsItems(User user) {
        for (int i = 0; i < user.getTwitts().size(); i++) {
            twittsCombo.addItem(user.getTwitts().get(i).getText());
        }
    }


    private void getFollowingItems(User user) {
        for (int i = 0; i < user.getFollowing().size(); i++) {
            followingCombo.addItem(user.getFollowing().get(i).getUserName());
        }
    }

    private void getBlackListItems(User user) {
        for (int i = 0; i < user.getBlackList().size(); i++) {
            blackListCombo.addItem(user.getBlackList().get(i).getUserName());
        }
    }

    private void getFollowersItems(User user) {
        for (int i = 0; i < user.getFollowers().size(); i++) {
            followersCombo.addItem(user.getFollowers().get(i).getUserName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changePicBotton) {
            try {
                picChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGE FILES", "PNG", "png", "jpg", "jpeg");
                picChooser.setFileFilter(filter);
                int returnValue = picChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = picChooser.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    System.out.println(path);

                    User mainUser = new User();
                    UserController mainUserController = new UserController(mainUser);
                    mainUserController.setAsMain();

                    mainUserController.changeProfilePic(path);

                    instance.remove(profilePicLable);
                    profilePicLable = new JLabel(mainUser.getProfilePic());
                    profilePicLable.setVisible(true);
                    profilePicLable.setBounds(160, 20, 150, 150);
                    instance.add(profilePicLable);

                    instance.revalidate();
                    profilePicLable.revalidate();
                    instance.repaint();
                    profilePicLable.repaint();

                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }
}
