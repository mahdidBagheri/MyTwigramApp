package Connection.Server;

public class ServerRequest {
    private String userName;
    private String command;
    private ServerPayLoad payLoad;

    public ServerRequest(String userName, String command, ServerPayLoad payLoad) {
        this.userName = userName;
        this.command = command;
        this.payLoad = payLoad;
    }

    public String getUserName() {
        return userName;
    }

    public String getCommand() {
        return command;
    }

    public ServerPayLoad getPayLoad() {
        return payLoad;
    }
}
