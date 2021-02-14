package jdbc.dao;

import jdbc.connection.DataSourceHelper;
import jdbc.model.Portion;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

public class PortionDaoImpl implements PortionDao {

    @Override
    public Portion findById(int id) {

        try (PreparedStatement statement=DataSourceHelper.getINSTANCE().getConnection()
                .prepareStatement("select * from portion where id =?")) {
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForPortion(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void loadCash(Map <Object, Integer> resultBySql) throws SQLException, IOException, ClassNotFoundException {

        try (PreparedStatement statement=DataSourceHelper.getINSTANCE().getConnection()
                .prepareStatement("select id, key from portion")) {

            statement.execute();

            ResultSet resultSet=statement.getResultSet();
            while (resultSet.next()) {
                resultBySql.put(fromStringBase64(resultSet.getString("key")), resultSet.getInt("id"));
            }

        } catch (SQLException throwables) {
            throw throwables;
        }

    }

    private Portion resultSetForPortion(ResultSet resultSet) throws SQLException, IOException, ClassNotFoundException {

        Portion portion= Portion.create(
                fromStringBase64(resultSet.getString("key")),
                fromStringBase64(resultSet.getString("value"))
        );
        portion.setId(resultSet.getInt("id"));
        return portion;

    }

    @Override
    public Portion createPortion(Object key, Object value) {

        Portion portion = Portion.create(key, value);

        //сразу пишем в базу
        try (PreparedStatement statement=DataSourceHelper.getINSTANCE().getConnection()
                .prepareStatement("INSERT INTO 'portion' ('key', 'value') VALUES (?, ?);")) {
            setStatementFromPortion(statement, portion);
            statement.executeUpdate();

            //Получаем созданный id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            portion.setId(generatedKeys.getInt(1));

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return portion;
    }
    private void setStatementFromPortion(PreparedStatement statement, Portion portion) throws SQLException, IOException {
        statement.setString(1, toStringBase64((Serializable) portion.getKey()));
        statement.setString(2, toStringBase64((Serializable) portion.getValue()));
    }

    /** Read the object from Base64 string. */
    private static Object fromStringBase64(String s ) throws IOException,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    private static String toStringBase64(Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

}
