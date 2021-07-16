package User.Events;

import User.Model.User;

public class UserEvent {
    User user;
    String command;

    public UserEvent(User user, String command) {
        this.user = user;
        this.command = command;
    }

    public User getUser() {
        return user;
    }

    public String getCommand() {
        return command;
    }
}
