package Chats.Group.View;

import Chats.Common.Message.Events.SendMessageEvent;
import Chats.Common.Message.Model.Message;
import Chats.Common.Message.View.MessagePanel;
import Chats.Group.Listener.SendMessageListener;
import Chats.Group.Listener.UpOrDownBtnListener;
import Chats.Group.Model.Group;
import Chats.Group.Threads.GroupThreadServerListener;
import Chats.PV.Exceptions.MessageSavedAndNotSent;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import MainFrame.View.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class GroupPanel extends JPanel implements ActionListener {
    public static GroupPanel instance = null;
    MainPanel mainPanel;

    JLabel picLable;

    MessagePanel messagePanel;
    JButton upBtn;
    JButton downBtn;
    JButton deleteBtn;
    JButton editBtn;
    JButton backBtn;
    JButton chooseImageBtn;
    JButton deleteImageBtn;
    JButton exitBtn;

    JTextArea messageField;
    JTextArea editField;

    JButton sendMessageBtn;

    SendMessageListener sendMessageListener;
    UpOrDownBtnListener upOrDownBtnListener;

    GroupThreadServerListener groupThreadServerListener;

    Group group;

    int messageNumber;
    JFileChooser picChooser;

    String picPath;

    public GroupPanel(Group group, MainPanel mainPanel) throws Throwable {

        this.group = group;
        this.mainPanel = mainPanel;

        messageNumber = Math.max(group.getMessages().size() - 1, 0);

        this.setLayout(null);
        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBounds(0, 0, frameConfig.getWidth(), frameConfig.getHeight());
        this.setBackground(colorConfig.getColor01());
        this.setVisible(true);
        if (group.getMessages().size() > 0) {
            messagePanel = new MessagePanel(group.getMessages().get(messageNumber),mainPanel);
        } else {
            messagePanel = new MessagePanel(mainPanel);
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
        editBtn.setVisible(true);
        editBtn.setText("edit");
        editBtn.addActionListener(this);

        if (group.getMessages().size() > 0) {
            deleteBtn.setEnabled(group.getMainUser().getUserUUID().equals(group.getMessages().get(messageNumber).getAuthor().getUserUUID()));
            editBtn.setEnabled(group.getMainUser().getUserUUID().equals(group.getMessages().get(messageNumber).getAuthor().getUserUUID()));
        } else {
            deleteBtn.setEnabled(false);
            editBtn.setEnabled(false);
        }


        chooseImageBtn = new JButton();
        chooseImageBtn.setBounds(395, 450, 80, 20);
        chooseImageBtn.setText("image");
        chooseImageBtn.addActionListener(this);


        sendMessageBtn = new JButton();
        sendMessageBtn.setBounds(10, 450, 80, 40);
        sendMessageBtn.setText("send");
        sendMessageBtn.addActionListener(this);

        messageField = new JTextArea();
        messageField.setBounds(100, 450, 290, 40);
        messageField.setVisible(true);

        editField = new JTextArea();
        editField.setBounds(310, 350, 160, 40);
        editField.setVisible(true);

        backBtn = new JButton();
        backBtn.setBounds(10, 500, 100, 40);
        backBtn.setText("back");
        backBtn.addActionListener(this);

        exitBtn = new JButton();
        exitBtn.setBounds(375, 500, 100, 40);
        exitBtn.setText("exit");
        exitBtn.addActionListener(this);
/*
        if(messageNumber > 0){
            picLable = new JLabel(group.getMessages().get(messageNumber).getImage());
        }
        else {
            picLable = new JLabel();
        }

 */
        picLable = new JLabel();
        picLable.setVisible(true);
        picLable.setBounds(315, 100, 150, 150);

        deleteImageBtn = new JButton();
        deleteImageBtn.setBounds(395, 470, 80, 20);
        deleteImageBtn.setText("delete");
        deleteImageBtn.addActionListener(this);

        addAll();

        instance = this;

        this.sendMessageListener = new SendMessageListener(this);
        this.upOrDownBtnListener = new UpOrDownBtnListener(this);

        this.groupThreadServerListener = new GroupThreadServerListener(this);
        groupThreadServerListener.start();
    }

    private void addAll(){
        this.add(upBtn);
        this.add(downBtn);
        this.add(messagePanel);
        this.add(backBtn);
        this.add(deleteBtn);
        this.add(messageField);
        this.add(sendMessageBtn);
        this.add(picLable);
        this.add(chooseImageBtn);
        this.add(editBtn);
        this.add(editField);
        this.add(exitBtn);
        this.add(deleteImageBtn);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backBtn){
            mainPanel.back();
            groupThreadServerListener.setRunning(false);
        }
        else if(e.getSource() == sendMessageBtn){
            SendMessageEvent sendMessageEvent = new SendMessageEvent(messageField.getText());
            try {
                sendMessageListener.listen(sendMessageEvent);
                messageField.setText("");
                JOptionPane.showMessageDialog(this,"message sent");
            } catch (MessageSavedAndNotSent messageSavedAndNotSent) {
                messageSavedAndNotSent.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        else if(e.getSource() == upBtn){
            try {
                upOrDownBtnListener.listen("up");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else if(e.getSource() == downBtn){
            try {
                upOrDownBtnListener.listen("down");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public Group getGroup() {
        return group;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void decreaseMessageNumber() {
        this.messageNumber--;
    }

    public void increaseMessageNumber() {
        this.messageNumber++;
    }

    public void setMessage(Message message) throws IOException {
        this.remove(messagePanel);
        this.messagePanel = new MessagePanel(message,mainPanel);
        add(messagePanel);


        this.revalidate();
        this.repaint();
    }

    public void finalize(){
        groupThreadServerListener.setRunning(false);
    }

    public void updateGroupPanel() throws IOException {
        if(group.getMessages().size() > 0) {
            if(messageNumber > group.getMessages().size() - 1){
                messageNumber = group.getMessages().size() - 1;
            }
            if(messageNumber < 0){
                messageNumber = 0;
            }
            setMessage(group.getMessages().get(messageNumber));
        }
        this.repaint();
    }
}
