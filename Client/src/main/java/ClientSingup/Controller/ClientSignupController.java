package ClientSingup.Controller;

import Config.PathConfig.PathConfig;
import Connection.Client.ClientPayLoad;
import Connection.Client.ClientRequest;
import Connection.Client.ClientWaitForInput;
import Connection.ClientConnection;
import Connection.Exceptions.CouldNotConnectToServerException;
import Connection.Exceptions.EmailExistException;
import Connection.Exceptions.UsernameExistsException;
import ClientSingup.Events.SignupEvent;
import ClientSingup.Exceptions.EmptyFieldException;
import ClientSingup.Exceptions.PasswordsNotMatchException;
import ClientSingup.Exceptions.UserNameStartsWithDigitException;
import Connection.Server.ServerRequest;
import LocalDataBase.ConnectionToLocalDataBase;
import MainFrame.View.MainPanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

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

    public void saveUserDataToLocalDataBase(SignupEvent signupEvent) throws SQLException, IOException, ClassNotFoundException, CouldNotConnectToServerException {
        ClientConnection clientConnection = new ClientConnection();
        ClientRequest clientRequest = new ClientRequest("signup",null,null,"userData",signupEvent.getUserName(),signupEvent.getPassword1());
        clientConnection.execute(clientRequest);

        ClientWaitForInput.waitForInput(clientConnection.getSocket());
        ObjectInputStream objectInputStream = new ObjectInputStream(clientConnection.getSocket().getInputStream());
        ServerRequest serverRequest = (ServerRequest) objectInputStream.readObject();

        ConnectionToLocalDataBase connectionToLocalDataBase = new ConnectionToLocalDataBase();

        PathConfig pathConfig = new PathConfig();

        String sqlQuery;
        String defaultProfilePath = pathConfig.getUsersPicsPath() + "//defaultProfilePic.PNG";
        if(serverRequest.getPayLoad().getUser().getBirthDate().isEmpty()){
            sqlQuery = String.format("insert into \"UserInfo\" (\"UUID\", " +
                            "\"Username\", " +
                            "\"Pass\"," +
                            "\"Fname\", " +
                            "\"Lname\"," +
                            "\"Email\"," +
                            "\"BirthDate\","+
                            "\"Bio\","+
                            "\"PhoneNumber\","+
                            "\"DateJoined\","+
                            "\"Privacy\","+
                            "\"Status\","+
                            "\"LastSeenMode\","+
                            "\"LastSeen\","+
                            "\"ProfilePic\"," +
                            "\"sync\")"+
                            "values ('%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'Public',"+
                            "'Active',"+
                            "'EveryOne',"+
                            "'%s'," +
                            "'%s'," +
                            "true);",
                    serverRequest.getPayLoad().getUser().getUserUUID(),
                    serverRequest.getPayLoad().getUser().getUserName(),
                    serverRequest.getPayLoad().getUser().getPassWord(),
                    serverRequest.getPayLoad().getUser().getfName(),
                    serverRequest.getPayLoad().getUser().getlName(),
                    serverRequest.getPayLoad().getUser().getEmail(),
                    serverRequest.getPayLoad().getUser().getBirthDate(),
                    serverRequest.getPayLoad().getUser().getBio(),
                    serverRequest.getPayLoad().getUser().getPhoneNumber(),
                    serverRequest.getPayLoad().getUser().getLastSeen(),
                    serverRequest.getPayLoad().getUser().getLastSeen(),
                    defaultProfilePath);

        }
        else {
            sqlQuery = String.format("insert into \"UserInfo\" (\"UUID\", " +
                            "\"Username\", " +
                            "\"Pass\"," +
                            "\"Fname\", " +
                            "\"Lname\"," +
                            "\"Email\"," +
                            "\"Bio\","+
                            "\"PhoneNumber\","+
                            "\"DateJoined\","+
                            "\"Privacy\","+
                            "\"Status\","+
                            "\"LastSeenMode\","+
                            "\"LastSeen\"," +
                            "\"ProfilePic\"," +
                            "\"sync\")"+
                            "values ('%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'%s',"+
                            "'Public',"+
                            "'Active',"+
                            "'EveryOne',"+
                            "'%s'," +
                            "'%s'," +
                            "true);",
                    serverRequest.getPayLoad().getUser().getUserUUID(),
                    serverRequest.getPayLoad().getUser().getUserName(),
                    serverRequest.getPayLoad().getUser().getPassWord(),
                    serverRequest.getPayLoad().getUser().getfName(),
                    serverRequest.getPayLoad().getUser().getlName(),
                    serverRequest.getPayLoad().getUser().getEmail(),
                    serverRequest.getPayLoad().getUser().getBio(),
                    serverRequest.getPayLoad().getUser().getPhoneNumber(),
                    serverRequest.getPayLoad().getUser().getLastSeen(),
                    serverRequest.getPayLoad().getUser().getLastSeen(),
                    defaultProfilePath);

        }

        connectionToLocalDataBase.executeUpdate(sqlQuery);
        connectionToLocalDataBase.Disconect();
    }
}
