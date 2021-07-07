import MainFrame.View.MainFrameView;

import javax.swing.*;
import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainFrameView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
