package Chats.Chats;

import Chats.PV.Model.PV;
import Groups.Model.Group;
import User.Model.User;

import java.io.Serializable;
import java.util.LinkedList;

public class Chats implements Serializable {
    User user;
    LinkedList<PV> privateChats = new LinkedList<>();
    LinkedList<Group> groupChats = new LinkedList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void clearPrivateChats(){
        privateChats.clear();
    }

    public void addPrivateChat(PV pv){
        privateChats.add(pv);
    }

    public void clearGroupChats(){
        groupChats.clear();
    }

    public void addGroupChat(Group group){
        groupChats.add(group);
    }

}