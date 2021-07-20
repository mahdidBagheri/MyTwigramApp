package Chats.PV.Events;

public class ClientPVViewEvent {
    String username;

    public ClientPVViewEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
