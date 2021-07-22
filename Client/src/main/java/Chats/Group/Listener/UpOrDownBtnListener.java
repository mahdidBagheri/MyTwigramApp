package Chats.Group.Listener;

import Chats.Group.View.GroupPanel;
import Chats.PV.View.PVPanel;

import java.io.IOException;

public class UpOrDownBtnListener {

    GroupPanel groupPanel;
    public UpOrDownBtnListener(GroupPanel groupPanel) {
        this.groupPanel = groupPanel;
    }

    public void listen(String command) throws IOException {
        if(command.equals("up")){
            if(groupPanel.getMessageNumber() > 0){
                groupPanel.decreaseMessageNumber();
                groupPanel.setMessage(groupPanel.getGroup().getMessages().get(groupPanel.getMessageNumber()));
            }
        }
        else if(command.equals("down")){
            if(groupPanel.getMessageNumber() < groupPanel.getGroup().getMessages().size()-1){
                groupPanel.increaseMessageNumber();
                groupPanel.setMessage(groupPanel.getGroup().getMessages().get(groupPanel.getMessageNumber()));
            }
        }
    }
}
