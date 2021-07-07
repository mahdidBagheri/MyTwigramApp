package Connection.Utils;

import Connection.DataBaseConnection.ConnectionToDataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseUtils {
    public static boolean isEmptyTable(String TableName) throws SQLException, SQLException {
        ConnectionToDataBase connectionToServer = new ConnectionToDataBase();
        String sql = String.format("SELECT * FROM  \"%s\" LIMIT 1 ;", TableName);
        ResultSet rs = connectionToServer.executeQuery(sql);
        connectionToServer.Disconect();
        if (rs.next()) {
            return false;
        } else {
            return true;
        }
    }
}
