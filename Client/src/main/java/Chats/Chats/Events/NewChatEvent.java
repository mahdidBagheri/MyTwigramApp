package Chats.Chats.Events;

import User.Model.User;

public class NewChatEvent {
    User user;
    public NewChatEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
