package Chats.Group.Event;

public class GroupViewEvent {
    String groupName;

    public GroupViewEvent(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
