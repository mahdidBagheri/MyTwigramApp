package ServerSignup.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerRequest;
import ServerSignup.Controller.ServerSignupController;

import java.io.IOException;
import java.sql.SQLException;

public class ServerSignupListener {

    ServerConnection serverConnection;

    public ServerSignupListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }


    public void listen(ClientRequest clientRequest) throws SQLException, IOException {
        ServerSignupController signupController = new ServerSignupController(serverConnection);

        if(clientRequest.getCommand().equals("validate email")){
            boolean isValidEmail = signupController.validateEmail(clientRequest.getClientPayLoad().getStringStringHashMap().get("email"));
            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(), Boolean.toString(isValidEmail),null);
            serverConnection.execute(serverRequest);
        }
        else if(clientRequest.getCommand().equals("validate username")){
            boolean isValidUserName = signupController.validateUserName(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(), Boolean.toString(isValidUserName), null);
            serverConnection.execute(serverRequest);
        }
        /*
        else if(clientRequest.getCommand().equals("signup")){
            User user = new User();
            user.setUsername(clientRequest.getUsername());
            user.setPassword(clientRequest.getPassword());
            user.setEmail(clientRequest.getClientPayLoad().getStringStringHashMap().get("email"));
            signupController.signupUser(user);
        }
         */
    }
}
