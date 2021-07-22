package Chats.Chats.View;

import Chats.Group.Listener.AddMemmberListener;
import Chats.PV.Listener.ClientNewChatListener;
import Chats.Chats.Events.NewChatEvent;
import Chats.PV.Events.ClientPVViewEvent;
import Chats.PV.Exceptions.ExistingPVException;
import Chats.PV.Listener.ClientPVViewListener;
import Chats.PV.Model.PV;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import Connection.Exceptions.CouldNotConnectToServerException;
import Groups.Model.Group;
import MainFrame.View.MainPanel;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;

public class ChatsPanel extends JPanel implements ActionListener {

    public static ChatsPanel instance = null;

    JComboBox<String> pVsCombo;
    JComboBox<String> groupsCombo;
    JComboBox<String> followersCombo;
    JComboBox<String> newGroupMembersCombo;

    JButton pVVewiBtn;
    JButton groupViewBtn;
    JButton addFollowerBtn;
    JButton newGroupBtn;
    JButton removeAllBtn;
    JButton newChatBtn;

    JTextArea groupNameField;

    MainPanel mainPanel;
    User mainUser;

    ClientNewChatListener newChatListener;
    ClientPVViewListener clientPVViewListener;
    AddMemmberListener addMemmberListener;

    LinkedList<User> memmbersToAdd = new LinkedList<>();

    public ChatsPanel(MainPanel mainPanel, User mainUser) throws IOException {
        this.mainPanel = mainPanel;
        this.mainUser = mainUser;
        this.newChatListener = new ClientNewChatListener(this);
        this.clientPVViewListener = new ClientPVViewListener(mainPanel);
        this.addMemmberListener = new AddMemmberListener(this);

        this.setLayout(null);

        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor01());
        this.setBounds(150, 0, frameConfig.getWidth() - 150, frameConfig.getHeight());
        this.setVisible(true);


        pVsCombo = new JComboBox<>();
        pVsCombo.setBounds(200, 10, 100, 50);
        insertPVsintoCombo(mainUser.getChats());
        pVsCombo.setVisible(true);

        pVVewiBtn = new JButton();
        pVVewiBtn.setBounds(200, 60, 100, 20);
        pVVewiBtn.setText("PV view");
        pVVewiBtn.setVisible(true);
        pVVewiBtn.addActionListener(this);

        groupsCombo = new JComboBox<>();
        groupsCombo.setBounds(10, 10, 150, 50);
        insertGroupsintoCombo(mainUser.getGroups());
        groupsCombo.setVisible(true);

        groupViewBtn = new JButton();
        groupViewBtn.setBounds(10, 60, 100, 20);
        groupViewBtn.setText("groups view");
        groupViewBtn.setVisible(true);
        groupViewBtn.addActionListener(this);

        followersCombo = new JComboBox<>();
        followersCombo.setBounds(200, 200, 100, 50);
        insertFollowersIntoCombo(mainUser.getFollowers());
        followersCombo.setVisible(true);

        addFollowerBtn = new JButton();
        addFollowerBtn.setBounds(200, 250, 100, 20);
        addFollowerBtn.setText("add member");
        addFollowerBtn.setVisible(true);
        addFollowerBtn.addActionListener(this);

        newChatBtn = new JButton();
        newChatBtn.setBounds(200, 270, 100, 20);
        newChatBtn.setText("new chat");
        newChatBtn.setVisible(true);
        newChatBtn.addActionListener(this);

        groupNameField = new JTextArea();
        groupNameField.setBounds(10, 150, 100, 50);
        groupNameField.setVisible(true);

        newGroupMembersCombo = new JComboBox<>();
        newGroupMembersCombo.setBounds(10, 200, 100, 50);
        newGroupMembersCombo.setVisible(true);

        newGroupBtn = new JButton();
        newGroupBtn.setBounds(10, 250, 100, 20);
        newGroupBtn.setText("add group");
        newGroupBtn.setVisible(true);
        newGroupBtn.addActionListener(this);

        removeAllBtn = new JButton();
        removeAllBtn.setBounds(10, 270, 100, 20);
        removeAllBtn.setText("remove all");
        removeAllBtn.setVisible(true);
        removeAllBtn.addActionListener(this);


        this.add(pVsCombo);
        this.add(pVVewiBtn);
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
        for (Group group : groups) {
            groupsCombo.addItem(group.getGroupName());
        }
    }

    private void insertPVsintoCombo(LinkedList<PV> privateChats) {
        pVsCombo.removeAllItems();
        for (int i = 0; i < privateChats.size(); i++) {
            pVsCombo.addItem(privateChats.get(i).getContact().getUserName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newChatBtn) {

            int idx = followersCombo.getSelectedIndex();
            User user = mainUser.getFollowers().get(idx);
            try {
                NewChatEvent newChatEvent = new NewChatEvent(user);
                newChatListener.listen(newChatEvent);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            } catch (ExistingPVException existingPVException) {
                existingPVException.printStackTrace();
            }
        } else if (e.getSource() == pVVewiBtn) {
            int idx = pVsCombo.getSelectedIndex();
            String username = mainUser.getChats().get(idx).getContact().getUserName();
            ClientPVViewEvent clientPVViewEvent = new ClientPVViewEvent(username);
            try {
                clientPVViewListener.listen(clientPVViewEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        } else if (e.getSource() == addFollowerBtn) {
            try {
                String username = Objects.requireNonNull(getFollowersCombo().getSelectedItem()).toString();
                addMemmberListener.listen(username);
            } catch (ExistingPVException existingPVException) {
                existingPVException.printStackTrace();
            }
        } else if(e.getSource() == removeAllBtn){
            newGroupMembersCombo.removeAllItems();
        }
    }




    public JComboBox<String> getFollowersCombo() {
        return followersCombo;
    }

    public JComboBox<String> getNewGroupMembersCombo() {
        return newGroupMembersCombo;
    }

    public User getMainUser() {
        return mainUser;
    }

    public JComboBox<String> getpVsCombo() {
        return pVsCombo;
    }

    public JComboBox<String> getGroupsCombo() {
        return groupsCombo;
    }
}
