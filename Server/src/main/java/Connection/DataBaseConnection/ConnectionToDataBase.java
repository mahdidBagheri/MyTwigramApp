package Connection.DataBaseConnection;

import java.sql.*;

public class ConnectionToDataBase {
    String UserName = "postgres";
    String PassWord = "1";
    String jdbsURL = "jdbc:postgresql://localhost:5432/TwigramServer06";
    String PostgresDriver = "org.postgresql.Driver";
    Connection connection;
    public ConnectionToDataBase(){
        try {
            Class.forName(PostgresDriver);
            this.connection = DriverManager.getConnection(jdbsURL, UserName, PassWord);

        } catch (SQLException throwables) {
            System.out.println("Can not connect to server");
        } catch (ClassNotFoundException throwables){
            System.out.println("class not found exception");
        }
    }

    public void Disconect() throws SQLException {
        connection.close();
    }


    public void executeUpdate(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
        int i = statement.executeUpdate(sql);

    }

    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(sql);
    }

    public void finalize() throws Throwable {
        connection.close();
        super.finalize();
    }
}
