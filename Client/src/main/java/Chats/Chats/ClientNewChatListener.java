package Chats.Chats;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Server.ServerRequest;
import User.Controller.ClientUserController;
import User.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class ClientNewChatListener {
    public void listen(NewChatEvent newChatEvent) throws SQLException, IOException, ClassNotFoundException {
        User mainUser = new User();
        ClientUserController clientUserController = new ClientUserController(mainUser);
        clientUserController.setAsMain();

        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("username1",mainUser.getUserName());
        clientPayLoad.getStringStringHashMap().put("username2",newChatEvent.getUser().getUserName());


        ClientRequest clientRequest = new ClientRequest("chats",clientPayLoad,mainUser.getSession(),"createNewChat",mainUser.getUserName(),mainUser.getPassWord());
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();

        String chatAddress = serverRequest.getPayLoad().getStringStringHashMap().get("chatAddress");

        NewChatController newChatController = new NewChatController();

        newChatController.saveChatToLocalDataBase(chatAddress,newChatEvent.getUser().getUserName() );
    }


}
