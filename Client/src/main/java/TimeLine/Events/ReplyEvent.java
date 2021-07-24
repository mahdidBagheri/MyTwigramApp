package TimeLine.Events;

import Twitt.Model.Twitt;

public class ReplyEvent {
    Twitt parentTwitt;
    String text;
    Twitt newTwitt;

    public ReplyEvent(Twitt parentTwitt, String text) {
        this.parentTwitt = parentTwitt;
        this.text = text;
    }

    public Twitt getParentTwitt() {
        return parentTwitt;
    }

    public String getText() {
        return text;
    }

    public Twitt getNewTwitt() {
        return newTwitt;
    }

    public void setNewTwitt(Twitt newTwitt) {
        this.newTwitt = newTwitt;
    }
}
