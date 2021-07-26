package Settings.Listeners;

import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import LocalDataBase.SyncLocalDataBase;
import Settings.View.SettingPanel;
import User.Model.User;

public class ReconnectListener {
    SettingPanel settingPanel;

    public ReconnectListener(SettingPanel settingPanel) {
        this.settingPanel = settingPanel;
    }

    public void listen(User mainUser) throws Throwable {
        SyncLocalDataBase syncLocalDataBase = new SyncLocalDataBase();
        syncLocalDataBase.syncUser();
        mainUser.setSync("true");
    }
}
