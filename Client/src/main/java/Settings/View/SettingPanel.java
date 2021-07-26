package Settings.View;

import ClientSingup.Exceptions.PasswordsNotMatchException;
import Config.ColorConfig.ColorConfig;
import Connection.Exceptions.CouldNotConnectToServerException;
import MainFrame.View.MainPanel;
import Settings.Events.WrongPasswordException;
import Settings.Listeners.ReconnectListener;
import Settings.Listeners.SettingListener;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class SettingPanel extends JPanel implements ActionListener {
    User mainUser;
    MainPanel mainPanel;

    private static SettingPanel instance = null;
    JButton changePasswordBtn;
    JButton changeStatusBtn;
    JButton changeLastSeenModeBtn;
    JButton changePrivacyBtn;
    JButton deleteAcountBtn;
    JButton changeEmailBtn;
    JButton changePhoneNumberBtn;
    JButton changeBioBtn;
    JButton reconnectBtn;

    JTextArea oldPassField;
    JTextArea pass1Field;
    JTextArea pass2Field;

    JTextArea changeEmailField;
    JTextArea changePhoneNumberField;
    JTextArea changeBioField;

    SettingListener settingListener;
    ReconnectListener reconnectListener;

    public SettingPanel(User user, MainPanel mainPanel) throws IOException {
        this.mainUser = user;
        this.mainPanel = mainPanel;

        ColorConfig colorConfig = new ColorConfig();

        this.setLayout(null);
        this.setBounds(150, 0, 450, 600);
        setBackGroundColor();
        this.setVisible(true);

        changePasswordBtn = new JButton("ChangePassword");
        changePasswordBtn.setText("ChangePassword");
        changePasswordBtn.setBounds(5, 130, 140, 40);
        changePasswordBtn.setVisible(true);
        changePasswordBtn.addActionListener(this);

        reconnectBtn = new JButton("Reconnect");
        reconnectBtn.setText("Reconnect");
        reconnectBtn.setBounds(5, 5, 140, 40);
        reconnectBtn.setVisible(true);
        reconnectBtn.addActionListener(this);

        oldPassField = new JTextArea();
        oldPassField.setBounds(5, 180, 140, 40);
        oldPassField.setVisible(true);

        pass1Field = new JTextArea();
        pass1Field.setBounds(150, 180, 140, 40);
        pass1Field.setVisible(true);

        pass2Field = new JTextArea();
        pass2Field.setBounds(150, 230, 140, 40);
        pass2Field.setVisible(true);


        changeStatusBtn = new JButton("changeStatus");
        changeStatusBtn.setText("changeStatus");
        changeStatusBtn.setBounds(5, 300, 140, 40);
        changeStatusBtn.setVisible(true);
        changeStatusBtn.addActionListener(this);

        changeLastSeenModeBtn = new JButton("changeLastSeenMode");
        changeLastSeenModeBtn.setText("changeLastSeenMode");
        changeLastSeenModeBtn.setBounds(150, 300, 140, 40);
        changeLastSeenModeBtn.setVisible(true);
        changeLastSeenModeBtn.addActionListener(this);

        changePrivacyBtn = new JButton("changePrivacy");
        changePrivacyBtn.setText("changePrivacy");
        changePrivacyBtn.setBounds(5, 350, 140, 40);
        changePrivacyBtn.setVisible(true);
        changePrivacyBtn.addActionListener(this);

        deleteAcountBtn = new JButton("deleteAcount");
        deleteAcountBtn.setText("deleteAcount");
        deleteAcountBtn.setBounds(150, 350, 140, 40);
        deleteAcountBtn.setVisible(true);
        deleteAcountBtn.addActionListener(this);

        changeEmailBtn = new JButton("changeEmail");
        changeEmailBtn.setText("changeEmail");
        changeEmailBtn.setBounds(5, 400, 140, 40);
        changeEmailBtn.setVisible(true);
        changeEmailBtn.addActionListener(this);

        changeEmailField = new JTextArea();
        changeEmailField.setBounds(150, 400, 140, 40);
        changeEmailField.setVisible(true);

        changePhoneNumberBtn = new JButton("changePhoneNumber");
        changePhoneNumberBtn.setText("changePhoneNumber");
        changePhoneNumberBtn.setBounds(5, 450, 140, 40);
        changePhoneNumberBtn.setVisible(true);
        changePhoneNumberBtn.addActionListener(this);

        changePhoneNumberField = new JTextArea();
        changePhoneNumberField.setBounds(150, 450, 140, 40);
        changePhoneNumberField.setVisible(true);

        changeBioBtn = new JButton("changeBio");
        changeBioBtn.setText("changeBio");
        changeBioBtn.setBounds(5, 500, 140, 40);
        changeBioBtn.setVisible(true);
        changeBioBtn.addActionListener(this);

        changeBioField = new JTextArea();
        changeBioField.setBounds(150, 500, 140, 40);
        changeBioField.setVisible(true);

        this.add(changePasswordBtn);
        this.add(changeStatusBtn);
        this.add(changeLastSeenModeBtn);
        this.add(changePrivacyBtn);
        this.add(deleteAcountBtn);
        this.add(oldPassField);
        this.add(pass1Field);
        this.add(pass2Field);
        this.add(changeEmailBtn);
        this.add(changeEmailField);
        this.add(changePhoneNumberBtn);
        this.add(changePhoneNumberField);
        this.add(changeBioBtn);
        this.add(changeBioField);
        this.add(reconnectBtn);

        instance = this;

        this.settingListener = new SettingListener(this);
        this.reconnectListener = new ReconnectListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changePasswordBtn) {
            try {
                if (!pass1Field.getText().equals(pass2Field.getText())) {
                    throw new PasswordsNotMatchException("passwords do not match");
                } else if (!oldPassField.getText().equals(mainUser.getPassWord())) {
                    throw new WrongPasswordException("wrong password");
                } else {
                    mainUser.setPassWord(pass1Field.getText());
                    settingListener.listen(mainUser);
                    setBackGroundColor();
                    clearFields();
                }
            } catch (PasswordsNotMatchException passwordsNotMatchException) {
                passwordsNotMatchException.printStackTrace();
            } catch (WrongPasswordException wrongPasswordException) {
                wrongPasswordException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        } else if (e.getSource() == reconnectBtn) {
            try {
                reconnectListener.listen(mainUser);
                setBackGroundColor();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                JOptionPane.showMessageDialog(this, "could not connect");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        if (e.getSource() == changeEmailBtn) {
            try {

                mainUser.setEmail(changeEmailField.getText());
                settingListener.listen(mainUser);
                setBackGroundColor();
                clearFields();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
        if (e.getSource() == changeBioBtn) {
            try {

                mainUser.setBio(changeBioField.getText());
                settingListener.listen(mainUser);
                setBackGroundColor();
                clearFields();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
        if (e.getSource() == changeLastSeenModeBtn) {
            try {
                if(mainUser.getLastSeenMode().equals("EveryOne")){
                    mainUser.setLastSeenMode("FollowersOnly");
                }
                else if(mainUser.getLastSeenMode().equals("FollowersOnly")){
                    mainUser.setLastSeenMode("NoOne");
                }
                else {
                    mainUser.setLastSeenMode("EveryOne");
                }
                settingListener.listen(mainUser);
                setBackGroundColor();
                clearFields();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
        if (e.getSource() == changePhoneNumberBtn) {
            try {

                mainUser.setPhoneNumber(changePhoneNumberField.getText());
                settingListener.listen(mainUser);
                setBackGroundColor();
                clearFields();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
        if (e.getSource() == changePrivacyBtn) {
            try {
                if(mainUser.getPrivacy().equals("Private")){
                    mainUser.setPrivacy("Public");
                }
                else {
                    mainUser.setPrivacy("Private");
                }
                settingListener.listen(mainUser);
                setBackGroundColor();
                clearFields();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
        if (e.getSource() == changeStatusBtn) {
            try {
                if(mainUser.getStatus().equals("Active")){
                    mainUser.setStatus("inActive");
                }
                else {
                    mainUser.setStatus("Active");
                }
                settingListener.listen(mainUser);
                setBackGroundColor();
                clearFields();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }

    public void clearFields() {
        oldPassField.setText("");
        pass1Field.setText("");
        pass2Field.setText("");
        pass2Field.setText("");
        changeEmailField.setText("");
        changePhoneNumberField.setText("");
        changeBioField.setText("");
    }

    public void setBackGroundColor() throws IOException {
        ColorConfig colorConfig = new ColorConfig();
        if (mainUser.getSync().equals("true")) {
            this.setBackground(colorConfig.getColor01());
        } else {
            this.setBackground(colorConfig.getColor07());
        }
    }
}
