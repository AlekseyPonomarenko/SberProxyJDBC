package jdbc.connection;

public class DataSourceHelper {

    public static final DataSourceHelper INSTANCE = new DataSourceHelper();



    private DataSourceHelper() {
    }

    public static DataSourceHelper getINSTANCE() {
        return INSTANCE;
    }
}
