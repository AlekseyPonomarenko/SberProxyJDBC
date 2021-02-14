package run;

import jdbc.connection.DataSourceHelper;
import jdbc.dao.PortionDao;
import jdbc.dao.PortionDaoImpl;
import jdbc.model.Portion;
import jdbc.service.PortionService;

import java.io.*;
import java.sql.SQLException;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {

        //Старт
        try {
            DataSourceHelper.getINSTANCE().Connect();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return;
        }

        //Первичная инициализация таблиц
        try {
            PortionService.createSqlTables();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }

        PortionDao portionDao = new PortionDaoImpl();

        Portion model = portionDao.createPortion("key1", "val1");
        Portion model2 = portionDao.createPortion("key2", "val2");

        Portion model3 = portionDao.findById(5);
        System.out.println(model3.getValue());

        //Стоп
        try {
            DataSourceHelper.getINSTANCE().CloseDB();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }




}
