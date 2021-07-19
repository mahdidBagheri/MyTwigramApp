package TimeLine.ServerListener;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Server.ServerConnection;
import Connection.Server.ServerPayLoad;
import Connection.Server.ServerRequest;
import TimeLine.Model.TimeLine;
import TimeLine.ServerController.ServerTimeLineController;
import Twitt.Exceptions.TwittReadDataException;
import User.Exceptions.unsuccessfullReadDataFromDatabase;

import java.io.IOException;
import java.sql.SQLException;

public class ServerTimeLineListener {

    ServerConnection serverConnection;
    public ServerTimeLineListener(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void listen(ClientRequest clientRequest) throws TwittReadDataException, unsuccessfullReadDataFromDatabase, SQLException, IOException {
        TimeLine timeLine = new TimeLine();
        ServerTimeLineController serverTimeLineController = new ServerTimeLineController(serverConnection, timeLine);
        if(clientRequest.getSource().equals("timeLine")){
            serverTimeLineController.findTimeLineTwitts(clientRequest);
            ServerPayLoad serverPayLoad = new ServerPayLoad();
            serverPayLoad.setTimeLine(timeLine);
            ServerRequest serverRequest = new ServerRequest(clientRequest.getUsername(),"timeLine",serverPayLoad);
            serverConnection.execute(serverRequest);
        }
    }
}
