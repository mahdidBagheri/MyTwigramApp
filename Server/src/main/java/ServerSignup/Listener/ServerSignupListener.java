package ServerSignup.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import ServerSignup.Controller.ServerSignupController;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ServerSignupListener {

    ServerConnection serverConnection;

    public ServerSignupListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }


    public void listen(ClientRequest clientRequest) throws SQLException, IOException, unsuccessfullReadDataFromDatabase {
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

        else if(clientRequest.getCommand().equals("signup")){
            User user = new User();
            user.setUserName(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"));
            user.setPassWord(clientRequest.getClientPayLoad().getStringStringHashMap().get("password"));
            user.setfName(clientRequest.getClientPayLoad().getStringStringHashMap().get("firstName"));
            user.setlName(clientRequest.getClientPayLoad().getStringStringHashMap().get("lastName"));
            user.setEmail(clientRequest.getClientPayLoad().getStringStringHashMap().get("email"));
            user.setBio(clientRequest.getClientPayLoad().getStringStringHashMap().get("bio"));
            user.setPhoneNumber(clientRequest.getClientPayLoad().getStringStringHashMap().get("phoneNumber"));
            user.setBirthDate(clientRequest.getClientPayLoad().getStringStringHashMap().get("birthDate"));
            signupController.signUpUser(user);
        }
        else if(clientRequest.getCommand().equals("userData")){
            User user = new User();
            ServerUserController userController = new ServerUserController(user);
            userController.readAll(clientRequest.getUsername());

            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setUser(user);

            ServerRequest serverRequest = new ServerRequest(null, "userData",serverPayLoad);
            serverConnection.execute(serverRequest);
        }

    }
}
