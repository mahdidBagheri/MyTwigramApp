package Twitt.View;

import ClientLogin.Exceptions.EmptyFieldException;
import CommonClasses.Exceptions.ServerException;
import Config.ColorConfig.ColorConfig;
import Connection.Exceptions.CouldNotConnectToServerException;
import HyperLink.Model.ImprovedJLabel;
import MainFrame.View.MainPanel;
import TimeLine.Events.ReplyEvent;
import TimeLine.Listeners.ClientMoveTwittListener;
import TimeLine.Listeners.ClientTimeLineMoveReplyListener;
import Twitt.Controller.TwittsController;
import Twitt.Events.TwittViewEvent;
import Twitt.Listeners.ClientLikeListener;
import Twitt.Listeners.ClientReplyListener;
import Twitt.Listeners.ClientRetwittListener;
import Twitt.Listeners.ClientTwittViewListener;
import Twitt.Model.Twitt;
import User.Listener.ClientUserViewListener;
import User.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class TwittPanel extends JPanel implements ActionListener {
    MainPanel mainPanel;
    Twitt twitt;
    User mainUser;


    public TwittPanel instance = null;

    private ImprovedJLabel textLable;
    private JLabel titrLable;
    private JLabel likesLable;
    private JLabel reTwittsLable;
    private ImprovedJLabel repliesLable;

    private JButton replyBtn;
    private JButton likeBtn;
    private JButton reTwittBtn;
    private JButton goAuthorProfileBtn;
    private JButton saveBtn;
    private JButton goReply;
    private JButton exitBtn;

    private JTextArea replyField;

    private JComboBox<String> likesComboBox;
    private JComboBox<String> retwittsComboBox;

    private JButton nextReplyBtn;
    private JButton prevReplyBtn;

    JButton viewSelectedLikeBtn;
    JButton viewSelectedRetBtn;
    private JButton reportBtn;
    JButton deleteBtn;

    private JLabel replyText;

    private JButton backBtn;

    private JFileChooser picChooser;

    JLabel twittPicLable;

    ImageIcon twittPic;


    ClientReplyListener clientReplyListener;
    ClientRetwittListener clientRetwittListener;
    ClientTwittViewListener clientTwittViewListener;
    ClientUserViewListener clientUserViewListener;
    ClientLikeListener clientLikeListener;


    int replyNumber;
    String picPath;

    public TwittPanel(TwittViewEvent twittViewEvent) throws IOException {
        this.mainPanel = twittViewEvent.getMainPanel();
        this.twitt = twittViewEvent.getTwitt();
        this.mainUser = twittViewEvent.getMainUser();

        this.setLayout(null);
        ColorConfig colorConfig = new ColorConfig();


        this.setBackground(colorConfig.getColor01());
        this.setBounds(0, 0, 500, 600);
        this.setVisible(true);

        instance = this;

        titrLable = new JLabel();
        titrLable.setBounds(5, 20, 100, 30);
        titrLable.setVisible(true);
        titrLable.setText(twitt.getAuthor().getUserName());

        textLable = new ImprovedJLabel();
        textLable.setBounds(5, 20, 300, 100);
        textLable.setAlignmentX(TextArea.LEFT_ALIGNMENT);
        textLable.setAlignmentY(TextArea.TOP_ALIGNMENT);
        textLable.setVisible(true);
        textLable.setText(twitt.getText());

        likesLable = new JLabel();
        likesLable.setBounds(5, 150, 100, 50);
        likesLable.setVisible(true);
        likesLable.setText("Likes: " + twitt.getLikes().size());


        likeBtn = new JButton("like");
        likeBtn.setText(this.isLikedBy() ? "removeLike" : "like");
        likeBtn.setBounds(5, 250, 100, 40);
        likeBtn.setVisible(true);
        likeBtn.addActionListener(this);

        likesComboBox = new JComboBox<>();
        likesComboBox.setBounds(5, 300, 100, 20);
        likesComboBox.setVisible(true);
        insertLikesCombo();

        viewSelectedLikeBtn = new JButton("viewSelectedLikeBtn");
        viewSelectedLikeBtn.setText("view");
        viewSelectedLikeBtn.setBounds(5, 320, 100, 15);
        viewSelectedLikeBtn.setVisible(true);
        viewSelectedLikeBtn.addActionListener(this);


        reTwittsLable = new JLabel();
        reTwittsLable.setBounds(110, 150, 100, 50);
        reTwittsLable.setVisible(true);
        reTwittsLable.setText("retwitts: " + twitt.getReTwitts().size());

        reTwittBtn = new JButton("ReTwitt");
        reTwittBtn.setText(twittViewEvent.getMainUser().isRetwitted(twitt.getTwittUUID()) ? "remove Ret" : "Retwitt");
        reTwittBtn.setBounds(110, 250, 100, 40);
        reTwittBtn.setVisible(true);
        reTwittBtn.addActionListener(this);

        retwittsComboBox = new JComboBox<>();
        retwittsComboBox.setBounds(110, 300, 100, 20);
        retwittsComboBox.setVisible(true);


        viewSelectedRetBtn = new JButton("viewSelectedRetBtn");
        viewSelectedRetBtn.setText("view");
        viewSelectedRetBtn.setBounds(110, 320, 100, 15);
        viewSelectedRetBtn.setVisible(true);
        viewSelectedRetBtn.addActionListener(this);
        insertRetwittsCombo();

        repliesLable = new ImprovedJLabel();
        repliesLable.setBounds(220, 150, 100, 50);
        repliesLable.setVisible(true);
        repliesLable.setText("replies: " + twitt.getReplies().size());

        replyBtn = new JButton("Reply");
        replyBtn.setText("Reply");
        replyBtn.setBounds(240, 200, 80, 40);
        replyBtn.setVisible(true);
        replyBtn.addActionListener(this);

        replyField = new JTextArea();
        replyField.setBounds(5, 200, 230, 40);
        replyField.setVisible(true);


        replyText = new JLabel();
        replyText.setBounds(60, 360, 220, 150);
        replyText.setVisible(true);

        nextReplyBtn = new JButton();
        nextReplyBtn.setText("n");
        nextReplyBtn.setBounds(280, 340, 40, 150);
        nextReplyBtn.setVisible(true);
        nextReplyBtn.addActionListener(this);

        prevReplyBtn = new JButton();
        prevReplyBtn.setText("p");
        prevReplyBtn.setBounds(20, 340, 40, 150);
        prevReplyBtn.setVisible(true);
        prevReplyBtn.addActionListener(this);

        goAuthorProfileBtn = new JButton("goAuthurProf");
        goAuthorProfileBtn.setText("goAuthurProf");
        goAuthorProfileBtn.setBounds(220, 250, 100, 40);
        goAuthorProfileBtn.setVisible(true);
        goAuthorProfileBtn.addActionListener(this);


        saveBtn = new JButton("Save");
        saveBtn.setText("Save");
        saveBtn.setBounds(330, 200, 80, 30);
        saveBtn.setVisible(true);
        saveBtn.addActionListener(this);


        goReply = new JButton("Show Reply");
        goReply.setText("Show\nReply");
        goReply.setBounds(185, 500, 80, 35);
        goReply.setVisible(twitt.getReplies().size() != 0);
        goReply.addActionListener(this);

        backBtn = new JButton("back");
        backBtn.setText("back");
        backBtn.setBounds(20, 500, 80, 35);
        backBtn.setVisible(true);
        backBtn.addActionListener(this);

        exitBtn = new JButton("exit");
        exitBtn.setText("exit");
        exitBtn.setBounds(380, 500, 80, 35);
        exitBtn.setVisible(true);
        exitBtn.addActionListener(this);


        deleteBtn = new JButton("delete");
        deleteBtn.setText("delete");
        deleteBtn.setBounds(220,300,80,35);
        deleteBtn.setVisible(twittViewEvent.getMainUser().getUserUUID().equals(twittViewEvent.getTwitt().getAuthorUUID()));
        deleteBtn.setVisible(!twitt.getText().equals("Deleted Twitt!"));
        deleteBtn.addActionListener(this);

        reportBtn = new JButton("reportBtn");
        reportBtn.setText("report");
        reportBtn.setBounds(330, 240, 80, 30);
        reportBtn.setVisible(true);
        reportBtn.addActionListener(this);

        twittPicLable = new JLabel(twittViewEvent.getTwitt().getPic());
        twittPicLable.setVisible(true);
        twittPicLable.setBounds(280, 10, 150, 150);


        this.add(titrLable);
        this.add(textLable);
        this.add(likesLable);
        this.add(reTwittsLable);
        this.add(repliesLable);
        this.add(replyBtn);
        this.add(reTwittBtn);
        this.add(likeBtn);
        this.add(likesComboBox);
        this.add(retwittsComboBox);
        this.add(replyField);
        this.add(replyText);
        this.add(nextReplyBtn);
        this.add(prevReplyBtn);
        this.add(goAuthorProfileBtn);
        this.add(saveBtn);
        this.add(goReply);
        this.add(backBtn);
        this.add(viewSelectedLikeBtn);
        this.add(viewSelectedRetBtn);
        this.add(twittPicLable);
        this.add(deleteBtn);
        this.add(reportBtn);
        this.add(exitBtn);

        if(twitt.getText().equals("Deleted Twitt!")){
            likeBtn.setEnabled(false);
            reTwittBtn.setEnabled(false);
            replyBtn.setEnabled(false);
            saveBtn.setEnabled(false);
            reportBtn.setEnabled(false);
        }

        clientReplyListener = new ClientReplyListener();
        clientLikeListener = new ClientLikeListener();
        clientRetwittListener = new ClientRetwittListener();
        clientUserViewListener = new ClientUserViewListener(mainPanel);
        clientTwittViewListener = new ClientTwittViewListener(mainPanel);

    }

    private boolean isLikedBy() {
        for(int i = 0; i<this.twitt.getLikes().size();i++){
            if(this.twitt.getLikes().get(i).equals(mainUser.getUserName())){
                return true;
            }
        }
        return false;
    }

    public void setTwitt(Twitt twitt) throws ClassNotFoundException, SQLException, CouldNotConnectToServerException, IOException {
        textLable.setText(twitt.getText());
        likesLable.setText("likes" + twitt.getLikes().size());
        reTwittsLable.setText("Retwitts" + twitt.getReTwitts().size());
        repliesLable.setText("Replies" + twitt.getReplies().size());

        if (twitt.getReplies().size() != 0) {
            Twitt reply = twitt.getReplies().get(0);

            TwittsController replyController = new TwittsController(reply);
            replyController.readAllByUUID();
            setReply(reply);
        }
        repaint();
    }

    public void setReply(Twitt twitt) {
        replyText.setText(twitt.getText());
    }


    public void insertLikesCombo() {
        getLikesComboBox().removeAllItems();

        for (int i = 0; i < twitt.getLikes().size(); i++) {
            getLikesComboBox().addItem(twitt.getLikes().get(i));
        }
    }

    public void insertRetwittsCombo() {
        getRetwittsComboBox().removeAllItems();

        for (int i = 0; i < twitt.getReTwitts().size(); i++) {
            getRetwittsComboBox().addItem(twitt.getTwittReTwittersList().get(i).getUserName());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == goReply){
            try {
                TwittViewEvent twittViewEvent = new TwittViewEvent(twitt.getReplies().get(replyNumber),mainPanel);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

        }
        else if(e.getSource() == backBtn){
            mainPanel.back();
        }
        else if(e.getSource() == likeBtn){
            try {
                clientLikeListener.listen(this.twitt);
                updateGraphicsLike();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ServerException serverException) {
                serverException.printStackTrace();
            }
        }
        else if(e.getSource() == reTwittBtn){
            try {
                clientRetwittListener.listen(this.twitt);
                updateGraphicsRetwitt();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ServerException serverException) {
                JOptionPane.showMessageDialog(this,"server error may already retwitted");
                serverException.printStackTrace();
            }
        }
        else if(e.getSource() == replyBtn){

            try {
                ReplyEvent replyEvent = new ReplyEvent(this.twitt,replyField.getText());
                clientReplyListener.listen(replyEvent);
                updateReply(replyEvent.getNewTwitt());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (EmptyFieldException emptyFieldException) {
                emptyFieldException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            }
        }
    }

    public void addTwittRetwitts() {
        retwittsComboBox.removeAllItems();
        for(int i = 0; i < this.twitt.getTwittReTwittersList().size() ; i++){
            retwittsComboBox.addItem(this.twitt.getTwittReTwittersList().get(i).getUserName());
        }
    }

    public void addTwittLikes() {
        getLikesComboBox().removeAllItems();
        for(int i = 0; i < this.twitt.getLikes().size() ; i++){
            likesComboBox.addItem(this.twitt.getLikes().get(i));
        }
    }

    private void updateGraphicsLike() {
        if(likeBtn.getText().equals("like")){
            likeBtn.setText("removeLike");
            mainUser.getLikes().add(twitt.getTwittUUID());
            twitt.getLikes().add(mainUser.getUserName());
            addTwittLikes();
            addTwittRetwitts();
            likesLable.setText("Likes: " + twitt.getLikes().size());
            reTwittsLable.setText("retwitts: " + twitt.getReTwitts().size());
            JOptionPane.showMessageDialog(this,"liked successfully");
        }
        else if(likeBtn.getText().equals("removeLike")){
            likeBtn.setText("like");
            mainUser.getLikes().remove(twitt.getTwittUUID());
            twitt.getLikes().remove(mainUser.getUserName());
            addTwittLikes();
            addTwittRetwitts();
            likesLable.setText("Likes: " + twitt.getLikes().size());
            reTwittsLable.setText("retwitts: " + twitt.getReTwitts().size());

            JOptionPane.showMessageDialog(this,"removeLike");
        }
    }

    private void updateGraphicsRetwitt() {
        mainUser.getReTwitts().add(twitt.getTwittUUID());
        twitt.getReTwitts().add(mainUser.getUserName());
        twitt.getTwittReTwittersList().add(mainUser);
        addTwittLikes();
        addTwittRetwitts();
        likesLable.setText("Likes: " + twitt.getLikes().size());
        likesLable.setText("retwitts: " + twitt.getReTwitts().size());
        JOptionPane.showMessageDialog(this,"liked successfully");
    }

    public JComboBox<String> getLikesComboBox() {
        return likesComboBox;
    }

    public JComboBox<String> getRetwittsComboBox() {
        return retwittsComboBox;
    }

    public void updateReply(Twitt twitt) {
        this.twitt.getReplies().add(twitt);
        if(this.twitt.getReplies().size() == 1) {
            replyText.setText(twitt.getText());
            replyText.repaint();
            this.repaint();
        }
    }
}
