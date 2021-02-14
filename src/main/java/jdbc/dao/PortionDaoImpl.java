package jdbc.dao;

import jdbc.connection.DataSourceHelper;
import jdbc.model.Portion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PortionDaoImpl implements PortionDao {

    @Override
    public Portion findById(int id) {

        //сразу пишем в базу
        try (PreparedStatement statement=DataSourceHelper.getINSTANCE().getConnection()
                .prepareStatement("select * from portion where id =?")) {
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForPortion(resultSet);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }

    private Portion resultSetForPortion(ResultSet resultSet) throws SQLException {
        Portion portion= Portion.create(resultSet.getString("key"), resultSet.getString("value"));
        portion.setId(resultSet.getInt("id"));
        return portion;
    }

    @Override
    public Portion createPortion(String key, String value) {

        Portion portion = Portion.create(key, value);

        //сразу пишем в базу
        try (PreparedStatement statement=DataSourceHelper.getINSTANCE().getConnection()
                .prepareStatement("INSERT INTO 'portion' ('key', 'value') VALUES (?, ?);")) {
            statement.setString(1, portion.getKey());
            statement.setString(2, portion.getValue());
            statement.executeUpdate();

            //Получаем созданный id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            portion.setId(generatedKeys.getInt(1));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(portion.getId());
        return portion;
    }


}
