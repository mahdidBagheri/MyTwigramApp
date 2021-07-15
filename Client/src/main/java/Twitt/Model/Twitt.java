package Twitt.Model;

import java.nio.charset.Charset;
import java.util.LinkedList;

public class Twitt {
    LinkedList<String> reports = new LinkedList<String>();
    String text;

    public void setTwittUUID(String UUID) {
    }

    public LinkedList<String> getReports() {
        return reports;
    }

    public void setReports(LinkedList<String> reports) {
        this.reports = reports;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
