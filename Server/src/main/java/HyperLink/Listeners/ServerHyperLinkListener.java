package HyperLink.Listeners;

import Chats.PV.Controller.ServerPVController;
import Chats.PV.Model.PV;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import Twitt.Controller.ServerTwittsController;
import Twitt.Exceptions.TwittReadDataException;
import Twitt.Model.Twitt;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ServerHyperLinkListener {
    ServerConnection serverConnection;

    public ServerHyperLinkListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws TwittReadDataException, unsuccessfullReadDataFromDatabase, SQLException, IOException {
        String link = clientRequest.getClientPayLoad().getStringStringHashMap().get("link");
        if(link.startsWith("@Twitt")){
            Twitt reqTwitt = new Twitt();
            reqTwitt.setTwittUUID(link.substring(6));
            ServerTwittsController serverTwittsController = new ServerTwittsController(reqTwitt);
            if(serverTwittsController.isExist()) {
                serverTwittsController.readDataFromDataBaseByUUID();
                ServerPayLoad serverPayLoad = new ServerPayLoad();
                serverPayLoad.setTwitt(reqTwitt);
                ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"validLink",serverPayLoad);
                serverConnection.execute(serverRequest);

            }
            else {
                ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"invalidLink",null);
                serverConnection.execute(serverRequest);
            }
        }
        else if(link.startsWith("@PV")){
            User user = new User();
            user.setUserName(clientRequest.getUsername());
            ServerUserController serverUserController = new ServerUserController(user);
            serverUserController.readAll(user.getUserName());

            PV pv = new PV();
            pv.setUser(user);
            ServerPVController serverPVController = new ServerPVController(pv);
            pv.setPVTableName(link.substring(1));

            if(serverPVController.isExist()){
                serverPVController.readPV();
                serverPVController.readMessages();
                ServerPayLoad serverPayLoad = new ServerPayLoad();
                serverPayLoad.setPv(pv);
                ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"validLink",serverPayLoad);
                serverConnection.execute(serverRequest);

            }
            else {
                ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"invalidLink",null);
                serverConnection.execute(serverRequest);
            }
        }
        else {
            User reqUser = new User();
            reqUser.setUserName(link.substring(1));
            ServerUserController serverUserController = new ServerUserController(reqUser);
            if(serverUserController.isExist()){
                serverUserController.readAll(reqUser.getUserName());
                ServerPayLoad serverPayLoad = new ServerPayLoad();
                serverPayLoad.setUser(reqUser);
                ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"validLink",serverPayLoad);
                serverConnection.execute(serverRequest);
            }
            else {
                ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"invalidLink",null);
                serverConnection.execute(serverRequest);
            }
        }
    }
}
