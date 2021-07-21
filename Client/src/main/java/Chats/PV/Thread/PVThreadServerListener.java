package Chats.PV.Thread;

import Chats.PV.Controller.PVController;
import Chats.PV.View.PVPanel;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import LocalDataBase.SyncLocalDataBase;

import java.io.IOException;
import java.sql.SQLException;

public class PVThreadServerListener extends Thread {
    ClientConnection clientConnection;
    boolean isRunning = true;
    PVPanel pvPanel;
    SyncLocalDataBase syncLocalDataBase;

    public PVThreadServerListener(PVPanel pvPanel) throws InterruptedException, SQLException, IOException, ClassNotFoundException {
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

    public void run(){
        while (isRunning) {
            try {
                syncLocalDataBase.syncPV(pvPanel.getPv().getPVTableName());
                PVController pvController = new PVController(pvPanel.getPv());
                pvController.readMessages();
                pvPanel.updatePV();
                Thread.sleep(2000);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (CouldNotConnectToServerException e) {
                e.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void finalize() throws Throwable {
        setRunning(false);
        if(!clientConnection.getSocket().isClosed()){
            clientConnection.getSocket().close();
        }
        super.finalize();
    }
}
