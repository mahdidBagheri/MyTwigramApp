package ClientSingup.Listener;

import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Exceptions.EmailExistException;
import Connection.Exceptions.UsernameExistsException;
import ClientSingup.Controller.ClientSignupController;
import ClientSingup.Events.SignupEvent;
import ClientSingup.Exceptions.EmptyFieldException;
import ClientSingup.Exceptions.PasswordsNotMatchException;
import ClientSingup.Exceptions.UserNameStartsWithDigitException;
import MainFrame.View.MainPanel;

import java.io.IOException;

public class SignupListener {
    MainPanel mainPanel;
    public SignupListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(SignupEvent signupEvent) throws UserNameStartsWithDigitException,
            EmptyFieldException, PasswordsNotMatchException,
            ClassNotFoundException, UsernameExistsException,
            EmailExistException, CouldNotConnectToServerException,
            IOException {

        ClientSignupController signupController = new ClientSignupController(mainPanel);

        signupController.validateSignup(signupEvent);
        signupController.signup();
    }

}
