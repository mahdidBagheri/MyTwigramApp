package Chats.Chats;

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
