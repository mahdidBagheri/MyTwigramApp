package MainFrame.View;

import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import MainFrame.Events.LoginViewEvent;
import MainFrame.Events.SignupViewEvent;
import MainFrame.Listener.LoginViewListener;
import MainFrame.Listener.SignupViewListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TopPanel extends JPanel implements ActionListener {

    MainPanel mainPanel;

    JButton loginBtn;
    JButton signupBtn;

    LoginViewListener loginViewListener;
    SignupViewListener signupViewListener;


    public TopPanel(MainPanel mainPanel) throws IOException {

        this.mainPanel = mainPanel;
        loginViewListener = new LoginViewListener(mainPanel);
        signupViewListener = new SignupViewListener(mainPanel);

        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor04());
        this.setLayout(null);
        this.setBounds(0,0,(int)(frameConfig.getWidth()),80);

        loginBtn = new JButton();
        loginBtn.setBounds(10,10,100,60);
        loginBtn.setText("Login");
        loginBtn.setVisible(true);
        loginBtn.addActionListener(this);

        signupBtn = new JButton();
        signupBtn.setBounds(120,10,100,60);
        signupBtn.setText("Signup");
        signupBtn.setVisible(true);
        signupBtn.addActionListener(this);

        this.add(loginBtn);
        this.add(signupBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginBtn){
            LoginViewEvent loginViewEvent = new LoginViewEvent(mainPanel);
            try {
                loginViewListener.listen(loginViewEvent);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else if(e.getSource() == signupBtn){
            SignupViewEvent signupViewEvent = new SignupViewEvent(mainPanel);
            try {
                signupViewListener.listen(signupViewEvent);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
