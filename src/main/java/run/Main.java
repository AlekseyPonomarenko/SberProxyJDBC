package run;

import jdbc.connection.DataSourceHelper;
import jdbc.service.PortionService;
import primaryTasks.Service;
import primaryTasks.ServiceImpl;
import superCacheProxy.Cache;
import superCacheProxy.SBHashMapService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    private static Service service;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException, IOException {

        boolean testMulti = true;//Тестирование многопоточности

        Cache.СacheType сacheType = Cache.СacheType.SQLITE;

        if (сacheType == Cache.СacheType.SQLITE) {
            //Старт
            DataSourceHelper.getINSTANCE().Connect();

            //Первичная инициализация таблиц
            PortionService.createSqlTables();
        }

        service = ServiceImpl.createNewProxy();

        //Загрузка кэша из бд
        SBHashMapService.loadCash(сacheType);

        if (testMulti) {

            //генератор потоков
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            for (int i=0; i<10; i++ ) {
                for (int j = 0; j < 5; j++) {
                    MyRunnable task  = new MyRunnable("t" + i, i);
                    executor.execute(task);
                }
            }

            executor.shutdown();
            try {
                executor.awaitTermination(2, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            for (int i=0; i < 10; i++) {
                service.doHardWork("key" + i, "value" + i);
            }

            for (int i=0; i < 10; i++) {
                service.doHardWork("key" + i, "value" + i);
            }
        }


        if (сacheType == Cache.СacheType.SQLITE){
            //Стоп
            DataSourceHelper.getINSTANCE().CloseDB();
        }
        else if (сacheType == Cache.СacheType.FILE){
            SBHashMapService.saveCash(сacheType);
        }

    }

    public static Service getService() {
        return service;
    }
}
