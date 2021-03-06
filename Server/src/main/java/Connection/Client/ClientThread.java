package Connection.Client;

import Connection.Server.ServerConnection;
import Connection.Server.ServerRequest;
import Connection.Server.ServerWaitForInput;
import ServerLogin.Listener.ServerLoginListener;
import ServerProfile.Listener.ServerProfileListener;
import ServerSearch.Listener.ServerSearchListener;
import ServerSignup.Listener.ServerSignupListener;
import User.Exceptions.alreadyFollowedException;
import User.Exceptions.notFollowingUserException;
import User.Exceptions.selfFollowException;
import User.Exceptions.sendFollowRequestException;
import User.Listener.ServerUserListener;
import User.Listener.UserViewListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ClientThread extends Thread{
    private ServerConnection serverConnection;

    public ClientThread(Socket socket) {
        this.serverConnection = new ServerConnection(socket);
    }


    @Override
    public void run() {
        try {
            ServerWaitForInput serverWaitForInput = new ServerWaitForInput();
            serverWaitForInput.waitForInput(serverConnection.getSocket());
            ObjectInputStream objectInputStream = new ObjectInputStream(serverConnection.getSocket().getInputStream());
            ClientRequest clientRequest = (ClientRequest) objectInputStream.readObject();


            if (clientRequest.getSource().equals("signup")) {
                ServerSignupListener signupListener = new ServerSignupListener(serverConnection);
                signupListener.listen(clientRequest);
            }
            if (clientRequest.getSource().equals("login")) {
                ServerLoginListener loginListener = new ServerLoginListener(serverConnection);
                loginListener.listen(clientRequest);
            }
            if (clientRequest.getSource().equals("profile")) {
                ServerProfileListener serverProfileListener = new ServerProfileListener(serverConnection);
                serverProfileListener.listen(clientRequest);
            }
            if (clientRequest.getSource().equals("search")) {
                ServerSearchListener serverSearchListener = new ServerSearchListener(serverConnection);
                serverSearchListener.listen(clientRequest);
            }
            if (clientRequest.getSource().equals("userView")) {
                UserViewListener userViewListener = new UserViewListener(serverConnection);
                userViewListener.listen(clientRequest);
            }
            if (clientRequest.getSource().equals("onUserAction")) {
                ServerUserListener userListener = new ServerUserListener(serverConnection);
                userListener.listen(clientRequest);
            }
        }catch (IOException | ClassNotFoundException | SQLException e){
            try {
                serverConnection.getSocket().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        } catch (User.Exceptions.unsuccessfullReadDataFromDatabase unsuccessfullReadDataFromDatabase) {
            unsuccessfullReadDataFromDatabase.printStackTrace();
        } catch (selfFollowException | sendFollowRequestException | alreadyFollowedException | notFollowingUserException e){
            ServerRequest serverRequest = new ServerRequest(null,"could not follow",null);
            try {
                serverConnection.execute(serverRequest);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
