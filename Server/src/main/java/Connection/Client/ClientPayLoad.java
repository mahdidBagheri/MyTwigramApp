package Connection.Client;

import Chats.Group.Model.Group;
import Twitt.Model.Twitt;
import User.Model.User;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class ClientPayLoad implements Serializable {

    HashMap<String,String> stringStringHashMap = new HashMap<>();
    User user;
    Twitt twitt;
    File file;
    Group group;

    public HashMap<String, String> getStringStringHashMap() {
        return stringStringHashMap;
    }
    public void setStringStringHashMap(HashMap<String, String> stringMap) {
        this.stringStringHashMap = stringMap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Twitt getTwitt() {
        return twitt;
    }

    public void setTwitt(Twitt twitt) {
        this.twitt = twitt;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
