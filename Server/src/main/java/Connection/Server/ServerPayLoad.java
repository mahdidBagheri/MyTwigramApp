package Connection.Server;

import Chats.PV.Model.PV;
import TimeLine.Model.TimeLine;
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
}
