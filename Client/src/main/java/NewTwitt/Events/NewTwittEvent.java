package NewTwitt.Events;

public class NewTwittEvent {
    String text;
    String type;
    String parent;
    String picPath;

    public NewTwittEvent(String text, String type, String parent, String picPath) {
        this.text = text;
        this.type = type;
        this.parent = parent;
        this.picPath = picPath;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public String getParent() {
        return parent;
    }

    public String getPicPath() {
        return picPath;
    }
}
