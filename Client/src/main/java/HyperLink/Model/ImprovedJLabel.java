package HyperLink.Model;

import HyperLink.Listeners.MouseClickListener;
import MainFrame.View.MainPanel;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class ImprovedJLabel extends JLabel {
    LinkedList<String> hyperlinks = new LinkedList<>();
    MouseClickListener mouseClickListener;
    MainPanel mainPanel;

    public ImprovedJLabel() {
        super();
        mouseClickListener = new MouseClickListener(this);
        this.addMouseListener(new MouseClickListener(this));
    }

    public ImprovedJLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        this.setText(findHyperlinks(text));
        mouseClickListener = new MouseClickListener(this);
        this.addMouseListener(new MouseClickListener(this));
    }

    public ImprovedJLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.setText(findHyperlinks(text));
        mouseClickListener = new MouseClickListener(this);
        this.addMouseListener(new MouseClickListener(this));
    }

    public ImprovedJLabel(String text) {
        super(text);
        this.setText(findHyperlinks(text));
        mouseClickListener = new MouseClickListener(this);
        this.addMouseListener(new MouseClickListener(this));
    }

    public ImprovedJLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        mouseClickListener = new MouseClickListener(this);
        this.addMouseListener(new MouseClickListener(this));
    }

    public ImprovedJLabel(Icon image) {
        super(image);
        mouseClickListener = new MouseClickListener(this);
        this.addMouseListener(new MouseClickListener(this));
    }

    public void setText(String text){
        String resText = findHyperlinks(text);
        super.setText(resText);
    }

    public void setMainPanel(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }


    public String findHyperlinks(String text){
        LinkedList<String> stringParts = new LinkedList<>();

        String addHTML = "<html> " + text + " </html>";
        int lastStop = 0;
        int start = 1;

        for (int i = 1; i <addHTML.length() ; i++) {
            start = i;
            int stop = i+1;
            if(addHTML.charAt(i) == '@' ){
                while (addHTML.charAt(stop) != ' ' && addHTML.charAt(stop) != '@'){
                    stop++;
                }
                stringParts.add(addHTML.substring(lastStop,start));
                stringParts.add(addHTML.substring(start,stop));
                lastStop = stop;
                i = stop-1;
            }
        }
        stringParts.add(addHTML.substring(lastStop,addHTML.length()));

        String resultString = "";
        for (String subS : stringParts){
            if(subS.length() > 1 && subS.charAt(0) == '@'){
                hyperlinks.add(subS);
                subS = "<font color=blue><u>" + subS + "</u></font>" ;
            }
            resultString = resultString.concat(subS);
        }

        return resultString;
    }

    public LinkedList<String> getHyperlinks() {
        return hyperlinks;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
