package MainFrame.Listener;

import MainFrame.Events.LoginViewEvent;
import MainFrame.Events.SignupViewEvent;
import MainFrame.View.MainPanel;

import java.io.IOException;

public class SignupViewListener {
    MainPanel mainPanel;
    public SignupViewListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }

    public void listen(SignupViewEvent signupViewEvent) throws IOException {
        mainPanel.addSignupPanel();
    }
}
