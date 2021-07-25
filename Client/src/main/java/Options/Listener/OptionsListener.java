package Options.Listener;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import LocalDataBase.LocalDataBase;
import MainFrame.View.MainPanel;
import User.Controller.UserController;
import User.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class OptionsListener {
    MainPanel mainPanel;
    public OptionsListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(String command) throws Throwable {
        if(command.equals("profileBtn")){
            SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
            syncLocalDataBase.syncAll();
            syncLocalDataBase.finalize();

            User mainUser = new User();
            UserController userController = new UserController(mainUser);
            userController.setAsMain();

            mainPanel.addProfilePanel(userController.getUser());
            userController.finalize();

        }
        else if(command.equals("searchBtn")){
            mainPanel.addSearchPanel();
        }
        else if(command.equals("logOutBtn")){
            mainPanel.addWelcomePanel();
        }
        else if(command.equals("newTwitt")){
            mainPanel.addNewTwittPanel();
        }
        else if(command.equals("timeLine")){
            User mainUser = new User();
            ClientUserController userController = new ClientUserController(mainUser);
            userController.setAsMain();
            userController.finalize();

            ClientConnection clientConnection = new ClientConnection();
            ClientPayLoad clientPayLoad = new ClientPayLoad();
            clientPayLoad.getStringStringHashMap().put("username",mainUser.getUserName());
            ClientRequest clientRequest = new ClientRequest("timeLine",clientPayLoad,mainUser.getSession(),"timeLine",mainUser.getUserName(),mainUser.getPassWord());
            clientConnection.execute(clientRequest);

            ClientWaitForInput.waitForInput(clientConnection.getSocket());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
            ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();

            mainPanel.addTimeLinePanel(serverRequest.getPayLoad().getTimeLine(), mainUser);
        }
        else if(command.equals("chats")){

            SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
            syncLocalDataBase.syncChats();
            syncLocalDataBase.syncFollowings();
            syncLocalDataBase.syncFollowers();
            syncLocalDataBase.syncGroups();
            syncLocalDataBase.finalize();

            User mainUser = new User();
            ClientUserController userController = new ClientUserController(mainUser);
            userController.setAsMain();
            userController.finalize();

            mainPanel.addChatsPanel(mainUser);
        }
    }

}
