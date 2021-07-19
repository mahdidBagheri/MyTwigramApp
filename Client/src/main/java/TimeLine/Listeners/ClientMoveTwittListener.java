package TimeLine.Listeners;

import TimeLine.Model.TimeLine;
import TimeLine.View.TimeLinePanel;

import javax.swing.*;

public class ClientMoveTwittListener {
    TimeLine timeLine;
    TimeLinePanel timeLinePanel;

    public ClientMoveTwittListener(TimeLine timeLine, TimeLinePanel timeLinePanel) {
        this.timeLine = timeLine;
        this.timeLinePanel = timeLinePanel;
    }

    public void listen(String command) {
        if(command.equals("up")) {
            if(timeLinePanel.twittNum > 0) {
                timeLinePanel.twittNum -= 1;
                timeLinePanel.replyNum = 0;

                timeLinePanel.titrLable.setText(timeLine.getTwitts().get(timeLinePanel.twittNum).getAuthor().getUserName());
                timeLinePanel.textLable.setText(timeLine.getTwitts().get(timeLinePanel.twittNum).getText());
                timeLinePanel.likesLable.setText("Likes: " + Integer.toString(timeLine.getTwitts().get(timeLinePanel.twittNum).getLikes().size()));
                timeLinePanel.reTwittsLable.setText("Retwitts: " + Integer.toString(timeLine.getTwitts().get(timeLinePanel.twittNum).getReTwitts().size()));
                timeLinePanel.repliesLable.setText("Replies: " + timeLine.getTwitts().get(timeLinePanel.twittNum).getReplies().size());
                if(timeLine.getTwitts().get(timeLinePanel.twittNum).getReplies().size()!= 0){
                    timeLinePanel.replyText.setText(timeLine.getTwitts().getLast().getReplies().getFirst().getText());
                }
                else {
                    timeLinePanel.replyText.setText(" ");
                }
                if(timeLine.getTwitts().get(timeLinePanel.twittNum).getPic() != null){
                    timeLinePanel.setImageLabel(timeLine.getTwitts().get(timeLinePanel.twittNum).getPic());
                }
                else {
                    timeLinePanel.setImageLabel(timeLine.getTwitts().get(timeLinePanel.twittNum).getPic());
                }
                timeLinePanel.imageLabel.repaint();
                timeLinePanel.repaint();
            }
        }
        else if(command.equals("down")) {
            if(timeLinePanel.twittNum < timeLine.getTwitts().size()-1) {
                timeLinePanel.twittNum += 1;
                timeLinePanel.replyNum = 0;

                timeLinePanel.titrLable.setText(timeLine.getTwitts().get(timeLinePanel.twittNum).getAuthor().getUserName());
                timeLinePanel.textLable.setText(timeLine.getTwitts().get(timeLinePanel.twittNum).getText());
                timeLinePanel.likesLable.setText("Likes: " + Integer.toString(timeLine.getTwitts().get(timeLinePanel.twittNum).getLikes().size()));
                timeLinePanel.reTwittsLable.setText("Retwitts: " + Integer.toString(timeLine.getTwitts().get(timeLinePanel.twittNum).getReTwitts().size()));
                timeLinePanel.repliesLable.setText("Replies: " + timeLine.getTwitts().get(timeLinePanel.twittNum).getReplies().size());
                if(timeLine.getTwitts().get(timeLinePanel.twittNum).getReplies().size()!= 0){
                    timeLinePanel.replyText.setText(timeLine.getTwitts().get(timeLinePanel.twittNum).getReplies().getFirst().getText());
                }
                else {
                    timeLinePanel.replyText.setText(" ");
                }
                if(timeLine.getTwitts().get(timeLinePanel.twittNum).getPic() != null){
                    timeLinePanel.setImageLabel(timeLine.getTwitts().get(timeLinePanel.twittNum).getPic());

                }
                else {
                    timeLinePanel.setImageLabel(timeLine.getTwitts().get(timeLinePanel.twittNum).getPic());
                }
                timeLinePanel.imageLabel.repaint();
                timeLinePanel.repaint();
            }
        }
    }
}
