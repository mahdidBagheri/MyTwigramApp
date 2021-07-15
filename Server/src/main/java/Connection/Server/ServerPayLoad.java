package Connection.Server;

import User.Model.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class ServerPayLoad implements Serializable {
    HashMap<String,String> stringStringHashMap = new HashMap<>();
    LinkedList<User> usersRequestedList = new LinkedList<>();

    User user;

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
}
