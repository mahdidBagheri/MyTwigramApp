package ServerLogin.Listener;

import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import ServerLogin.Controller.ServerLoginController;

import java.io.IOException;
import java.sql.SQLException;

public class ServerLoginListener {
    ServerConnection serverConnection;
    public ServerLoginListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws SQLException, IOException {
        ServerLoginController serverLoginController = new ServerLoginController();

        boolean isUserPassMatch =  serverLoginController.checkUserPass(clientRequest.getClientPayLoad().getStringStringHashMap().get("username"),
                clientRequest.getClientPayLoad().getStringStringHashMap().get("password"));

        if(isUserPassMatch){
            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.getStringStringHashMap().put("permition","true");
            serverPayLoad.getStringStringHashMap().put("session",serverLoginController.createAndSaveSession(clientRequest.getUsername()));
            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"login",serverPayLoad);
            serverConnection.execute(serverRequest);
        }
        else {
            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.getStringStringHashMap().put("permition","false");
            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"login",serverPayLoad);
            serverConnection.execute(serverRequest);
        }
    }


}
