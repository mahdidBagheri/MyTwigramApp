package Chats.Group.Listener;

import Chats.Chats.View.ChatsPanel;
import Chats.PV.Exceptions.ExistingPVException;

import java.util.Objects;

public class AddMemmberListener {

    ChatsPanel chatsPanel;

    public AddMemmberListener(ChatsPanel chatsPanel) {
        this.chatsPanel = chatsPanel;
    }

    public void listen(String username) throws ExistingPVException {
        boolean isRepeated = checkRepeatedMemberForGroup(username);
        if(isRepeated){
            throw new ExistingPVException("PV exist");
        }

        chatsPanel.getNewGroupMembersCombo().addItem(username);
    }

    private boolean checkRepeatedMemberForGroup(String username) {
        for (int i = 0; i < chatsPanel.getNewGroupMembersCombo().getItemCount(); i++) {
            String memberUsername = chatsPanel.getNewGroupMembersCombo().getItemAt(i);
            if(memberUsername.equals(username)){
                return true;
            }
        }
        return false;
    }
}
