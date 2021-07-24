package NewTwitt.Listener;

import Connection.Exceptions.CouldNotConnectToServerException;
import MainFrame.View.MainPanel;
import NewTwitt.Controller.NewTwittController;
import NewTwitt.Controller.NewTwittValidation;
import NewTwitt.Events.NewTwittEvent;
import NewTwitt.Exceptions.NewTwittException;

import java.io.IOException;
import java.sql.SQLException;

public class NewTwittListener {
    MainPanel mainPanel;

    NewTwittValidation newTwittValidation = new NewTwittValidation();
    NewTwittController newTwittController = new NewTwittController();

    public NewTwittListener(MainPanel mainPanel) throws SQLException, IOException, ClassNotFoundException {
        this.mainPanel = mainPanel;
    }

    public void listen(NewTwittEvent newTwittEvent) throws NewTwittException, SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        newTwittValidation.Validate(newTwittEvent);
        newTwittController.newTwitt(newTwittEvent);
    }
}
