package ClientSearch.View;

import ClientSearch.Events.SearchEvent;
import ClientSearch.Listeners.SearchViewListener;
import User.Listener.ClientUserViewListener;
import Config.ColorConfig.ColorConfig;
import Config.FrameConfig.FrameConfig;
import Connection.Server.ServerRequest;
import MainFrame.View.MainPanel;
import User.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class SearchPanel extends JPanel implements ActionListener {
    private SearchPanel instance = null;
    private JTextArea searchField;
    private JButton searchBtn;
    private ResultPanel searchResultPanel;
    private JButton upBtn;
    private JButton downBtn;

    private SearchViewListener searchViewListener;
    private ClientUserViewListener userViewListener;

    LinkedList<User> searchResultList = new LinkedList<>();


    public SearchPanel(MainPanel mainPanel) throws IOException {
        ColorConfig colorConfig = new ColorConfig();
        FrameConfig frameConfig = new FrameConfig();

        this.setLayout(null);

        this.setBackground(colorConfig.getColor01());
        this.setBounds(150,0,frameConfig.getWidth(),frameConfig.getHeight());
        this.setVisible(true);


        instance = this;

        searchField = new JTextArea();
        searchField.setBounds(20, 20, 300, 50);
        searchField.setVisible(true);

        searchBtn = new JButton();
        searchBtn.setBounds(240, 80, 80, 40);
        searchBtn.setText("Search");
        searchBtn.setVisible(true);
        searchBtn.addActionListener(this);

        upBtn = new JButton();
        upBtn.setBounds(20, 130, 300, 20);
        upBtn.setText("Up");
        upBtn.setVisible(true);

        searchResultPanel = new ResultPanel(mainPanel);
        searchResultPanel.setUserViewListener(new ClientUserViewListener(mainPanel));


        downBtn = new JButton();
        downBtn.setBounds(20, 500, 300, 20);
        downBtn.setText("Down");
        downBtn.setVisible(true);


        this.add(searchField);
        this.add(searchBtn);
        this.add(upBtn);
        this.add(downBtn);
        this.add(searchResultPanel);

        searchViewListener = new SearchViewListener(mainPanel, instance);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            SearchEvent searchEvent = new SearchEvent( searchField.getText());
            try {
                searchViewListener.listen(searchEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }


        }
    }

    public void showSearchResult(ServerRequest serverRequest) throws IOException {
        if (serverRequest.getCommand().equals("found")) {
            searchResultPanel.setUser(serverRequest.getPayLoad().getUser());
        }
        else {
            searchResultPanel.showNotFound();
        }
    }
}
