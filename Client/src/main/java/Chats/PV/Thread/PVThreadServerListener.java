package Chats.PV.Thread;

import Chats.PV.Controller.PVController;
import Chats.PV.View.PVPanel;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import LocalDataBase.SyncLocalDataBase;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class PVThreadServerListener extends Thread {
    ClientConnection clientConnection;
    boolean isRunning = true;
    boolean tryConnection = true;
    PVPanel pvPanel;
    SyncLocalDataBase syncLocalDataBase;

    public PVThreadServerListener(PVPanel pvPanel) throws Throwable {
        this.pvPanel = pvPanel;
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

    public void run() {
        while (isRunning) {

            if (tryConnection) {
                try {
                    syncLocalDataBase.syncPV(pvPanel.getPv().getPVTableName());
                } catch (Throwable throwable) {
                    tryConnection = false;
                    JOptionPane.showMessageDialog(pvPanel,throwable.getMessage());
                    throwable.printStackTrace();
                }
            }

            try {
                PVController pvController = new PVController(pvPanel.getPv());
                pvController.readMessages();
                pvPanel.updatePV();
                pvController.finalize();
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



    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void setTryConnection(boolean tryConnection) {
        this.tryConnection = tryConnection;
    }

    public void finalize() throws Throwable {
        setRunning(false);
        if (!clientConnection.getSocket().isClosed()) {
            clientConnection.getSocket().close();
        }
        super.finalize();
    }
}
