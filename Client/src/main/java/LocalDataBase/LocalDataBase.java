package LocalDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LocalDataBase {
    String UserName = "postgres";
    String PassWord = "1";
    String jdbsURL = "jdbc:postgresql://localhost:5432/";
    String dataBaseUrl;
    String PostgresDriver = "org.postgresql.Driver";
    Connection connection;
    Connection localDBConnection;


    public void createLocalDataBase(String DBname) throws ClassNotFoundException, SQLException {
        try{
            this.dataBaseUrl = jdbsURL + DBname;
            Class.forName(PostgresDriver);
            this.connection = DriverManager.getConnection(jdbsURL, UserName, PassWord);

            Statement statement = connection.createStatement();
            String sql = "create database" + " \"" + DBname + "\" " +  ";";
            statement.executeUpdate( sql);
            //connection.close();

            createAdditionalTables();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Can not connect to server");
        } catch (ClassNotFoundException throwables){
            throwables.printStackTrace();
            System.out.println("class not found exception");
        }
    }


    private void createAdditionalTables() throws SQLException, ClassNotFoundException {
        Class.forName(PostgresDriver);
        this.localDBConnection = DriverManager.getConnection(dataBaseUrl, UserName, PassWord);

        createTimeLineTable();
        createChatsTable();
        createGroupsTable();
    }

    private void createGroupsTable() throws SQLException {
        String sql = String.format("create table \"GroupsTable\"(\"ChatAddress\" character varying (50),\"GroupName\" character varying (50), \"Date\" timestamp without time zone);");
        Statement statement = localDBConnection.createStatement();
        statement.executeUpdate(sql);
    }

    private void createChatsTable() throws SQLException {
        String sql = String.format("create table \"ChatsTable\"(\"ChatAddress\" character varying (50),\"Username\" character varying (50), \"Date\" timestamp without time zone);");
        Statement statement = localDBConnection.createStatement();
        statement.executeUpdate(sql);
    }

    private void createTimeLineTable() throws SQLException {
        String sql = String.format("create table \"TimeLine\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"text\" text,\"Username\" character varying (50), \"Date\" timestamp without time zone);");
        Statement statement = localDBConnection.createStatement();
        statement.executeUpdate(sql);
    }


}
