package ClientLogin.Events;

public class LoginEvent {
    String username;
    String password;
    public LoginEvent(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
