package MainFrame.Listener;

import MainFrame.View.MainPanel;

public class BackListener {
    MainPanel mainPanel;
    public BackListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void listen(){
        mainPanel.back();
    }
}
