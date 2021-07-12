package User.Model;

import Twitt.Model.Twitt;

import java.io.Serializable;

public class Activity implements Serializable {
    String UUID;
    Twitt twitt;
    String type;
    String date;

    public Twitt getTwitt() {
        return twitt;
    }

    public void setTwitt(Twitt twitt) {
        this.twitt = twitt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
