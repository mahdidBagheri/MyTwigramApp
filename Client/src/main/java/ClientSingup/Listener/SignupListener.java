package ClientSingup.Listener;

import ClientSingup.Controller.ClientSignupController;
import ClientSingup.Events.SignupEvent;
import ClientSingup.Exceptions.EmptyFieldException;
import ClientSingup.Exceptions.PasswordsNotMatchException;
import ClientSingup.Exceptions.UserNameStartsWithDigitException;
import MainFrame.View.MainPanel;

public class SignupListener {
    MainPanel mainPanel;
    public SignupListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(SignupEvent signupEvent) throws UserNameStartsWithDigitException,
            EmptyFieldException, PasswordsNotMatchException {

        ClientSignupController signupController = new ClientSignupController(mainPanel);

        signupController.validateSignup(signupEvent);
        signupController.signup();
    }

}
