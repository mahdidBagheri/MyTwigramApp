package MainFrame.Events;

import MainFrame.View.MainPanel;

public class SignupViewEvent {
    MainPanel mainPanel;
    public SignupViewEvent(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
