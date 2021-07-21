package ClientSearch.View;

import Connection.Exceptions.CouldNotConnectToServerException;
import MainFrame.View.MainPanel;
import User.Events.UserViewEvent;
import User.Listener.ClientUserViewListener;
import Config.ColorConfig.ColorConfig;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class ResultPanel extends JPanel implements ActionListener {
    ClientUserViewListener userViewListener;
    JButton goProfileBtn;
    JLabel userNameLable;
    User user;
    JLabel profilePicLable;
    JLabel nameLabel;
    JLabel bioLabel;

    MainPanel mainPanel;

    public ResultPanel(MainPanel mainPanel) throws IOException {
        this.setLayout(null);
        this.setBounds(20, 150, 300,350);
        ColorConfig colorConfig = new ColorConfig();

        this.setBackground(colorConfig.getColor02());
        this.setVisible(true);

        this.mainPanel = mainPanel;

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
        if(e.getSource() == goProfileBtn){
            try {
                UserViewEvent userViewEvent = new UserViewEvent(user,mainPanel);
                userViewListener.listen(userViewEvent);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            }
        }
    }

    public void showNotFound() {
        userNameLable.setText("NOT FOUND!");
        userNameLable.setText(" ");
        user = null;
        profilePicLable.setText(" ");
        nameLabel.setText(" ");
        bioLabel.setText(" ");
        this.repaint();
    }


    public void setUserViewListener(ClientUserViewListener userViewListener) {
        this.userViewListener = userViewListener;
    }
}
