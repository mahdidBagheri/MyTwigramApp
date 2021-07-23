package HyperLink.Model;

import MainFrame.View.MainPanel;

import java.io.Serializable;

public class Hyperlink implements Serializable {
    private String link;
    private MainPanel mainPanel;
    private String type;

    public Hyperlink(String link, MainPanel mainPanel) {
        this.link = link;
        this.mainPanel = mainPanel;

        if(link.startsWith("@PV")){
            type = "PV";
        }
        else if(link.startsWith("@Group")){
            type = "Group";
        }
        else if(link.startsWith("@Twitt")){
            type = "Twitt";
        }
        else {
            type = "User";
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
