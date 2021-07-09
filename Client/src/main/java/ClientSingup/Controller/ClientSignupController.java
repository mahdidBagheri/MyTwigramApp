package ClientSingup.Controller;

import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Exceptions.EmailExistException;
import Connection.Exceptions.UsernameExistsException;
import ClientSingup.Events.SignupEvent;
import ClientSingup.Exceptions.EmptyFieldException;
import ClientSingup.Exceptions.PasswordsNotMatchException;
import ClientSingup.Exceptions.UserNameStartsWithDigitException;
import MainFrame.View.MainPanel;

import java.io.IOException;

public class ClientSignupController {
    MainPanel mainPanel;

    public ClientSignupController(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }

    public void validateSignup(SignupEvent signupEvent) throws PasswordsNotMatchException,
            EmptyFieldException, UserNameStartsWithDigitException, UsernameExistsException,
            IOException, EmailExistException, ClassNotFoundException, CouldNotConnectToServerException {

        if(!signupEvent.getPassword1().equals(signupEvent.getPassword2())){
            throw new PasswordsNotMatchException("Passwords do not match");
        }

        if(signupEvent.getPassword2().isEmpty()){
            throw new EmptyFieldException("password field is empty");
        }

        if(signupEvent.getUserName().isEmpty()){
            throw new EmptyFieldException("UserName is empty");
        }

        if(signupEvent.getEmail().isEmpty()){
            throw new EmptyFieldException("email field is empty");
        }

        if(Character.isDigit(signupEvent.getUserName().charAt(0))){
            throw new UserNameStartsWithDigitException(signupEvent.getUserName());
        }

        ckeckUsername(signupEvent);
        checkEmail(signupEvent);

    }

    private void ckeckUsername(SignupEvent signupEvent) throws CouldNotConnectToServerException, IOException, ClassNotFoundException, UsernameExistsException {
        ClientConnection clientConnection = new ClientConnection();
        ClientPayLoad payLoad = new ClientPayLoad();

        payLoad.getStringStringHashMap().put("username",signupEvent.getUserName());
        payLoad.getStringStringHashMap().put("password",signupEvent.getPassword1());
        payLoad.getStringStringHashMap().put("email",signupEvent.getEmail());

        ClientRequest clientRequest = new ClientRequest("signup",payLoad,null,"validate username",null,null);
        boolean isUsernameExists = clientConnection.executeBoolean(clientRequest);
        if(isUsernameExists){
            throw new UsernameExistsException("Username Exists");
        }
        clientConnection.getSocket().close();
    }

    private void checkEmail(SignupEvent signupEvent) throws CouldNotConnectToServerException, IOException, ClassNotFoundException, EmailExistException {
        ClientConnection clientConnection = new ClientConnection();

        ClientPayLoad payLoad = new ClientPayLoad();
        payLoad.getStringStringHashMap().put("username",signupEvent.getUserName());
        payLoad.getStringStringHashMap().put("password",signupEvent.getPassword1());
        payLoad.getStringStringHashMap().put("email",signupEvent.getEmail());
        ClientRequest clientRequest = new ClientRequest("signup",payLoad,null,"validate email",null,null);
        boolean isEmailExists = clientConnection.executeBoolean(clientRequest);
        if(isEmailExists){
            throw new EmailExistException("Email Exists");
        }
        clientConnection.getSocket().close();

    }

    public void signup(SignupEvent signupEvent) throws CouldNotConnectToServerException, IOException, ClassNotFoundException, EmailExistException {
        ClientConnection clientConnection = new ClientConnection();

        ClientPayLoad payLoad = new ClientPayLoad();
        payLoad.getStringStringHashMap().put("username",signupEvent.getUserName());
        payLoad.getStringStringHashMap().put("password",signupEvent.getPassword1());
        payLoad.getStringStringHashMap().put("email",signupEvent.getEmail());
        payLoad.getStringStringHashMap().put("birthDate",birthDate(signupEvent));
        payLoad.getStringStringHashMap().put("bio",signupEvent.getBio());
        payLoad.getStringStringHashMap().put("phoneNumber",signupEvent.getPhone());
        payLoad.getStringStringHashMap().put("firstName",signupEvent.getFirstName());
        payLoad.getStringStringHashMap().put("lastName",signupEvent.getLastName());
        ClientRequest clientRequest = new ClientRequest("signup",payLoad,null,"signup",null,null);
        clientConnection.execute(clientRequest);

    }

    public String birthDate(SignupEvent signUpEvent){
        if(signUpEvent.getBirthDay() == null){
            return null;
        }
        return signUpEvent.getBirthDay() + "-"
                + signUpEvent.getBirthMonth() + "-"
                + signUpEvent.getBirthYear() + " 00:00:00";
    }
}
