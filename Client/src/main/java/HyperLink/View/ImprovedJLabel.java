package HyperLink.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class ImprovedJLabel extends JLabel implements MouseListener {
    public ImprovedJLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        this.setText(findHyperlinks(text));
    }

    public ImprovedJLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.setText(findHyperlinks(text));
    }

    public ImprovedJLabel(String text) {
        super(text);
        this.setText(findHyperlinks(text));
    }

    public ImprovedJLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public ImprovedJLabel(Icon image) {
        super(image);
    }

    public ImprovedJLabel() {
        super();

    }

    @Override
    public void setText(String text){
        String resultText = findHyperlinks(text);
        super.setText(resultText);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
                while (addHTML.charAt(stop) == ' '){
                    stop++;
                }
                stringParts.add(addHTML.substring(lastStop,start));
                stringParts.add(addHTML.substring(start,stop));
                lastStop = stop;
                i = stop;
            }
        }
        stringParts.add(addHTML.substring(lastStop,addHTML.length()));

        String resultString = "";
        for (String subS : stringParts){
            if(subS.charAt(0) == '@'){
                subS = "<font color=blue><u>" + subS + "</u></font>" ;
            }
            resultString = resultString.concat(subS);
        }

        return resultString;
    }
}
