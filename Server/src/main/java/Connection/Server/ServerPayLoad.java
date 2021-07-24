package Connection.Server;


import Chats.Group.Model.Group;
import Chats.PV.Model.PV;
import TimeLine.Model.TimeLine;
import Twitt.Model.Twitt;
import User.Model.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class ServerPayLoad implements Serializable {
    HashMap<String,String> stringStringHashMap = new HashMap<>();
    LinkedList<User> usersRequestedList = new LinkedList<>();

    User user;
    TimeLine timeLine;
    PV pv;
    Group group;
    Twitt twitt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<String, String> getStringStringHashMap() {
        return stringStringHashMap;
    }

    public LinkedList<User> getUsersRequestedList() {
        return usersRequestedList;
    }

    public TimeLine getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(TimeLine timeLine) {
        this.timeLine = timeLine;
    }

    public PV getPv() {
        return pv;
    }

    public void setPv(PV pv) {
        this.pv = pv;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Twitt getTwitt() {
        return twitt;
    }

    public void setTwitt(Twitt twitt) {
        this.twitt = twitt;
    }
}
