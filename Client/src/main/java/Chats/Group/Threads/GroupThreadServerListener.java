package Chats.Group.Threads;

import Chats.Group.Controller.ClientGroupController;
import Chats.Group.View.GroupPanel;
import Chats.PV.Controller.PVController;
import Chats.PV.View.PVPanel;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import LocalDataBase.SyncLocalDataBase;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class GroupThreadServerListener extends Thread {
    GroupPanel groupPanel;
    boolean isRunning = true;

    ClientConnection clientConnection;
    boolean tryConnection = true;
    SyncLocalDataBase syncLocalDataBase;

    public GroupThreadServerListener(GroupPanel groupPanel) throws Throwable {
        this.groupPanel = groupPanel;

        this.syncLocalDataBase = new SyncLocalDataBase();

        while (!isRunning) {
            try {
                this.clientConnection = new ClientConnection();
                break;
            } catch (CouldNotConnectToServerException e) {
                e.printStackTrace();
                Thread.sleep(1000);
                continue;
            }
        }
    }

    @Override
    public void run() {
        while (isRunning) {

            if (tryConnection) {
                try {
                    syncLocalDataBase.syncGroupMessagesAndMemmbers(groupPanel.getGroup().getGroupTableAddress());
                } catch (Throwable throwable) {
                    tryConnection = false;
                    JOptionPane.showMessageDialog(groupPanel,throwable.getMessage());
                    throwable.printStackTrace();
                }
            }

            try {
                ClientGroupController clientGroupController = new ClientGroupController(groupPanel.getGroup());
                clientGroupController.readMessages();

                groupPanel.updateGroupPanel();
                clientGroupController.finalize();
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
