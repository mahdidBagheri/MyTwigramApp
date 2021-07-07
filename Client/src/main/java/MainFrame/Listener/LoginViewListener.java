package MainFrame.Listener;

import MainFrame.Events.LoginViewEvent;
import MainFrame.View.MainPanel;

import java.io.IOException;

public class LoginViewListener {
    MainPanel mainPanel;
    public LoginViewListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }

    public void listen(LoginViewEvent loginViewEvent) throws IOException {
        mainPanel.addLoginPanel();
    }
}
