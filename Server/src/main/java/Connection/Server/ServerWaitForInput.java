package Connection.Server;

import java.io.IOException;
import java.net.Socket;

public class ServerWaitForInput {
    volatile boolean isFinished = false;

    public ServerWaitForInput() {
    }
    public ServerWaitForInput(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void waitForInput(Socket socket) {

        long start = System.currentTimeMillis();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }


            try {

                if(isFinished){
                    break;
                }
                if (socket.getInputStream().available() == 0) {
                    continue;
                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            break;

        }
    }
}
