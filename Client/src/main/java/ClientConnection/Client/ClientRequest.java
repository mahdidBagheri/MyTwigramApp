package ClientConnection.Client;

import ClientConnection.Client.ClientPayLoad;
import ClientConnection.ClientConnection;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private String source;
    private ClientPayLoad clientPayLoad;
    private String session;
    private String command;
    private String username;
    private String password;

    public ClientRequest(String source, ClientPayLoad clientPayLoad, String session, String command, String username, String password ) {
        this.source = source;
        this.clientPayLoad = clientPayLoad;
        this.session = session;
        this.command = command;
        this.username = username;
        this.password = password;
    }

    public String getSource() {
        return source;
    }

    public ClientPayLoad getClientPayLoad() {
        return clientPayLoad;
    }

    public String getSession() {
        return session;
    }

    public String getCommand() {
        return command;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
