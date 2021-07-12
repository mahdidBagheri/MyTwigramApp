package Connection.Server;

import User.Model.User;

import java.io.Serializable;

public class ServerPayLoad implements Serializable {
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
