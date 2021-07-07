package ClientSingup.Exceptions;

public class UserNameStartsWithDigitException extends Exception {
    public UserNameStartsWithDigitException(String msg){
        super(msg);
    }
}
