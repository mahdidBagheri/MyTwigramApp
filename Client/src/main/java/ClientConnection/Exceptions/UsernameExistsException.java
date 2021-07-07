package ClientConnection.Exceptions;

public class UsernameExistsException extends Exception {
    public UsernameExistsException(String msg){
        super(msg);
    }
}
