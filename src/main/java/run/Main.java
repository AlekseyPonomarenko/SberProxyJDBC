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

        Portion model3  =portionDao.findById(5);
        System.out.println(model3.getValue());





        //Стоп
        try {
            DataSourceHelper.getINSTANCE().CloseDB();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        //https://coderoad.ru/134492/%D0%9A%D0%B0%D0%BA-%D1%81%D0%B5%D1%80%D0%B8%D0%B0%D0%BB%D0%B8%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D1%8C-%D0%BE%D0%B1%D1%8A%D0%B5%D0%BA%D1%82-%D0%B2-%D1%81%D1%82%D1%80%D0%BE%D0%BA%D1%83

       /* System.out.println("Старт программы");
        Portion model = Portion.create("1", "test");
        String string = toString(model);
        System.out.println(" Encoded serialized version " );
        System.out.println( string );
        Portion some = ( Portion ) fromString( string );
        System.out.println( "\n\nReconstituted object");
        System.out.println( some );*/
    }




}
