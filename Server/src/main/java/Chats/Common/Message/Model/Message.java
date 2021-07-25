package Chats.Common.Message.Model;

import User.Model.User;

import javax.swing.*;
import java.io.File;
import java.io.Serializable;

public class Message implements Serializable {
    User author;
    String text;
    String date;
    ImageIcon pic;
    File picFile;
    String state;

    public Message(User author, String text, String date, String picAddress) {
        this.author = author;
        this.text = text;
        this.date = date;
        this.pic = new ImageIcon(picAddress);
        this.picFile = new File(picAddress);
    }

    public Message(User author, String text, String date) {
        this.author = author;
        this.text = text;
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ImageIcon getPic() {
        return pic;
    }

    public void setPic(ImageIcon pic) {
        this.pic = pic;
    }

    public File getPicFile() {
        return picFile;
    }

    public void setPicFile(File picFile) {
        this.picFile = picFile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
