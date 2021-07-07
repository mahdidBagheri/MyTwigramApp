package ClientLogin.View;

import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginPanel extends JPanel implements ActionListener {

    JLabel UserNameLable ;
    JTextArea UserNameText;

    JLabel PasswordLable ;
    JTextArea PasswordText;

    JButton LogInBotton;
    JButton exitBtn;


    public LoginPanel() throws IOException {
        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor01());
        this.setLayout(null);
        this.setBounds(0,80,(int)(frameConfig.getWidth()),(int)frameConfig.getHeight()-80);


        UserNameLable = new JLabel();
        UserNameLable.setBounds(50,50,150,20);
        UserNameLable.setText("Insert your UserName*:");
        UserNameLable.setVisible(true);

        UserNameText = new JTextArea();
        UserNameText.setBounds(50,80,150,40);
        UserNameText.setVisible(true);

        PasswordLable = new JLabel();
        PasswordLable.setBounds(300,50,150,20);
        PasswordLable.setText("Insert your Password*:");
        PasswordLable.setVisible(true);

        PasswordText = new JTextArea();
        PasswordText.setBounds(300,80,150,40);
        PasswordText.setVisible(true);


        LogInBotton = new JButton("LogIn!");
        LogInBotton.setText("LogIn!");
        LogInBotton.setFocusable(false);
        LogInBotton.setBounds(200,200,110,50);
        LogInBotton.addActionListener(this);

        exitBtn = new JButton("Exit!");
        exitBtn.setText("Exit!");
        exitBtn.setFocusable(false);
        exitBtn.setBounds(380,410,80,40);
        exitBtn.addActionListener(this);


        this.add(UserNameLable);
        this.add(UserNameText);
        this.add(PasswordLable);
        this.add(PasswordText);
        this.add(LogInBotton);
        this.add(exitBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
