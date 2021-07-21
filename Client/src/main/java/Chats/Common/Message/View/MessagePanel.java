package Chats.Common.Message.View;


import Chats.Common.Message.Model.Message;
import Config.ColorConfig.ColorConfig;

import javax.swing.*;
import java.io.IOException;

public class MessagePanel extends JPanel {
    JLabel messageTextLbl;
    JLabel messageDateLbl;
    JLabel authurLbl;
    JLabel imageLbl;

    Message message;
    public MessagePanel(Message message) throws IOException {
        this.message = message;
        this.setLayout(null);
        ColorConfig colorConfig = new ColorConfig();

        this.setBounds(0, 20, 300, 400);
        this.setBackground(colorConfig.getColor03());
        this.setVisible(true);

        authurLbl = new JLabel();
        authurLbl.setBounds(10,30,100,30);
        authurLbl.setText("author: " + this.message.getAuthor().getUserName());
        authurLbl.setVisible(true);

        messageTextLbl = new JLabel();
        messageTextLbl.setBounds(10,80,200,200);
        messageTextLbl.setText(this.message.getText());
        messageTextLbl.setVisible(true);

        messageDateLbl = new JLabel();
        messageDateLbl.setBounds(10,330,200,30);
        messageDateLbl.setText(this.message.getDate());
        messageDateLbl.setVisible(true);

        imageLbl = new JLabel(this.message.getPic());
        imageLbl.setBounds(140,10,150,150);
        imageLbl.setVisible(true);


        this.add(authurLbl);
        this.add(messageTextLbl);
        this.add(messageDateLbl);
        this.add(imageLbl);

    }

    public MessagePanel() throws IOException {
        this.setLayout(null);
        ColorConfig colorConfig = new ColorConfig();

        this.setBounds(0, 20, 300, 400);
        this.setBackground(colorConfig.getColor03());
        this.setVisible(true);

        authurLbl = new JLabel();
        authurLbl.setBounds(0,0,200,30);
        authurLbl.setText("Start a new Chat :)");
        authurLbl.setVisible(true);

        messageTextLbl = new JLabel();
        messageTextLbl.setBounds(0,30,200,200);
        messageTextLbl.setVisible(true);

        messageDateLbl = new JLabel();
        messageDateLbl.setBounds(0,230,200,30);
        messageDateLbl.setVisible(true);

        imageLbl = new JLabel();
        imageLbl.setBounds(140,10,150,150);
        imageLbl.setVisible(true);


        this.add(authurLbl);
        this.add(messageTextLbl);
        this.add(messageDateLbl);

    }

    public void setMessage(Message message){
        this.message = message;
        authurLbl.setText("author: " + message.getAuthor().getUserName());
        messageTextLbl.setText(message.getText());
        messageDateLbl.setText(message.getDate());
        imageLbl.setIcon(message.getPic());

        authurLbl.revalidate();
        messageTextLbl.revalidate();
        messageDateLbl.revalidate();
        imageLbl.revalidate();
        this.revalidate();

        authurLbl.repaint();
        messageTextLbl.repaint();
        messageDateLbl.repaint();
        imageLbl.repaint();
        this.repaint();

    }

}

