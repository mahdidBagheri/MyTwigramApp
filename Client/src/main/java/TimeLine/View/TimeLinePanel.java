package TimeLine.View;

import ClientLogin.Exceptions.EmptyFieldException;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import Connection.Exceptions.CouldNotConnectToServerException;
import Constants.Constants;
import MainFrame.View.MainPanel;
import TimeLine.Controller.ClientTimeLineController;
import TimeLine.Events.ReplyEvent;
import TimeLine.Listeners.*;
import TimeLine.Model.TimeLine;
import Twitt.Model.Twitt;
import User.Listener.ClientUserViewListener;

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

    public JLabel textLable;
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

    public JLabel replyText;

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


        titrLable = new JLabel();
        titrLable.setBounds(5, 5, 300, 100);
        titrLable.setVisible(true);

        textLable = new JLabel();
        textLable.setBounds(5, 20, 300, 100);
        textLable.setAlignmentX(TextArea.LEFT_ALIGNMENT);
        textLable.setAlignmentY(TextArea.TOP_ALIGNMENT);
        textLable.setVisible(true);

        likesLable = new JLabel();
        likesLable.setBounds(5, 170, 100, 50);
        likesLable.setVisible(true);

        imageLabel = new JLabel();
        imageLabel.setBounds(150, 30, Constants.picWidth, Constants.picHeight);
        imageLabel.setVisible(true);


        likeBtn = new JButton("Like");
        likeBtn.setText("Like");
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

        replyText = new JLabel();
        replyText.setBounds(60, 360, 220, 150);
        replyText.setVisible(true);

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
        clientReplyListener = new ClientReplyListener(this,timeLine);

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
        else if(e.getSource() == nextReplyBtn){
            clientMoveReplyListener.listen("next");
        }
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

    public void setImageLabel(ImageIcon imageIcon){
        instance.imageLabel.setIcon(imageIcon);
        instance.imageLabel.revalidate();
        instance.imageLabel.repaint();
        instance.revalidate();
        instance.repaint();
        int a = 0;
    }


}
