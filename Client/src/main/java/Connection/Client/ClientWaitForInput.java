package Connection.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientWaitForInput {
    public static void waitForInput(Socket socket) {
        long start = System.currentTimeMillis();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            try {
                if (socket.getInputStream().available() == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            int a = 0;
            break;

        }
    }

}
