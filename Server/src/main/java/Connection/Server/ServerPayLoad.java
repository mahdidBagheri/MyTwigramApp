package Connection.Server;

import User.Model.User;

import java.io.Serializable;
import java.util.HashMap;

public class ServerPayLoad implements Serializable {
    HashMap<String,String> stringStringHashMap = new HashMap<>();

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
}
