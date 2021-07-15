package ClientSearch.Listeners;

import ClientSearch.Events.SearchEvent;
import ClientSearch.View.SearchPanel;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Server.ServerRequest;
import MainFrame.View.MainPanel;
import User.Controller.UserController;
import User.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

public class SearchViewListener {
    MainPanel mainPanel;
    SearchPanel searchPanel;
    public SearchViewListener(MainPanel mainPanel, SearchPanel searchPanel) {
        this.mainPanel = mainPanel;
        this.searchPanel = searchPanel;
    }

    public void listen(SearchEvent searchEvent) throws SQLException, IOException, ClassNotFoundException {
        User mainUser = new User();
        UserController mainUserController = new UserController(mainUser);
        mainUserController.setAsMain();

        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad clientPayLoad = new ClientPayLoad();
        clientPayLoad.getStringStringHashMap().put("searchedItem",searchEvent.getSearchedItem());
        ClientRequest clientRequest = new ClientRequest("search",clientPayLoad,mainUser.getSession(),"search",mainUser.getUserName(),mainUser.getPassWord());
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();
        searchPanel.showSearchResult(serverRequest);

    }
}
