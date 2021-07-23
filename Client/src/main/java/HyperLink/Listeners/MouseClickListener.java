package HyperLink.Listeners;

import HyperLink.Model.ImprovedJLabel;
import HyperLink.View.HyperLinkChooseFrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MouseClickListener implements MouseListener {
    ImprovedJLabel improvedJLabel;

    public MouseClickListener(ImprovedJLabel improvedJLabel){
        this.improvedJLabel = improvedJLabel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
        try {
            CreateLinksFrame();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        improvedJLabel.setCursor(cursor);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
        improvedJLabel.setCursor(cursor);
    }

    private void CreateLinksFrame() throws IOException {
        new HyperLinkChooseFrame(improvedJLabel);
    }

}
