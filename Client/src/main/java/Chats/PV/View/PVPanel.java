package Chats.PV.View;

import Chats.Common.Message.View.MessagePanel;
import Chats.PV.Listener.SendMessageListener;
import Chats.PV.Listener.UpOrDownBtnListener;
import Chats.PV.Model.PV;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import MainFrame.View.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PVPanel extends JPanel implements ActionListener {
    PV pv;
    MainPanel mainPanel;

    public static PVPanel instance = null;

    JLabel picLable;

    MessagePanel messagePanel;
    JButton upBtn;
    JButton downBtn;
    JButton deleteBtn;
    JButton editBtn;
    JButton backBtn;
    JButton exitBtn;
    JButton chooseImageBtn;
    JButton deleteImageBtn;

    JTextArea messageField;
    JTextArea editField;

    JButton sendMessageBtn;

    SendMessageListener sendMessageListener;
    UpOrDownBtnListener upOrDownBtnListener;

    int messageNumber;

    JFileChooser picChooser;

    String picPath;

    public PVPanel(PV pv, MainPanel mainPanel) throws IOException {
        this.pv = pv;
        this.mainPanel = mainPanel;

        messageNumber = Math.max(pv.getMessages().size() - 1, 0);

        this.setLayout(null);

        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBounds(0, 0, frameConfig.getWidth(), frameConfig.getHeight());
        this.setBackground(colorConfig.getColor01());
        this.setVisible(true);

        if (pv.getMessages().size() > 0) {
            messagePanel = new MessagePanel(pv.getMessages().get(messageNumber));
        } else {
            messagePanel = new MessagePanel();
        }

        upBtn = new JButton();
        upBtn.setBounds(0, 0, 300, 20);
        upBtn.setText("up");
        upBtn.addActionListener(this);

        downBtn = new JButton();
        downBtn.setBounds(0, 420, 300, 20);
        downBtn.setText("down");
        downBtn.addActionListener(this);

        deleteBtn = new JButton();
        deleteBtn.setBounds(400, 300, 80, 40);
        deleteBtn.setText("delete");
        deleteBtn.addActionListener(this);
        deleteBtn.setVisible(true);

        editBtn = new JButton();
        editBtn.setBounds(310, 300, 80, 40);
        editBtn.setText("edit");
        editBtn.addActionListener(this);

        editField = new JTextArea();
        editField.setBounds(310, 350, 160, 40);
        editField.setVisible(true);


        if (pv.getMessages().size() > 0) {
            deleteBtn.setEnabled(pv.getUser().getUserUUID().equals(pv.getMessages().get(messageNumber).getAuthor().getUserUUID()));
            editBtn.setEnabled(pv.getUser().getUserUUID().equals(pv.getMessages().get(messageNumber).getAuthor().getUserUUID()));
        } else {
            deleteBtn.setEnabled(false);
            editBtn.setEnabled(false);
        }

        sendMessageBtn = new JButton();
        sendMessageBtn.setBounds(10, 450, 80, 40);
        sendMessageBtn.setText("send");
        sendMessageBtn.addActionListener(this);

        chooseImageBtn = new JButton();
        chooseImageBtn.setBounds(395, 450, 80, 20);
        chooseImageBtn.setText("Image");
        chooseImageBtn.addActionListener(this);

        deleteImageBtn = new JButton();
        deleteImageBtn.setBounds(395, 470, 80, 20);
        deleteImageBtn.setText("delete");
        deleteImageBtn.addActionListener(this);

        messageField = new JTextArea();
        messageField.setBounds(100, 450, 290, 40);
        messageField.setVisible(true);

        backBtn = new JButton();
        backBtn.setBounds(10, 500, 100, 40);
        backBtn.setText("back");
        backBtn.addActionListener(this);

        exitBtn = new JButton();
        exitBtn.setBounds(375, 500, 100, 40);
        exitBtn.setText("exit");
        exitBtn.addActionListener(this);

        picLable = new JLabel();
        picLable.setVisible(true);
        picLable.setBounds(315, 100, 150, 150);

        this.add(upBtn);
        this.add(downBtn);
        this.add(messagePanel);
        this.add(backBtn);
        this.add(deleteBtn);
        this.add(messageField);
        this.add(sendMessageBtn);
        this.add(chooseImageBtn);
        this.add(picLable);
        this.add(editBtn);
        this.add(editField);
        this.add(exitBtn);
        this.add(deleteImageBtn);

        instance = this;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backBtn){
            mainPanel.back();
        }
    }
}
