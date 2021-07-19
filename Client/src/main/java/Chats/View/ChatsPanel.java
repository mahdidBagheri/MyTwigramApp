package Chats.View;

import Chats.Events.NewChatEvent;
import Chats.Listeners.ClientChatViewListener;
import Chats.Listeners.ClientNewChatListener;
import Chats.Model.PV;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import Groups.Model.Group;
import MainFrame.View.MainPanel;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class ChatsPanel extends JPanel implements ActionListener {

    public static ChatsPanel instance = null;

    JComboBox<String> PVsCombo;
    JComboBox<String> groupsCombo;
    JComboBox<String> followersCombo;
    JComboBox<String> newGroupMembersCombo;

    JButton PVVewiBtn;
    JButton groupViewBtn;
    JButton addFollowerBtn;
    JButton newGroupBtn;
    JButton removeAllBtn;
    JButton newChatBtn;

    JTextArea groupNameField;

    MainPanel mainPanel;
    User mainUser;

    ClientChatViewListener chatsMainViewListener;
    ClientNewChatListener newChatListener;

    LinkedList<User> memmbersToAdd = new LinkedList<>();

    public ChatsPanel(MainPanel mainPanel, User mainUser) throws IOException {
        this.mainPanel = mainPanel;
        this.mainUser = mainUser;
        this.chatsMainViewListener = new ClientChatViewListener();
        this.newChatListener = new ClientNewChatListener();

        this.setLayout(null);

        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor01());
        this.setBounds(150,0,frameConfig.getWidth()-150,frameConfig.getHeight());
        this.setVisible(true);


        PVsCombo = new JComboBox<>();
        PVsCombo.setBounds(200,10,100,50);
        insertPVsintoCombo(mainUser.getChats());
        PVsCombo.setVisible(true);

        PVVewiBtn = new JButton();
        PVVewiBtn.setBounds(200,60,100,20);
        PVVewiBtn.setText("PV view");
        PVVewiBtn.setVisible(true);
        PVVewiBtn.addActionListener(this);

        groupsCombo = new JComboBox<>();
        groupsCombo.setBounds(10,10,150,50);
        insertGroupsintoCombo(mainUser.getGroups());
        groupsCombo.setVisible(true);

        groupViewBtn = new JButton();
        groupViewBtn.setBounds(10,60,100,20);
        groupViewBtn.setText("groups view");
        groupViewBtn.setVisible(true);
        groupViewBtn.addActionListener(this);

        followersCombo = new JComboBox<>();
        followersCombo.setBounds(200,200,100,50);
        insertFollowersIntoCombo(mainUser.getFollowers());
        followersCombo.setVisible(true);

        addFollowerBtn = new JButton();
        addFollowerBtn.setBounds(200,250,100,20);
        addFollowerBtn.setText("add member");
        addFollowerBtn.setVisible(true);
        addFollowerBtn.addActionListener(this);

        newChatBtn = new JButton();
        newChatBtn.setBounds(200,270,100,20);
        newChatBtn.setText("new chat");
        newChatBtn.setVisible(true);
        newChatBtn.addActionListener(this);

        groupNameField = new JTextArea();
        groupNameField.setBounds(10, 150, 100,50 );
        groupNameField.setVisible(true);

        newGroupMembersCombo = new JComboBox<>();
        newGroupMembersCombo.setBounds(10,200,100,50);
        newGroupMembersCombo.setVisible(true);

        newGroupBtn = new JButton();
        newGroupBtn.setBounds(10,250,100,20);
        newGroupBtn.setText("add group");
        newGroupBtn.setVisible(true);
        newGroupBtn.addActionListener(this);

        removeAllBtn = new JButton();
        removeAllBtn.setBounds(10,270,100,20);
        removeAllBtn.setText("remove all");
        removeAllBtn.setVisible(true);
        removeAllBtn.addActionListener(this);


        this.add(PVsCombo);
        this.add(PVVewiBtn);
        this.add(groupsCombo);
        this.add(groupViewBtn);
        this.add(followersCombo);
        this.add(addFollowerBtn);
        this.add(newChatBtn);
        this.add(newGroupMembersCombo);
        this.add(newGroupBtn);
        this.add(removeAllBtn);
        this.add(groupNameField);

        instance = this;

    }

    private void insertFollowersIntoCombo(LinkedList<User> followers) {
        followersCombo.removeAllItems();
        for (User follower : followers) {
            followersCombo.addItem(follower.getUserName());
        }
    }

    private void insertGroupsintoCombo(LinkedList<Group> groups) {
        groupsCombo.removeAllItems();
        for(Group group:groups){
            groupsCombo.addItem(group.getGroupName());
        }
    }

    private void insertPVsintoCombo(LinkedList<PV> privateChats) {
        PVsCombo.removeAllItems();
        for (int i = 0; i < privateChats.size(); i++) {
            PVsCombo.addItem(privateChats.get(i).getContact().getUserName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newChatBtn){

            int idx = followersCombo.getSelectedIndex();
            User user = mainUser.getFollowers().get(idx);

            NewChatEvent newChatEvent = new NewChatEvent(user);
            try {
                newChatListener.listen(newChatEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }
}
