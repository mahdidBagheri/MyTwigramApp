package Chats.Model;

import User.Model.User;

public class PV {
    User user;
    User contact;
    String PVTableName;

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

    }
}
