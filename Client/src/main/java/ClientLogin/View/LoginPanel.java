package ClientLogin.View;

import ClientLogin.Events.LoginEvent;
import ClientLogin.Exceptions.EmptyFieldException;
import ClientLogin.Listeners.LoginListener;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Exceptions.UserPassNotMatchException;
import MainFrame.View.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class LoginPanel extends JPanel implements ActionListener {

    JLabel userNameLable ;
    JTextArea userNameText;

    JLabel passwordLable ;
    JTextArea passwordText;

    JButton logInBotton;
    JButton exitBtn;

    LoginListener loginListener;

    public LoginPanel(MainPanel mainPanel) throws IOException {
        this.loginListener = new LoginListener(mainPanel);

        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor01());
        this.setLayout(null);
        this.setBounds(0,80,(int)(frameConfig.getWidth()),(int)frameConfig.getHeight()-80);


        userNameLable = new JLabel();
        userNameLable.setBounds(50,50,150,20);
        userNameLable.setText("Insert your UserName*:");
        userNameLable.setVisible(true);

        userNameText = new JTextArea();
        userNameText.setBounds(50,80,150,40);
        userNameText.setVisible(true);

        passwordLable = new JLabel();
        passwordLable.setBounds(300,50,150,20);
        passwordLable.setText("Insert your Password*:");
        passwordLable.setVisible(true);

        passwordText = new JTextArea();
        passwordText.setBounds(300,80,150,40);
        passwordText.setVisible(true);


        logInBotton = new JButton("LogIn!");
        logInBotton.setText("LogIn!");
        logInBotton.setFocusable(false);
        logInBotton.setBounds(200,200,110,50);
        logInBotton.addActionListener(this);

        exitBtn = new JButton("Exit!");
        exitBtn.setText("Exit!");
        exitBtn.setFocusable(false);
        exitBtn.setBounds(380,410,80,40);
        exitBtn.addActionListener(this);


        this.add(userNameLable);
        this.add(userNameText);
        this.add(passwordLable);
        this.add(passwordText);
        this.add(logInBotton);
        this.add(exitBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logInBotton){
            LoginEvent loginEvent = new LoginEvent(userNameText.getText(), passwordText.getText());
            try {
                loginListener.listen(loginEvent);
                System.out.println("successfully loggedin");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (EmptyFieldException emptyFieldException) {
                emptyFieldException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (UserPassNotMatchException userPassNotMatchException) {
                userPassNotMatchException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
