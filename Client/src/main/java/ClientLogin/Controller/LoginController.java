package ClientLogin.Controller;

import ClientLogin.Events.LoginEvent;
import ClientLogin.Exceptions.EmptyFieldException;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Exceptions.UserPassNotMatchException;
import Connection.Server.ServerRequest;
import LocalDataBase.ConnectionToLocalDataBase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class LoginController {

    ConnectionToLocalDataBase connectionToLocalDataBase;

    public LoginController() throws SQLException, IOException, ClassNotFoundException {
        this.connectionToLocalDataBase = new ConnectionToLocalDataBase();
    }

    public void validateLogin(LoginEvent loginEvent) throws EmptyFieldException, IOException {
        if(loginEvent.getUsername().isEmpty()){
            throw new EmptyFieldException("username is empty");
        }
        if(loginEvent.getPassword().isEmpty()){
            throw new EmptyFieldException("password is empty");
        }


    }

    public void loginUser(LoginEvent loginEvent) throws IOException, ClassNotFoundException, UserPassNotMatchException, SQLException, CouldNotConnectToServerException {
        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username",loginEvent.getUsername());
        clientPayLoad.getStringStringHashMap().put("password",loginEvent.getPassword());

        ClientRequest clientRequest = new ClientRequest("login",clientPayLoad,null,"login",loginEvent.getUsername(),loginEvent.getPassword());
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        String permition = serverRequest.getPayLoad().getStringStringHashMap().get("permition");
        if(permition.equals("true")){
            saveSessionToDataBase(serverRequest.getPayLoad().getStringStringHashMap().get("session"));
        }
        else {
            throw new UserPassNotMatchException("username and password does not match");
        }

    }

    private void saveSessionToDataBase(String session) throws SQLException, IOException, ClassNotFoundException {
        String sql = String.format("update \"UserInfo\" set\"Session\" = '%s';",session);
        connectionToLocalDataBase.executeUpdate(sql);
    }

    public void finalize() throws SQLException {
        connectionToLocalDataBase.Disconect();
    }


}
