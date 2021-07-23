package Twitt.Model;

import User.Model.User;

import javax.swing.*;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

public class Twitt implements Serializable {
    String text;

    String twittUUID;
    User author;
    String authorUUID;
    LinkedList<String> likes = new LinkedList<String>();
    LinkedList<String> reTwitts = new LinkedList<String>();
    LinkedList<User> twittReTwittersList = new LinkedList<>();
    LinkedList<Twitt> replies= new LinkedList<Twitt>();
    LinkedList<String> reports= new LinkedList<String>();
    String date;
    String type;
    String parent;
    String hasPic;
    String picAddress;
    ImageIcon pic;
    User userWhoLiked;
    File picFile;

    public File getPicFile() {
        return picFile;
    }

    public void setPicFile(File picFile) {
        this.picFile = picFile;
    }

    public String getHasPic() {
        return hasPic;
    }

    public void setHasPic(String hasPic) {
        this.hasPic = hasPic;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public User getUserWhoLiked() {
        return userWhoLiked;
    }

    public void setUserWhoLiked(User userWhoLiked) {
        this.userWhoLiked = userWhoLiked;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public String getTwittUUID() {
        return twittUUID;
    }

    public void setTwittUUID(String twittUUID) {
        this.twittUUID = twittUUID;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getAuthorUUID() {
        return authorUUID;
    }

    public void setAuthorUUID(String authorUUID) {
        this.authorUUID = authorUUID;
    }

    public LinkedList<String> getLikes() {
        return likes;
    }

    public void setLikes(LinkedList<String> likes) {
        this.likes = likes;
    }

    public LinkedList<String> getReTwitts() {
        return this.reTwitts;
    }

    public void setReTwitts(LinkedList<String> reTwitts) {
        this.reTwitts = reTwitts;
    }

    public LinkedList<Twitt> getReplies() {
        return this.replies;
    }

    public void setReplies(LinkedList<Twitt> replies) {
        this.replies = replies;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ImageIcon getPic() {
        return this.pic;
    }

    public void setPic(ImageIcon pic) {
        this.pic = pic;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public LinkedList<String> getReports() {
        return reports;
    }

    public void setReports(LinkedList<String> reports) {
        this.reports = reports;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public LinkedList<User> getTwittReTwittersList() {
        return twittReTwittersList;
    }

    public void setTwittReTwittersList(LinkedList<User> twittReTwittersList) {
        this.twittReTwittersList = twittReTwittersList;
    }
}
