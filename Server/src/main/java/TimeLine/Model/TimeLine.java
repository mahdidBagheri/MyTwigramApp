package TimeLine.Model;

import Twitt.Model.Twitt;

import java.io.Serializable;
import java.util.LinkedList;

public class TimeLine implements Serializable {
    LinkedList<Twitt> twitts = new LinkedList<>();

    public void addTwitt(Twitt twitt){
        twitts.add(twitt);
    }

    public void clearTimeLine(){
        twitts.clear();
    }

    public LinkedList<Twitt> getTwitts() {
        return twitts;
    }
}