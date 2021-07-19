package TimeLine.Events;

import Twitt.Model.Twitt;

public class ReplyEvent {
    Twitt parentTwitt;
    String text;

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
}
