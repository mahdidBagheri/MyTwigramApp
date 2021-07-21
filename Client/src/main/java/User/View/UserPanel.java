package User.View;

import Config.ColorConfig.ColorConfig;
import Connection.Exceptions.CouldNotConnectToServerException;
import MainFrame.Listener.BackListener;
import MainFrame.View.MainPanel;
import TimeLine.Listeners.ClientTwittViewListener;
import User.Controller.ClientUserController;
import User.Events.UserEvent;
import User.Events.UserViewEvent;
import User.Exceptions.FollowException;
import User.Listener.ClientUserListener;
import User.Listener.ClientUserViewListener;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class UserPanel extends JPanel implements ActionListener {
    User user;

    JLabel nameLabel;
    JLabel lNameLabel;
    JLabel userNameLabel;
    JLabel emailLabel;
    JLabel bioLable;
    JLabel phoneNumberLbl;
    JLabel lastSeenLbl;

    JComboBox<String> followersCombo;
    JComboBox<String> followingCombo;
    JComboBox<String> twittsCombo;

    JButton followersViewBtn;
    JButton followingViewBtn;
    JButton twittListViewBtn;

    JLabel profilePicLable;

    ImageIcon profilePic;

    JButton backBtn;
    JButton follow_OR_unfollowBtn;
    JButton block_OR_unblockBtn;
    JButton mute_OR_unmuteBtn;
    JButton messageBtn;
    JButton exitBtn;


    ClientUserViewListener userViewListener;
    ClientTwittViewListener twittViewListener;
    ClientUserListener userListener;
    BackListener backListener;

    MainPanel mainPanel;

    public UserPanel(UserViewEvent userViewEvent) throws IOException {
        this.twittViewListener = new ClientTwittViewListener();
        this.userListener = new ClientUserListener(userViewEvent.getMainPanel());
        this.mainPanel = userViewEvent.getMainPanel();
        this.backListener = new BackListener(mainPanel);

        ColorConfig colorConfig = new ColorConfig();

        this.user = userViewEvent.getUser();
        this.setLayout(null);
        this.setBounds(0, 0, 500, 600);
        this.setBackground(colorConfig.getColor01());
        this.setVisible(true);

        nameLabel = new JLabel();
        nameLabel.setVisible(true);
        nameLabel.setBounds(20, 10, 500, 15);
        nameLabel.setText("Name: " + user.getfName() + " " + user.getlName());


        userNameLabel = new JLabel();
        userNameLabel.setVisible(true);
        userNameLabel.setBounds(20, 40, 500, 15);
        userNameLabel.setText("User Name: " + user.getUserName());

        emailLabel = new JLabel();
        emailLabel.setVisible(true);
        emailLabel.setBounds(20, 70, 500, 15);
        if (user.getPrivacy().equals("Private")) {
            emailLabel.setText("Email: " + "Not allowed to show.");
        }
        else {
            emailLabel.setText("Email: " + user.getEmail());
        }

        bioLable = new JLabel();
        bioLable.setVisible(true);
        bioLable.setBounds(20, 100, 500, 15);
        bioLable.setText("Bio: " + user.getBio());

        phoneNumberLbl = new JLabel();
        phoneNumberLbl.setVisible(true);
        phoneNumberLbl.setBounds(20, 130, 500, 15);
        if (user.getPrivacy().equals("Private")) {
            phoneNumberLbl.setText("Phone Number: " + "Not allowed to show.");
        }
        else {
            phoneNumberLbl.setText("Phone Number: " + user.getPhoneNumber());
        }

        lastSeenLbl = new JLabel();
        lastSeenLbl.setVisible(true);
        lastSeenLbl.setBounds(20, 160, 500, 15);
        if(user.getLastSeenMode().equals("EveryOne")){
            lastSeenLbl.setText("Last Seen: "  + user.getLastSeen());
        }
        else if(user.getLastSeenMode().equals("JustFollowing")){
            if(user.isFollowing(userViewEvent.getMainUser().getUserUUID())){
                lastSeenLbl.setText("Last Seen: "  + user.getLastSeen());
            }
            else {
                lastSeenLbl.setText("Last Seen: "  + "recently");
            }
        }
        else {
            lastSeenLbl.setText("Last Seen: "  + "recently");
        }


        backBtn = new JButton();
        backBtn.setBounds(20, 500, 70, 40);
        backBtn.setText("back");
        backBtn.setVisible(true);
        backBtn.addActionListener(this);

        exitBtn = new JButton();
        exitBtn.setBounds(210, 500, 70, 40);
        exitBtn.setText("exit");
        exitBtn.setVisible(true);
        exitBtn.addActionListener(this);


        follow_OR_unfollowBtn = new JButton();
        follow_OR_unfollowBtn.setBounds(20, 190, 120, 40);
        follow_OR_unfollowBtn.setText(userViewEvent.getMainUser().isFollowing(user.getUserName()) ? "unfollow" : "follow");
        follow_OR_unfollowBtn.setVisible(true);
        follow_OR_unfollowBtn.addActionListener(this);

        block_OR_unblockBtn = new JButton();
        block_OR_unblockBtn.setBounds(20, 240, 120, 40);
        block_OR_unblockBtn.setText(userViewEvent.getMainUser().isBlocked(user.getUserUUID()) ? "unblock" : "block");
        block_OR_unblockBtn.setVisible(true);
        block_OR_unblockBtn.addActionListener(this);

        mute_OR_unmuteBtn = new JButton();
        mute_OR_unmuteBtn.setBounds(160, 190, 120, 40);
        mute_OR_unmuteBtn.setText(userViewEvent.getMainUser().isMuted(user.getUserUUID()) ? "unmute" : "mute");
        mute_OR_unmuteBtn.setVisible(true);
        mute_OR_unmuteBtn.addActionListener(this);

        messageBtn = new JButton();
        messageBtn.setBounds(160, 240, 120, 40);
        messageBtn.setText("message");
        messageBtn.setVisible(userViewEvent.getMainUser().isFollowing(user.getUserUUID()));
        messageBtn.addActionListener(this);


        followersCombo = new JComboBox<>();
        getFollowersItems(userViewEvent);
        followersCombo.setBounds(20, 290, 100, 50);
        followersCombo.setVisible(true);

        followersViewBtn = new JButton("flwr View");
        followersViewBtn.setText("View");
        followersViewBtn.setBounds(20, 340, 100, 15);
        followersViewBtn.setVisible(true);
        followersViewBtn.addActionListener(this);

        followingCombo = new JComboBox<>();
        getFollowingsItems(userViewEvent);
        followingCombo.setBounds(180, 290, 100, 50);
        followingCombo.setVisible(true);

        followingViewBtn = new JButton("View");
        followingViewBtn.setText("flwing View");
        followingViewBtn.setBounds(180, 340, 100, 15);
        followingViewBtn.setVisible(true);
        followingViewBtn.addActionListener(this);


        twittsCombo = new JComboBox<>();
        getTwittsItems(userViewEvent);
        twittsCombo.setBounds(50, 380, 200, 85);
        twittsCombo.setVisible(true);

        twittListViewBtn = new JButton("View");
        twittListViewBtn.setText("twitts View");
        twittListViewBtn.setBounds(50, 465, 100, 15);
        twittListViewBtn.setVisible(true);
        twittListViewBtn.addActionListener(this);

        profilePicLable = new JLabel(userViewEvent.getUser().getProfilePic());
        profilePicLable.setVisible(true);
        profilePicLable.setBounds(250, 10, 150, 150);


        this.add(nameLabel);
        this.add(userNameLabel);
        this.add(emailLabel);
        this.add(bioLable);
        this.add(backBtn);
        this.add(follow_OR_unfollowBtn);
        this.add(block_OR_unblockBtn);
        this.add(mute_OR_unmuteBtn);
        this.add(messageBtn);
        this.add(followersCombo);
        this.add(followersViewBtn);
        this.add(followingCombo);
        this.add(followingViewBtn);
        this.add(twittsCombo);
        this.add(twittListViewBtn);
        this.add(profilePicLable);
        this.add(exitBtn);
        this.add(phoneNumberLbl);
        this.add(lastSeenLbl);


    }


    private void getFollowersItems(UserViewEvent userViewEvent) {
        followersCombo.removeAllItems();
        for (int i = 0; i < userViewEvent.getUser().getFollowers().size(); i++) {
            followersCombo.addItem(userViewEvent.getUser().getFollowers().get(i).getUserName());
        }
    }

    private void getFollowingsItems(UserViewEvent userViewEvent) {
        followingCombo.removeAllItems();
        for (int i = 0; i < userViewEvent.getUser().getFollowing().size(); i++) {
            followingCombo.addItem(userViewEvent.getUser().getFollowing().get(i).getUserName());
        }
    }

    private void getTwittsItems(UserViewEvent userViewEvent) {
        twittsCombo.removeAllItems();
        for (int i = 0; i < userViewEvent.getUser().getTwitts().size(); i++) {
            twittsCombo.addItem(userViewEvent.getUser().getTwitts().get(i).getText());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == follow_OR_unfollowBtn){

            try {
                UserEvent userEvent = new UserEvent(user,"followOrUnfollow");
                userListener.listen(userEvent);
                if(follow_OR_unfollowBtn.getText().equals("follow")){
                    follow_OR_unfollowBtn.setText("unfollow");
                    JOptionPane.showMessageDialog(this,"successfully followed");
                }
                else {
                    follow_OR_unfollowBtn.setText("follow");
                    JOptionPane.showMessageDialog(this,"successfully unfollowed");
                }
                updateCombos();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (FollowException followException) {
                followException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            }
        }
        else if(e.getSource() == backBtn){
            backListener.listen();
        }
    }

    private void updateCombos() throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {

        User user = this.user;
        ClientUserController clientUserController = new ClientUserController(user);
        clientUserController.readAllByUsername();

        UserViewEvent userViewEvent = new UserViewEvent(clientUserController.getUser(),mainPanel);

        getFollowersItems(userViewEvent);
        getFollowingsItems(userViewEvent);
        getTwittsItems(userViewEvent);
    }
}
