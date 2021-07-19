package ServerSearch.Listener;

import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import Twitt.Exceptions.TwittReadDataException;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerSearchListener {
    ServerConnection serverConnection;
    public ServerSearchListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws SQLException, unsuccessfullReadDataFromDatabase, IOException, TwittReadDataException {
        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
        String sql = String.format("select * from \"UsersTable\" where \"UserName\" = '%s';",clientRequest.getClientPayLoad().getStringStringHashMap().get("searchedItem"));
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        ServerRequest serverRequest = null;
        ServerPayLoad serverPayLoad = new ServerPayLoad();
        if(rs != null){
            if(rs.next()){
                User user = new User();
                ServerUserController userController = new ServerUserController(user);
                userController.readAll(rs.getString(2));
                user.setPassWord("");
                serverPayLoad.setUser(user);
                serverRequest = new ServerRequest(clientRequest.getUsername(),"found",serverPayLoad);
            }
            else {
                serverRequest = new ServerRequest(clientRequest.getUsername(),"notfound",null);
            }
        }
        serverConnection.execute(serverRequest);
    }
}
