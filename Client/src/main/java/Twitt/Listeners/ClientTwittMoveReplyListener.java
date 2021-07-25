package Twitt.Listeners;

import TimeLine.View.TimeLinePanel;
import Twitt.Model.Twitt;
import Twitt.View.TwittPanel;

public class ClientTwittMoveReplyListener {
    TwittPanel twittPanel;
    Twitt twitt;

    public ClientTwittMoveReplyListener(TwittPanel twittPanel) {
        this.twittPanel = twittPanel;
        this.twitt = twittPanel.getTwitt();
    }

    public void listen(String command) {
        if(command.equals("next")){
            if(twittPanel.getReplyNumber() < twitt.getReplies().size() - 1){
                twittPanel.increaseReplyNum();
                twittPanel.setReply(twitt.getReplies().get(twittPanel.getReplyNumber()));
            }

        }
        else if(command.equals("prev")){
            if(twittPanel.getReplyNumber() > 0 ){
                twittPanel.decreaseReplyNum();
                twittPanel.setReply(twitt.getReplies().get(twittPanel.getReplyNumber()));
            }

        }

    }
}
