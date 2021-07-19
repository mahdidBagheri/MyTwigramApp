package Twitt.Listeners;

import TimeLine.Model.TimeLine;
import TimeLine.View.TimeLinePanel;

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
                if(timeLine.getTwitts().getLast().getReplies().size()!= 0){
                    timeLinePanel.replyText.setText(timeLine.getTwitts().getLast().getReplies().getFirst().getText());
                }
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
                if(timeLine.getTwitts().getLast().getReplies().size()!= 0){
                    timeLinePanel.replyText.setText(timeLine.getTwitts().getLast().getReplies().getFirst().getText());
                }
                timeLinePanel.repaint();
            }
        }
    }
}
