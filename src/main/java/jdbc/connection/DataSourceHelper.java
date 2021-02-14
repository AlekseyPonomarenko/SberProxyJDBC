package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceHelper {

    public static final DataSourceHelper INSTANCE = new DataSourceHelper();

    private Connection connection;

    private DataSourceHelper() {
    }

    public static DataSourceHelper getINSTANCE() {
        return INSTANCE;
    }

    public void  Connect() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:cache1.s3db");
        System.out.println("База Подключена!");
    }

    public Connection getConnection() {
        return connection;
    }
    public void CloseDB() throws ClassNotFoundException, SQLException
    {
        connection.close();

        System.out.println("Соединения закрыты");
    }

}
