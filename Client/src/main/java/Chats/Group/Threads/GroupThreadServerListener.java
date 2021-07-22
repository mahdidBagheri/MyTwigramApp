package Chats.Group.Threads;

import Chats.Group.View.GroupPanel;

public class GroupThreadServerListener extends Thread {
    GroupPanel groupPanel;
    boolean isRunning = true;

    public GroupThreadServerListener(GroupPanel groupPanel) {
        this.groupPanel = groupPanel;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
