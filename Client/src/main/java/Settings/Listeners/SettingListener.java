package Settings.Listeners;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import LocalDataBase.SyncLocalDataBase;
import Settings.View.SettingPanel;
import User.Controller.ClientUserController;
import User.Model.User;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class SettingListener {
    SettingPanel settingPanel;

    public SettingListener(SettingPanel settingPanel) {
        this.settingPanel = settingPanel;
    }

    public void listen(User mainUser) throws SQLException, IOException, ClassNotFoundException {

        ClientUserController clientUserController = new ClientUserController(mainUser);
        try {
            clientUserController.getUser().setSync("false");
            clientUserController.saveUserInfo();
            SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
            syncLocalDataBase.syncUser();
            clientUserController.getUser().setSync("true");

            JOptionPane.showMessageDialog(settingPanel,"changed!");

        } catch (CouldNotConnectToServerException e) {
            clientUserController.getUser().setSync("false");
            clientUserController.saveUserInfo();
            JOptionPane.showMessageDialog(settingPanel,"could not send but saved");
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }
}
