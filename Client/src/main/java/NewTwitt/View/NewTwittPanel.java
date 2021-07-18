package NewTwitt.View;

import Config.ColorConfig.ColorConfig;
import MainFrame.View.MainPanel;
import NewTwitt.Events.NewTwittEvent;
import NewTwitt.Exceptions.NewTwittException;
import NewTwitt.Listener.NewTwittListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import Constants.*;

public class NewTwittPanel extends JPanel implements ActionListener {
    private static NewTwittPanel instance = null;
    JButton TwittBotton;
    JButton backBotton;
    JTextArea newTwittField;
    JButton choosePicBtn;
    JButton deletePicBtn;

    JFileChooser picChooser;

    JLabel picLable;

    NewTwittListener newTwittListener;

    String picPath;
    MainPanel mainPanel;

    public NewTwittPanel(MainPanel mainPanel) throws IOException {
        ColorConfig colorConfig = new ColorConfig();

        this.mainPanel = mainPanel;
        newTwittListener = new NewTwittListener(mainPanel);

        this.setLayout(null);
        this.setBounds(150, 0, 450, 600);
        this.setBackground(colorConfig.getColor01());
        this.setVisible(true);

        TwittBotton = new JButton("TwittBotton");
        TwittBotton.setText("Twitt");
        TwittBotton.setBounds(200, 255, 100, 35);
        TwittBotton.setVisible(true);
        TwittBotton.addActionListener(this);

        choosePicBtn = new JButton("pic");
        choosePicBtn.setText("Pic");
        choosePicBtn.setBounds(50, 255, 100, 35);
        choosePicBtn.setVisible(true);
        choosePicBtn.addActionListener(this);

        deletePicBtn = new JButton("pic");
        deletePicBtn.setText("delete Pic");
        deletePicBtn.setBounds(50, 290, 100, 35);
        deletePicBtn.setVisible(true);
        deletePicBtn.addActionListener(this);

        backBotton = new JButton("backBotton");
        backBotton.setText("Back");
        backBotton.setBounds(50, 255, 100, 35);
        backBotton.setVisible(true);
        backBotton.addActionListener(this);

        newTwittField = new JTextArea();
        newTwittField.setBounds(30, 100, 280, 150);
        newTwittField.setVisible(true);

        picLable = new JLabel();
        picLable.setVisible(true);
        picLable.setBounds(10, 300, 150, 150);

        String picPath;

        this.add(TwittBotton);
        this.add(newTwittField);
        this.add(choosePicBtn);
        this.add(picLable);
        this.add(deletePicBtn);

        instance = this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == choosePicBtn) {
            try {
                picChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGE FILES", "PNG", "png", "jpg", "jpeg");
                picChooser.setFileFilter(filter);
                int returnValue = picChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = picChooser.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    System.out.println(path);

                    picPath = path;

                    ImageIcon myImage = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(Constants.picWidth, Constants.picHeight, Image.SCALE_DEFAULT));


                    instance.remove(picLable);
                    picLable = new JLabel(myImage);
                    picLable.setVisible(true);
                    picLable.setBounds(50, 350, Constants.picWidth, Constants.picHeight);
                    instance.add(picLable);

                    instance.revalidate();
                    picLable.revalidate();
                    instance.repaint();
                    picLable.repaint();


                }
            } catch (HeadlessException headlessException) {
                headlessException.printStackTrace();
            }

        }

        else if (e.getSource() == TwittBotton) {

            NewTwittEvent newTwittEvent = new NewTwittEvent(getNewTwittField(), "Twitt", "", picPath);

            picPath = null;
            this.remove(picLable);
            picLable = new JLabel();
            picLable.setVisible(true);
            picLable.setBounds(10, 300, 150, 150);
            this.add(picLable);

            this.repaint();
            newTwittField.setText(" ");
            try {
                newTwittListener.listen(newTwittEvent);
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"successfully twitted");
            } catch (NewTwittException newTwittException) {
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,newTwittException.getMessage());
                System.out.println(newTwittException.getMessage());
            } catch (SQLException | IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"local file error");

            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
        else if(e.getSource() == deletePicBtn){
            picPath = null;

            instance.remove(picLable);
            picLable = new JLabel();
            picLable.setVisible(true);
            picLable.setBounds(50, 350, Constants.picWidth, Constants.picHeight);
            instance.add(picLable);

            instance.revalidate();
            picLable.revalidate();
            instance.repaint();
            picLable.repaint();
        }
    }

    private String getNewTwittField() {
        return newTwittField.getText();
    }
}
