package User.Model;

import Chats.Chats.Model.Chats;
import Chats.PV.Model.PV;
import Groups.Model.Group;
import Notification.Model.NewFollowerNotif;
import Notification.Model.Notification;
import Notification.Model.PendingFollowersRequestNotif;
import Twitt.Model.Twitt;

import javax.swing.*;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;

public class User implements Serializable {

    private String userUUID;
    private String userName;
    private String passWord;
    private String fName;
    private String lName;
    private String email;
    private String bio;
    private String phoneNumber;
    private String lastSeen;
    private String birthDate;
    private String privacy;
    private String lastSeenMode;
    private String session;
    private String sync;

    private LinkedList<User> followers = new LinkedList<User>();
    private LinkedList<User> following = new LinkedList<User>();
    private LinkedList<User> blackList = new LinkedList<User>();
    private LinkedList<Notification> notifications = new LinkedList<Notification>();

    private LinkedList<String> pendingFollowingRequest = new LinkedList<String>();
    private LinkedList<PendingFollowersRequestNotif> pendingFollowersRequestNotif = new LinkedList<PendingFollowersRequestNotif>();
    private LinkedList<NewFollowerNotif> newFollowers = new LinkedList<NewFollowerNotif>();
    private LinkedList<String> newUnFollowers = new LinkedList<String>();
    private LinkedList<User> mutedUsers = new LinkedList<User>();

    private String Status;

    private LinkedList<Twitt> twitts = new LinkedList<Twitt>();
    private LinkedList<String> likes = new LinkedList<String>();
    private LinkedList<Twitt> savedTwitts = new LinkedList<Twitt>();
    private LinkedList<String> reTwitts = new LinkedList<String>();
    private LinkedList<String> replies = new LinkedList<String>();

    private LinkedList<PV> chats = new LinkedList<PV>();
    private LinkedList<Group> groups = new LinkedList<Group>();

    private LinkedList<Activity> activities = new LinkedList<Activity>();

    private ImageIcon profilePic;

    private Chats chat = new Chats();

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public ImageIcon getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(ImageIcon profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getLastSeenMode() {
        return lastSeenMode;
    }

    public void setLastSeenMode(String lastSeenMode) {
        this.lastSeenMode = lastSeenMode;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public LinkedList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(LinkedList<User> followers) {
        this.followers = followers;
    }

    public LinkedList<User> getFollowing() {
        return following;
    }

    public void setFollowing(LinkedList<User> following) {
        this.following = following;
    }

    public LinkedList<User> getBlackList() {
        return blackList;
    }

    public void setBlackList(LinkedList<User> blackList) {
        this.blackList = blackList;
    }

    public LinkedList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(LinkedList<Notification> notifications) {
        this.notifications = notifications;
    }

    public LinkedList<String> getPendingFollowingRequest() {
        return pendingFollowingRequest;
    }

    public void setPendingFollowingRequest(LinkedList<String> pendingFollowingRequest) {
        this.pendingFollowingRequest = pendingFollowingRequest;
    }

    public LinkedList<PendingFollowersRequestNotif> getPendingFollowersRequestNotif() {
        return pendingFollowersRequestNotif;
    }

    public void setPendingFollowersRequestNotif(LinkedList<PendingFollowersRequestNotif> pendingFollowersRequestNotif) {
        this.pendingFollowersRequestNotif = pendingFollowersRequestNotif;
    }

    public LinkedList<NewFollowerNotif> getNewFollowers() {
        return newFollowers;
    }

    public void setNewFollowers(LinkedList<NewFollowerNotif> newFollowers) {
        this.newFollowers = newFollowers;
    }

    public LinkedList<String> getNewUnFollowers() {
        return newUnFollowers;
    }

    public void setNewUnFollowers(LinkedList<String> newUnFollowers) {
        this.newUnFollowers = newUnFollowers;
    }

    public LinkedList<User> getMutedUsers() {
        return mutedUsers;
    }

    public void setMutedUsers(LinkedList<User> mutedUsers) {
        this.mutedUsers = mutedUsers;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public LinkedList<Twitt> getTwitts() {
        return twitts;
    }

    public void setTwitts(LinkedList<Twitt> twitts) {
        this.twitts = twitts;
    }

    public LinkedList<String> getLikes() {
        return likes;
    }

    public void setLikes(LinkedList<String> likes) {
        this.likes = likes;
    }

    public LinkedList<Twitt> getSavedTwitts() {
        return savedTwitts;
    }

    public void setSavedTwitts(LinkedList<Twitt> savedTwitts) {
        this.savedTwitts = savedTwitts;
    }

    public LinkedList<String> getReTwitts() {
        return reTwitts;
    }

    public void setReTwitts(LinkedList<String> reTwitts) {
        this.reTwitts = reTwitts;
    }

    public LinkedList<String> getReplies() {
        return replies;
    }

    public void setReplies(LinkedList<String> replies) {
        this.replies = replies;
    }

    public LinkedList<PV> getChats() {
        return chats;
    }

    public void setChats(LinkedList<PV> chats) {
        this.chats = chats;
    }

    public void addChat(PV pv){
        this.chats.add(pv);
    }

    public LinkedList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(LinkedList<Activity> activities) {
        this.activities = activities;
    }

    public Chats getChat() {
        return chat;
    }

    public void setChat(Chats chat) {
        this.chat = chat;
    }

    public LinkedList<Group> getGroups() {
        return groups;
    }

    public void addGrouop(Group group){this.groups.add(group);}

    public void setGroups(LinkedList<Group> groups) {
        this.groups = groups;
    }

    public boolean isFollowedBy(String username) throws SQLException {
        for (int i = 0; i <this.getFollowing().size() ; i++) {
            if(this.getFollowers().get(i).getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean isFollowing(String UserUUID){
        if(this.getFollowing().contains(UserUUID)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isBlocked(String UserUUID){
        if(this.getBlackList().contains(UserUUID)){
            return true;
        }
        else{
            return false;
        }
    }
    /*
        public boolean isBlockedBy(String UserUUID) throws SQLException {
            User dstUser = new User();
            dstUser.setUserUUID(UserUUID);
            UserController dsrUserController = new UserController(dstUser);
            dsrUserController.readBlackList();
            if(dstUser.getBlackList().contains(this.getUserUUID())){
                return true;
            }
            else{
                return false;
            }
        }
     */
    public boolean isMuted(String UserUUID){
        if(this.getMutedUsers().contains(UserUUID)){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isMutedBy(String UserUUID) throws SQLException {
        User dstUser = new User();
        if(dstUser.getMutedUsers().contains(this.getUserUUID())){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isLiked(String twittUUID) {
        if(this.getLikes().contains(twittUUID)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isRetwitted(String twittUUID) {
        if(this.getReTwitts().contains(twittUUID)){
            return true;
        }
        else{
            return false;
        }
    }
}
