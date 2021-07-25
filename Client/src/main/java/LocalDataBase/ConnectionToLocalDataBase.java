package LocalDataBase;

import Config.DataBaseConfig.DataBaseConfig;

import java.io.IOException;
import java.sql.*;

public class ConnectionToLocalDataBase {

    String UserName = "postgres";
    String PassWord = "1";
    String jdbsURL = "jdbc:postgresql://localhost:5432/";
    String localjdbsURL;
    String PostgresDriver = "org.postgresql.Driver";
    Connection localDBConnection;


    public ConnectionToLocalDataBase() throws ClassNotFoundException, SQLException, IOException {
        DataBaseConfig dataBaseConfig = new DataBaseConfig();

        this.localjdbsURL = jdbsURL + dataBaseConfig.getDataBaseName();
        Class.forName(PostgresDriver);
        this.localDBConnection = DriverManager.getConnection(localjdbsURL, UserName, PassWord);

    }

    public void Disconect() throws SQLException {
        localDBConnection.close();
    }


    public void executeUpdate(String sql) throws SQLException {
        Statement statement = this.localDBConnection.createStatement();
        int i = statement.executeUpdate(sql);
        int a = 0;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = this.localDBConnection.createStatement();
        return statement.executeQuery(sql);
    }

    public void finalize() throws Throwable {
        localDBConnection.close();
        super.finalize();
    }


}
