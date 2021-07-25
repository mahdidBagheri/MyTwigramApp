package Twitt.Listeners;

import ClientLogin.Exceptions.EmptyFieldException;
import Connection.Exceptions.CouldNotConnectToServerException;
import NewTwitt.Controller.NewTwittController;
import NewTwitt.Events.NewTwittEvent;
import TimeLine.Events.ReplyEvent;
import TimeLine.Model.TimeLine;
import TimeLine.View.TimeLinePanel;

import java.io.IOException;
import java.sql.SQLException;

public class ClientReplyListener {

    public void listen(ReplyEvent replyEvent) throws Throwable {
        if(replyEvent.getText().isEmpty()){
            throw new EmptyFieldException("reply field is empty");
        }
        NewTwittEvent newTwittEvent = new NewTwittEvent(replyEvent.getText(),"reply",replyEvent.getParentTwitt().getTwittUUID(),null);
        NewTwittController newTwittsController = new NewTwittController();
        newTwittsController.newTwitt(newTwittEvent);
        replyEvent.setNewTwitt(newTwittsController.getNewTwitt());
    }
}
