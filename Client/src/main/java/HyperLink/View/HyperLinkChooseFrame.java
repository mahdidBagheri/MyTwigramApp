package HyperLink.View;

import Config.FrameConfig.FrameConfig;
import HyperLink.Listeners.ChooseLinkListener;
import HyperLink.Model.ImprovedJLabel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class HyperLinkChooseFrame {
    ImprovedJLabel improvedJLabel;

    Dialog dialog;

    JButton OKBtn;
    JComboBox<String> linksCombo;

    JLabel titleLbl;
    ChooseLinkListener chooseLinkListener;

    JFrame f;

    public HyperLinkChooseFrame(ImprovedJLabel improvedJLabel) throws IOException {
        this.improvedJLabel = improvedJLabel;
        FrameConfig frameConfig = new FrameConfig();
        f= new JFrame();
        f.setLocation(150+frameConfig.getFrameXLoc(),100+frameConfig.getFrameYLoc());
        f.setLayout(null);
        chooseLinkListener = new ChooseLinkListener(this);

        OKBtn = new JButton ("OK");
        OKBtn.addActionListener (chooseLinkListener);
        OKBtn.setBounds(200,40,70,50);

        linksCombo = new JComboBox<>();
        linksCombo.setVisible(true);
        linksCombo.setBounds(10,40,150,50);
        addItems();

        dialog = new JDialog(f , "Dialog Example" );
        dialog.setLocation(150+frameConfig.getFrameXLoc(),100+frameConfig.getFrameYLoc());
        dialog.setLayout( null );
        dialog.setSize(300,150);
        dialog.setVisible(true);

        titleLbl = new JLabel();
        titleLbl.setText("choose a link: ");
        titleLbl.setBounds(10,0,200,30);

        dialog.add(titleLbl);
        dialog.add(OKBtn);
        dialog.add(linksCombo);
    }

    private void addItems() {
        linksCombo.removeAllItems();
        for (String link:improvedJLabel.getHyperlinks()){
            linksCombo.addItem(link);
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    public JButton getOKBtn() {
        return OKBtn;
    }

    public JFrame getF() {
        return f;
    }

    public ImprovedJLabel getImprovedJLabel() {
        return improvedJLabel;
    }

    public void setImprovedJLabel(ImprovedJLabel improvedJLabel) {
        this.improvedJLabel = improvedJLabel;
    }

    public String getSelectedLink(){
        return Objects.requireNonNull(linksCombo.getSelectedItem()).toString();
    }
}
