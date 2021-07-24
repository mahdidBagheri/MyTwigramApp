package TimeLine.Listeners;

import MainFrame.View.MainPanel;
import TimeLine.View.TimeLinePanel;
import Twitt.Model.Twitt;

public class ClientTimeLineMoveReplyListener {
    TimeLinePanel timeLinePanel;
    Twitt twitt;

    public ClientTimeLineMoveReplyListener(TimeLinePanel timeLinePanel) {
        this.timeLinePanel = timeLinePanel;
        this.twitt = timeLinePanel.getTimeLine().getTwitts().get(timeLinePanel.getTwittNum());
    }

    public void listen(String command) {
        if(command.equals("next")){
            if(timeLinePanel.getReplyNum() < twitt.getReplies().size() - 1){
                timeLinePanel.increaseReplyNum();
                timeLinePanel.setReply(twitt.getReplies().get(timeLinePanel.getReplyNum()));
            }

        }
        else if(command.equals("prev")){
            if(timeLinePanel.getReplyNum() > 0 ){
                timeLinePanel.decreaseReplyNum();
                timeLinePanel.setReply(twitt.getReplies().get(timeLinePanel.getReplyNum()));
            }

        }

    }
}
