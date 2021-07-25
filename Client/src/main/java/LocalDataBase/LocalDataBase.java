package LocalDataBase;

import Config.DataBaseConfig.DataBaseConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

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

            createFunctions();

            createAdditionalTables();
            DataBaseConfig dataBaseConfig = new DataBaseConfig();
            dataBaseConfig.saveDataBaseName(DBname);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Can not connect to server");
        } catch (ClassNotFoundException throwables){
            throwables.printStackTrace();
            System.out.println("class not found exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFunctions() throws ClassNotFoundException, SQLException {
        Class.forName(PostgresDriver);
        this.localDBConnection = DriverManager.getConnection(dataBaseUrl, UserName, PassWord);

        Statement statement = localDBConnection.createStatement();
        String sql = "CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\";";
        statement.executeUpdate( sql);

    }


    private void createAdditionalTables() throws SQLException, ClassNotFoundException {
        Class.forName(PostgresDriver);
        this.localDBConnection = DriverManager.getConnection(dataBaseUrl, UserName, PassWord);

        createTimeLineTable();
        createChatsTable();
        createGroupsTable();
        createUserInfoTable();
        createTablesPointToUsers();
        createTablesPointToTwitts();
        createLogTable();

        localDBConnection.close();
    }


    private void createGroupsTable() throws SQLException {
        String sql = String.format("create table \"GroupsTable\"(\"ChatAddress\" character varying (50),\"GroupName\" character varying (50), \"Date\" timestamp without time zone, \"state\" character varying (6) );");
        Statement statement = localDBConnection.createStatement();
        statement.executeUpdate(sql);
    }

    private void createChatsTable() throws SQLException {
        String sql = String.format("create table \"ChatsTable\"(\"ChatAddress\" character varying (50),\"Username\" character varying (50), \"Date\" timestamp without time zone, \"state\" character varying (6));");
        Statement statement = localDBConnection.createStatement();
        statement.executeUpdate(sql);
    }

    private void createTimeLineTable() throws SQLException {
        String sql = String.format("create table \"TimeLine\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"text\" text,\"Username\" character varying (50), \"Date\" timestamp without time zone,\"state\" character varying (6));");
        Statement statement = localDBConnection.createStatement();
        statement.executeUpdate(sql);
    }

    private void createUserInfoTable() throws SQLException {
        String sql = String.format("create table \"UserInfo\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"Username\" character varying (50), \"Pass\" character varying (50),\"Fname\" character varying (50),\"Lname\" character varying (50),\"Email\" character varying (50),\"Privacy\" character varying (50),\"BirthDate\" character varying (50),\"PhoneNumber\" character varying (50),\"Bio\" character varying (50),\"Status\" character varying (50),\"LastSeen\" character varying (50),\"LastSeenMode\" character varying (50), \"DateJoined\" timestamp without time zone,\"Activities\" character varying (50),\"ProfilePic\" character varying (200),\"Session\" character varying (50), \"sync\" character varying (6));");
        Statement statement = localDBConnection.createStatement();
        statement.executeUpdate(sql);
    }

    private void createTablesPointToUsers() throws SQLException {
        LinkedList<String> tables = new LinkedList<>();
        tables.add("followers");
        tables.add("followings");
        tables.add("blacklist");
        tables.add("mutes");

        for(String tablename : tables){
            String sql = String.format("create table \"%s\"(\"UUID\" uuid NOT NULL PRIMARY KEY, \"Username\" character varying (50), \"sync\" character varying (6));",tablename);
            Statement statement = localDBConnection.createStatement();
            statement.executeUpdate(sql);
        }

    }

    private void createTablesPointToTwitts() throws SQLException {
        LinkedList<String> tables = new LinkedList<>();
        tables.add("twitts");

        for(String tablename : tables){
            String sql = String.format("create table \"%s\"(\"TwittUUID\" uuid NOT NULL PRIMARY KEY, \"text\" text, \"Author\" character varying (50),\"LikesNum\" character varying (50),\"RetwittsNum\" character varying (50), \"sync\" character varying (6));",tablename);
            Statement statement = localDBConnection.createStatement();
            statement.executeUpdate(sql);
        }
    }

    private void createLogTable() throws SQLException {
        String sql = String.format("create table \"LogTable\"(\"ID\" SERIAL NOT NULL PRIMARY KEY,\"Level\" character varying (50), \"Content\" character varying (200),\"DateJoined\" timestamp without time zone);");
        Statement statement = localDBConnection.createStatement();
        statement.executeUpdate(sql);
    }






}
