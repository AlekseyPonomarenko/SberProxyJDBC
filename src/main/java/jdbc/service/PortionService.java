package jdbc.service;

import jdbc.connection.DataSourceHelper;

import java.sql.SQLException;
import java.sql.Statement;

public class PortionService {

    public static void createSqlTables() throws SQLException {

        Statement statement = DataSourceHelper.getINSTANCE().getConnection().createStatement();
        statement.execute("CREATE TABLE if not exists 'portion' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'key' text, 'value' text);");
        System.out.println("CREATE TABLE if not exists 'portion'");
        statement.close();

    }
}
