package Chats.PV.Listener;

import Chats.PV.View.PVPanel;

import java.io.IOException;

public class UpOrDownBtnListener {
    PVPanel pvPanel;
    public UpOrDownBtnListener(PVPanel pvPanel) {
    this.pvPanel = pvPanel;
    }

    public void listen(String command) throws IOException {
        if(command.equals("up")){
            if(pvPanel.getMessageNumber() > 0){
                pvPanel.decreaseMessageNumber();
                pvPanel.setMessage(pvPanel.getPv().getMessages().get(pvPanel.getMessageNumber()));
            }
        }
        else if(command.equals("down")){
            if(pvPanel.getMessageNumber() < pvPanel.getPv().getMessages().size()-1){
                pvPanel.increaseMessageNumber();
                pvPanel.setMessage(pvPanel.getPv().getMessages().get(pvPanel.getMessageNumber()));
            }
        }
    }
}
