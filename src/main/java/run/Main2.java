package run;

import jdbc.connection.DataSourceHelper;
import jdbc.service.PortionService;
import primaryTasks.Service;
import primaryTasks.ServiceImpl;
import superCache.Cache;
import superCache.ParamsForCache;
import superCache.SBHashMapService;

import java.io.IOException;
import java.sql.SQLException;

public class Main2 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException, IOException {


        Cache.СacheType сacheType = Cache.СacheType.SQLITE;


        if (сacheType == Cache.СacheType.SQLITE) {
            //Старт
            DataSourceHelper.getINSTANCE().Connect();

            //Первичная инициализация таблиц
            PortionService.createSqlTables();
        }

        Service service = ServiceImpl.createNewProxy();

        //Загрузка кэша из бд
        SBHashMapService.loadCash(сacheType);

        for (int i=0; i < 10; i++) {
            service.doHardWork("key" + i, "value" + i);
        }

        for (int i=0; i < 10; i++) {
            service.doHardWork("key" + i, "value" + i);
        }


        if (сacheType == Cache.СacheType.SQLITE){
            //Стоп
            DataSourceHelper.getINSTANCE().CloseDB();
        }
        else if (сacheType == Cache.СacheType.FILE){
            SBHashMapService.saveCash(сacheType);
        }

    }

}
