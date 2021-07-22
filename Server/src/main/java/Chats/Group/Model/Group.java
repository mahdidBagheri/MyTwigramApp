package Chats.Group.Model;

import Chats.Common.Message.Model.Message;
import User.Model.User;

import java.util.LinkedList;

public class Group {
    public String groupName;
    public String groupTableAddress;
    public LinkedList<User> memmbers = new LinkedList<>();
    public LinkedList<Message> messages = new LinkedList<>();
    public User mainUser;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupTableAddress() {
        return groupTableAddress;
    }

    public void setGroupTableAddress(String groupTableAddress) {
        this.groupTableAddress = groupTableAddress;
    }

    public LinkedList<User> getMemmbers() {
        return memmbers;
    }

    public void setMemmbers(LinkedList<User> memmbers) {
        this.memmbers = memmbers;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<Message> messages) {
        this.messages = messages;
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }
}

