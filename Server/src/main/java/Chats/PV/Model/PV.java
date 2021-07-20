package Chats.PV.Model;

import Bot.Model.Bot;
import Chats.Common.Message.Model.Message;
import User.Model.User;

import java.io.Serializable;
import java.util.LinkedList;

public class PV implements Serializable {
    User user;
    User contact;
    String PVTableName;
    LinkedList<Message> messages= new LinkedList<>();
    Bot bot;

    public void setUser(User user) {
        this.user = user;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    public void setPVTableName(String PVTableName) {
        this.PVTableName = PVTableName;
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public User getUser() {
        return user;
    }

    public User getContact() {
        return contact;
    }

    public String getPVTableName() {
        return PVTableName;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public Bot getBots() {
        return this.bot;
    }


}
