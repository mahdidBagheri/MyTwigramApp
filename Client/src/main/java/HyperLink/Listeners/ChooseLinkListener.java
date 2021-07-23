package HyperLink.Listeners;

import Connection.Exceptions.CouldNotConnectToServerException;
import HyperLink.Controller.HyperLinkController;
import HyperLink.Model.Hyperlink;
import HyperLink.View.HyperLinkChooseFrame;
import MainFrame.View.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class ChooseLinkListener implements ActionListener {
    HyperLinkChooseFrame hyperLinkChooseFrame;

    public ChooseLinkListener(HyperLinkChooseFrame hyperLinkChooseFrame) {
        this.hyperLinkChooseFrame = hyperLinkChooseFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == hyperLinkChooseFrame.getOKBtn()){
            MainPanel mainPanel = hyperLinkChooseFrame.getImprovedJLabel().getMainPanel();
            String link = hyperLinkChooseFrame.getSelectedLink();
            Hyperlink hyperlink = new Hyperlink(link,mainPanel);
            HyperLinkController hyperLinkController = new HyperLinkController(hyperlink);
            try {
                hyperLinkController.goToLink();
            } catch (CouldNotConnectToServerException couldNotConnectToServerException) {
                couldNotConnectToServerException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

            hyperLinkChooseFrame.getDialog().setVisible(false);
            hyperLinkChooseFrame.getF().dispose();
        }
        else {
            hyperLinkChooseFrame.getDialog().setVisible(false);
            hyperLinkChooseFrame.getF().dispose();
        }
    }
}
