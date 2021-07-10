package ClientSingup.Listener;

import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Exceptions.EmailExistException;
import Connection.Exceptions.UsernameExistsException;
import ClientSingup.Controller.ClientSignupController;
import ClientSingup.Events.SignupEvent;
import ClientSingup.Exceptions.EmptyFieldException;
import ClientSingup.Exceptions.PasswordsNotMatchException;
import ClientSingup.Exceptions.UserNameStartsWithDigitException;
import LocalDataBase.LocalDataBase;
import MainFrame.View.MainPanel;

import java.io.IOException;
import java.sql.SQLException;

public class SignupListener {
    MainPanel mainPanel;
    public SignupListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(SignupEvent signupEvent) throws UserNameStartsWithDigitException,
            EmptyFieldException, PasswordsNotMatchException,
            ClassNotFoundException, UsernameExistsException,
            EmailExistException, CouldNotConnectToServerException,
            IOException, SQLException {

        ClientSignupController signupController = new ClientSignupController(mainPanel);

        signupController.validateSignup(signupEvent);
        signupController.signup(signupEvent);
        LocalDataBase localDataBase = new LocalDataBase();
        localDataBase.createLocalDataBase("User" + signupEvent.getUserName() + "DataBase");

    }

}
