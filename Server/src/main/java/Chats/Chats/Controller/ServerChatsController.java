package Chats.Chats.Controller;

import Chats.Chats.Model.Chats;
import Connection.DataBaseConnection.ConnectionToDataBase;

public class ServerChatsController {
    Chats chats;
    ConnectionToDataBase connectionToDataBase;
    public ServerChatsController(Chats chats) {
        this.chats = chats;
        this.connectionToDataBase = new ConnectionToDataBase();
    }



}
