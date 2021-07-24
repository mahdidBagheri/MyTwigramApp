package TimeLine.View;

import ClientLogin.Exceptions.EmptyFieldException;
import CommonClasses.Exceptions.ServerException;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import Connection.Exceptions.CouldNotConnectToServerException;
import Constants.Constants;
import HyperLink.Model.ImprovedJLabel;
import MainFrame.View.MainPanel;
import TimeLine.Controller.ClientTimeLineController;
import TimeLine.Events.ReplyEvent;
import TimeLine.Listeners.*;
import TimeLine.Model.TimeLine;
import Twitt.Events.TwittViewEvent;
import Twitt.Listeners.ClientTwittViewListener;
import Twitt.Model.Twitt;
import User.Events.UserViewEvent;
import User.Listener.ClientUserViewListener;
import User.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class TimeLinePanel extends JPanel implements ActionListener {
    public static TimeLinePanel instance = null;

    public JButton upBotton;
    public JButton downBotton;

    public ImprovedJLabel textLable;
    public JLabel titrLable;
    public JLabel likesLable;
    public JLabel reTwittsLable;
    public JLabel repliesLable;

    public JLabel imageLabel;

    public JButton replyBtn;
    public JButton likeBtn;
    public JButton reTwittBtn;
    public JButton goAuthorProfileBtn;
    public JButton saveBtn;
    public JButton reportBtn;
    public JButton goReply;

    public JTextArea replyField;

    public JComboBox<String> likesComboBox;
    public JComboBox<String> retwittsComboBox;

    public JButton nextReplyBtn;
    public JButton prevReplyBtn;

    public JButton viewSelectedLikeBtn;
    public JButton viewSelectedRetBtn;

    public ImprovedJLabel replyText;

    public MainPanel mainPanel;
    public TimeLine timeLine;

    public int twittNum;
    public int replyNum;


    ClientMoveTwittListener clientMoveTwittListener;
    ClientMoveReplyListener clientMoveReplyListener;
    ClientReplyListener clientReplyListener;
    ClientRetwittListener clientRetwittListener;
    ClientTwittViewListener clientTwittViewListener;
    ClientUserViewListener clientUserViewListener;
    ClientLikeListener clientLikeListener;
    ClientGoAuthorListener clientGoAuthorListener;


    public TimeLinePanel(MainPanel mainPanel, TimeLine timeLine) throws IOException {
        this.setLayout(null);
        this.mainPanel = mainPanel;
        this.timeLine = timeLine;
        twittNum = timeLine.getTwitts().size()-1;
        replyNum = 0;

        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setBackground(colorConfig.getColor01());
        this.setBounds(150, 0, frameConfig.getWidth() - 150, frameConfig.getHeight());
        this.setVisible(true);


        upBotton = new JButton("Up");
        upBotton.setText("UP");
        upBotton.setBounds(0, 0, 330, 20);
        upBotton.setVisible(true);
        upBotton.addActionListener(this);

        downBotton = new JButton("Down");
        downBotton.setText("Down");
        downBotton.setBounds(0, 530, 330, 20);
        downBotton.setVisible(true);
        downBotton.addActionListener(this);


        titrLable = new ImprovedJLabel();
        titrLable.setBounds(5, 30, 300, 20);
        titrLable.setVisible(true);

        textLable = new ImprovedJLabel();
        textLable.setBounds(5, 20, 300, 100);
        textLable.setAlignmentX(TextArea.LEFT_ALIGNMENT);
        textLable.setAlignmentY(TextArea.TOP_ALIGNMENT);
        textLable.setVisible(true);
        textLable.setMainPanel(mainPanel);

        likesLable = new JLabel();
        likesLable.setBounds(5, 170, 100, 50);
        likesLable.setVisible(true);

        imageLabel = new JLabel();
        imageLabel.setBounds(150, 30, Constants.picWidth, Constants.picHeight);
        imageLabel.setVisible(true);


        likeBtn = new JButton("like");
        likeBtn.setText("like");
        likeBtn.setBounds(5, 270, 100, 40);
        likeBtn.setVisible(true);
        likeBtn.addActionListener(this);

        likesComboBox = new JComboBox<>();
        likesComboBox.setBounds(5, 320, 100, 20);
        likesComboBox.setVisible(true);

        viewSelectedLikeBtn = new JButton("view");
        viewSelectedLikeBtn.setText("view");
        viewSelectedLikeBtn.setBounds(5, 340, 100, 20);
        viewSelectedLikeBtn.setVisible(true);
        viewSelectedLikeBtn.addActionListener(this);

        reTwittsLable = new JLabel();
        reTwittsLable.setBounds(110, 170, 100, 50);
        reTwittsLable.setVisible(true);

        reTwittBtn = new JButton("ReTwitt");
        reTwittBtn.setText("ReTwitt");
        reTwittBtn.setBounds(110, 270, 100, 40);
        reTwittBtn.setVisible(true);
        reTwittBtn.addActionListener(this);

        retwittsComboBox = new JComboBox<>();
        retwittsComboBox.setBounds(110, 320, 100, 20);
        retwittsComboBox.setVisible(true);

        viewSelectedRetBtn = new JButton("view");
        viewSelectedRetBtn.setText("view");
        viewSelectedRetBtn.setBounds(110, 340, 100, 20);
        viewSelectedRetBtn.setVisible(true);
        viewSelectedRetBtn.addActionListener(this);


        repliesLable = new JLabel();
        repliesLable.setBounds(220, 170, 100, 50);
        repliesLable.setVisible(true);

        replyBtn = new JButton("Reply");
        replyBtn.setText("Reply");
        replyBtn.setBounds(240, 220, 80, 40);
        replyBtn.setVisible(true);
        replyBtn.addActionListener(this);

        replyField = new JTextArea();
        replyField.setBounds(5, 220, 230, 40);
        replyField.setVisible(true);


        nextReplyBtn = new JButton();
        nextReplyBtn.setText("n");
        nextReplyBtn.setBounds(280, 370, 40, 150);
        nextReplyBtn.setVisible(true);
        nextReplyBtn.addActionListener(this);

        prevReplyBtn = new JButton();
        prevReplyBtn.setText("p");
        prevReplyBtn.setBounds(20, 370, 40, 150);
        prevReplyBtn.setVisible(true);
        prevReplyBtn.addActionListener(this);

        replyText = new ImprovedJLabel();
        replyText.setBounds(60, 360, 220, 100);
        replyText.setVisible(true);
        replyText.setMainPanel(mainPanel);

        goAuthorProfileBtn = new JButton("goAuthurProf");
        goAuthorProfileBtn.setText("goAuthurProf");
        goAuthorProfileBtn.setBounds(220, 270, 100, 40);
        goAuthorProfileBtn.setVisible(true);
        goAuthorProfileBtn.addActionListener(this);


        saveBtn = new JButton("saveBtn");
        saveBtn.setText("Save");
        saveBtn.setBounds(220, 320, 80, 20);
        saveBtn.setVisible(true);
        saveBtn.addActionListener(this);

        reportBtn = new JButton("reportBtn");
        reportBtn.setText("report");
        reportBtn.setBounds(220, 340, 80, 20);
        reportBtn.setVisible(true);
        reportBtn.addActionListener(this);

        goReply = new JButton("Show Reply");
        goReply.setText("Show\nReply");
        goReply.setBounds(185, 470, 80, 35);
        goReply.setVisible(true);
        goReply.addActionListener(this);


        this.add(titrLable);
        this.add(textLable);
        this.add(likesLable);
        this.add(reTwittsLable);
        this.add(repliesLable);
        this.add(upBotton);
        this.add(downBotton);
        this.add(replyBtn);
        this.add(reTwittBtn);
        this.add(likeBtn);
        this.add(likesComboBox);
        this.add(retwittsComboBox);
        this.add(nextReplyBtn);
        this.add(prevReplyBtn);
        this.add(replyField);
        this.add(replyText);
        this.add(goAuthorProfileBtn);
        this.add(saveBtn);
        this.add(goReply);
        this.add(viewSelectedLikeBtn);
        this.add(viewSelectedRetBtn);
        this.add(reportBtn);
        this.add(imageLabel);

        instance = this;

        initialize();

        clientMoveTwittListener = new ClientMoveTwittListener(timeLine, this);
        clientMoveReplyListener = new ClientMoveReplyListener(this);
        clientReplyListener = new ClientReplyListener(this,timeLine);
        clientLikeListener = new ClientLikeListener(this);
        clientRetwittListener = new ClientRetwittListener(this);
        clientGoAuthorListener = new ClientGoAuthorListener(this,mainPanel);
        clientUserViewListener = new ClientUserViewListener(mainPanel);
        clientTwittViewListener = new ClientTwittViewListener(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == upBotton) {
            clientMoveTwittListener.listen("up");
        }
        else if(e.getSource() == downBotton){
            clientMoveTwittListener.listen("down");
        }
        else if(e.getSource() == replyBtn){
            ReplyEvent replyEvent = new ReplyEvent(timeLine.getTwitts().get(twittNum),replyField.getText());
            try {
                clientReplyListener.listen(replyEvent);
                replyField.setText(" ");
                JOptionPane.showMessageDialog(this,"reply sent");

            } catch (SQLException | ClassNotFoundException | IOException | CouldNotConnectToServerException throwables) {
                throwables.printStackTrace();
            } catch (EmptyFieldException emptyFieldException) {
                JOptionPane.showMessageDialog(this,emptyFieldException.getMessage());

                emptyFieldException.printStackTrace();
            }
        }
        else if(e.getSource() == nextReplyBtn){
            clientMoveReplyListener.listen("next");
        }
        else if(e.getSource() == prevReplyBtn){
            clientMoveReplyListener.listen("prev");
        }
        else if(e.getSource() == likeBtn){
            try {
                clientLikeListener.listen();
            } catch (CouldNotConnectToServerException | SQLException | IOException | ClassNotFoundException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            } catch (ServerException serverException) {
                serverException.printStackTrace();
            }
        }
        else if(e.getSource() == reTwittBtn){
            try {
                clientRetwittListener.listen();
            } catch (CouldNotConnectToServerException | SQLException | IOException | ClassNotFoundException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            } catch (ServerException serverException) {
                JOptionPane.showMessageDialog(this,"already retwitted");
                serverException.printStackTrace();
            }
        }
        else if(e.getSource() == goAuthorProfileBtn){
            try {
                int idx = getTwittNum();
                Twitt twitt = getTimeLine().getTwitts().get(idx);

                UserViewEvent userViewEvent = new UserViewEvent(twitt.getAuthor(),mainPanel);
                clientUserViewListener.listen(userViewEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            }
        }
        else if(e.getSource() == viewSelectedLikeBtn){
            try {

                User user = new User();
                String username = likesComboBox.getSelectedItem().toString();
                if(username != null) {
                    user.setUserName(username);

                    UserViewEvent userViewEvent = new UserViewEvent(user, mainPanel);
                    clientUserViewListener.listen(userViewEvent);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            }
        }
        else if(e.getSource() == viewSelectedRetBtn){
            try {

                User user = new User();
                String username = retwittsComboBox.getSelectedItem().toString();
                if(user!= null) {
                    user.setUserName(username);

                    UserViewEvent userViewEvent = new UserViewEvent(user, mainPanel);
                    clientUserViewListener.listen(userViewEvent);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            }
        }
        else if(e.getSource() == goReply){

            try {
                TwittViewEvent twittViewEvent = new TwittViewEvent(timeLine.getTwitts().get(twittNum).getReplies().get(replyNum),mainPanel);
                clientTwittViewListener.listen(twittViewEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            }

        }
    }

    public void setReply(Twitt reply){
        replyText.setText(reply.getText());
        replyText.repaint();
        this.repaint();
    }


    public void updateReply(Twitt twitt) {
        timeLine.getTwitts().get(twittNum).getReplies().add(twitt);
        if(timeLine.getTwitts().get(twittNum).getReplies().size() == 1) {
            replyText.setText(twitt.getText());
            replyText.repaint();
            this.repaint();
        }
    }

    public void initialize() throws IOException {
        if(timeLine.getTwitts().size() > 0) {
            addTwittLikes();
            addTwittRetwitts();
            ClientTimeLineController clientTimeLineController = new ClientTimeLineController(timeLine);
            clientTimeLineController.saveAndSetStreams();
            titrLable.setText(timeLine.getTwitts().getLast().getAuthor().getUserName());
            textLable.setText(timeLine.getTwitts().getLast().getText());
            likesLable.setText("Likes:" + Integer.toString(timeLine.getTwitts().getLast().getLikes().size()));
            reTwittsLable.setText("Retwitts: " + Integer.toString(timeLine.getTwitts().getLast().getReTwitts().size()));
            repliesLable.setText("Replies: " + timeLine.getTwitts().getLast().getReplies().size());
            if (timeLine.getTwitts().getLast().getReplies().size() != 0) {
                replyText.setText(timeLine.getTwitts().getLast().getReplies().getFirst().getText());
            }
            if (timeLine.getTwitts().getLast().getPic() != null) {
                setImageLabel(timeLine.getTwitts().getLast().getPic());
            }
            imageLabel.repaint();
            this.repaint();
        }
    }

    public void increaseReplyNum(){
        replyNum++;
    }

    public void decreaseReplyNum(){
        replyNum--;
    }

    public void addTwittRetwitts() {
        retwittsComboBox.removeAllItems();
        for(int i = 0; i < timeLine.getTwitts().get(getTwittNum()).getTwittReTwittersList().size() ; i++){
            retwittsComboBox.addItem(timeLine.getTwitts().get(getTwittNum()).getTwittReTwittersList().get(i).getUserName());
        }
    }

    public void addTwittLikes() {
        getLikesComboBox().removeAllItems();
        for(int i = 0; i < timeLine.getTwitts().get(getTwittNum()).getLikes().size() ; i++){
            likesComboBox.addItem(timeLine.getTwitts().get(getTwittNum()).getLikes().get(i));
        }
    }

    public void setImageLabel(ImageIcon imageIcon){
        instance.imageLabel.setIcon(imageIcon);
        instance.imageLabel.revalidate();
        instance.imageLabel.repaint();
        instance.revalidate();
        instance.repaint();
        int a = 0;
    }

    public JButton getLikeBtn() {
        return likeBtn;
    }

    public JComboBox<String> getLikesComboBox() {
        return likesComboBox;
    }

    public int getTwittNum() {
        return twittNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public TimeLine getTimeLine() {
        return timeLine;
    }

    public JLabel getLikesLable() {
        return likesLable;
    }

    public void setLikesLable(JLabel likesLable) {
        this.likesLable = likesLable;
    }

    public JLabel getReTwittsLable() {
        return reTwittsLable;
    }

    public void setReTwittsLable(JLabel reTwittsLable) {
        this.reTwittsLable = reTwittsLable;
    }

    public JLabel getRepliesLable() {
        return repliesLable;
    }

    public void setRepliesLable(JLabel repliesLable) {
        this.repliesLable = repliesLable;
    }
}
