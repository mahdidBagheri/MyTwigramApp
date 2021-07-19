package TimeLine.Controller;

import TimeLine.Model.TimeLine;
import Twitt.Controller.TwittsController;
import Twitt.Model.Twitt;

import java.io.IOException;

public class ClientTimeLineController {
    TimeLine timeLine;

    public ClientTimeLineController(TimeLine timeLine) {
        this.timeLine = timeLine;
    }


    public void saveAndSetStreams() throws IOException {
        for (Twitt twitt:timeLine.getTwitts()){
            TwittsController twittsController = new TwittsController(twitt);
            twittsController.saveAndSetStreamedPic();
        }
    }
}
