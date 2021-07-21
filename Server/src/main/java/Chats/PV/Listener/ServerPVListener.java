package Chats.PV.Listener;

import Chats.PV.Controller.ServerPVController;
import Chats.PV.Model.PV;
import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ServerPVListener {
    ServerConnection serverConnection;
    public ServerPVListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws SQLException, IOException {
        if(clientRequest.getCommand().equals("sendMessage")){
            ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
            String PVTableName = clientRequest.getClientPayLoad().getStringStringHashMap().get("PVAddress");
            String username = clientRequest.getClientPayLoad().getStringStringHashMap().get("username");
            String text = clientRequest.getClientPayLoad().getStringStringHashMap().get("text");
            String picAddress = clientRequest.getClientPayLoad().getStringStringHashMap().get("imageAddress");
            String date = clientRequest.getClientPayLoad().getStringStringHashMap().get("date");
            String sql;
            if(picAddress != null){
                sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"ImageAddress\",\"Date\") values ('%s','%s','%s','%s');",PVTableName,text,username,picAddress,date);
            }
            else {
                sql = String.format("insert into \"%s\" (\"Message\",\"Author\",\"Date\") values ('%s','%s','%s');",PVTableName,text,username,date);

            }
            connectionToDataBase.executeUpdate(sql);
            connectionToDataBase.Disconect();
        }
        else if(clientRequest.getCommand().equals("requestPV")){
            PV pv = new PV();
            pv.setUser(new User());
            pv.setContact(new User());
            pv.getUser().setUserName(clientRequest.getUsername());
            String PVTableName = clientRequest.getClientPayLoad().getStringStringHashMap().get("PVTableName");
            pv.setPVTableName(PVTableName);
            ServerPVController serverPVController = new ServerPVController(pv);
            serverPVController.readMessages();

            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setPv(pv);
            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"pvRequest",serverPayLoad);
            serverConnection.execute(serverRequest);
        }
    }
}
