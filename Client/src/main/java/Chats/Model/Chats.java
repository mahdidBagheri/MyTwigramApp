package Chats.Model;

import User.Model.User;

import java.io.Serializable;

public class Chats implements Serializable {
    User user;
    public void setUser(User user) {
        this.user = user;
    }
}
