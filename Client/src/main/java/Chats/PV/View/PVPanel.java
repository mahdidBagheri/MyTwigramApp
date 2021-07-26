package Chats.PV.View;

import Chats.Common.Message.Events.SendMessageEvent;
import Chats.Common.Message.Model.Message;
import Chats.Common.Message.View.MessagePanel;
import Chats.PV.Exceptions.MessageSavedAndNotSent;
import Chats.PV.Listener.SendMessageListener;
import Chats.PV.Listener.UpOrDownBtnListener;
import Chats.PV.Model.PV;
import Chats.PV.Thread.PVThreadServerListener;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import MainFrame.View.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class PVPanel extends JPanel implements ActionListener {
    PV pv;
    MainPanel mainPanel;
    PVThreadServerListener pvThreadServerListener;

    public static PVPanel instance = null;

    JLabel picLable;

    MessagePanel messagePanel;
    JButton upBtn;
    JButton downBtn;
    JButton deleteBtn;
    JButton editBtn;
    JButton linkCopyBtn;
    JButton backBtn;
    JButton refrshBtn;
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

    public PVPanel(PV pv, MainPanel mainPanel) throws Throwable {
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
            messagePanel = new MessagePanel(pv.getMessages().get(messageNumber),mainPanel);
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

        linkCopyBtn = new JButton();
        linkCopyBtn.setBounds(400, 270, 80, 40);
        linkCopyBtn.setText("copyLink");
        linkCopyBtn.addActionListener(this);
        linkCopyBtn.setVisible(true);

        editBtn = new JButton();
        editBtn.setBounds(310, 300, 80, 40);
        editBtn.setText("edit");
        editBtn.addActionListener(this);

        refrshBtn = new JButton();
        refrshBtn.setBounds(310, 250, 80, 40);
        refrshBtn.setText("refresh");
        refrshBtn.addActionListener(this);

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

        addAll();


        instance = this;
        sendMessageListener = new SendMessageListener(pv);
        pvThreadServerListener = new PVThreadServerListener(this);
        pvThreadServerListener.start();
        upOrDownBtnListener = new UpOrDownBtnListener(this);
    }

    public void addAll(){
        this.add(upBtn);
        this.add(downBtn);
        this.add(messagePanel);
        this.add(backBtn);
        this.add(deleteBtn);
        this.add(sendMessageBtn);
        this.add(chooseImageBtn);
        this.add(picLable);
        this.add(editBtn);
        this.add(exitBtn);
        this.add(deleteImageBtn);
        this.add(refrshBtn);
        this.add(messageField);
        this.add(editField);
        this.add(linkCopyBtn);

    }

    public PV getPv() {
        return pv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backBtn){
            pvThreadServerListener.setRunning(false);
            mainPanel.back();
        }
        else if(e.getSource() == sendMessageBtn){
            SendMessageEvent sendMessageEvent = new SendMessageEvent(messageField.getText());
            try {
                sendMessageListener.listen(sendMessageEvent);
                JOptionPane.showMessageDialog(this,"message sent");
                messageField.setText("");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (MessageSavedAndNotSent messageSavedAndNotSent) {
                messageSavedAndNotSent.printStackTrace();
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
        else if(e.getSource() == refrshBtn){
            try {
                refreshConnection();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        else if(e.getSource() == linkCopyBtn){
            StringSelection selection = new StringSelection("@" + pv.getPVTableName());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            JOptionPane.showMessageDialog(this,"copied!");
        }
    }


    public void updatePV() throws IOException {
        if(pv.getMessages().size() > 0) {
            if(messageNumber > pv.getMessages().size() - 1){
                messageNumber = pv.getMessages().size() - 1;
            }
            if(messageNumber < 0){
                messageNumber = 0;
            }
            setMessage(pv.getMessages().get(messageNumber));
        }
        this.repaint();
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void increaseMessageNumber() {
        this.messageNumber++;
    }

    public void decreaseMessageNumber() {
        this.messageNumber--;
    }

    public void setMessage(Message message) throws IOException {
        this.remove(messagePanel);
        this.messagePanel = new MessagePanel(message,mainPanel);
        add(messagePanel);


        this.revalidate();
        this.repaint();
        messageField.setCaretPosition(messageField.getText().length());

    }

    public void refreshConnection() throws Throwable {
        pvThreadServerListener.setTryConnection(true);
    }

    public void finalize(){
        pvThreadServerListener.setRunning(false);
    }


}
