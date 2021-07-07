package MainFrame.Events;

import MainFrame.View.MainPanel;

public class LoginViewEvent {
    MainPanel mainPanel;
    public LoginViewEvent(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
