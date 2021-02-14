package superCacheProxy.cacheMap;

import superCacheProxy.ParamsForCache;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MapResultsImplFile implements MapResults {

    static private Map <Object, String> resultByFiles = new ConcurrentHashMap <>();


    static final private String catalog = "D:\\Temp";
    static final private String nameLastversionMap = "lastVersion.sbdat";


    static void newTempCatalog() {
        File file = new File(catalog);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public Object put(Object key, Object value, Method invokeMethod) {
        newTempCatalog();

        ParamsForCache paramsForCache = new ParamsForCache(invokeMethod);
        String newFileName = paramsForCache.prefix + "_" + UUID.randomUUID().toString() + ".sbdat";
        String fullFileName = catalog + "\\" + newFileName;

        //сериализовать
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFileName))) {
            oos.writeObject(value);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultByFiles.put(key, newFileName);
    }

    @Override
    public Object get(Object key, Method invokeMethod) {

        Object result;
        ParamsForCache paramsForCache=new ParamsForCache(invokeMethod);

        result = resultByFiles.get(key);
        String fullFileName=catalog + "\\" + result;

        try (ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fullFileName))) {
            result=(Object) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    @Override
    public boolean containsKey(Object key, Method invokeMethod) {
        return resultByFiles.containsKey(key);
    }


    static private boolean saveLastVersionFile() {

        String fullFileName = catalog + "\\" + nameLastversionMap;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFileName))) {
            oos.writeObject(resultByFiles);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    static public boolean loadLastVersionFile() {

        String fullFileName = catalog + "\\" + nameLastversionMap;
        File file = new File(fullFileName);

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullFileName))) {
                resultByFiles = (Map<Object, String>) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }
            System.out.println("Кэш загружен из файлового хранилища");
            return true;
        }

        return false;
    }

    @Override
    public void saveCache() throws SQLException, IOException, ClassNotFoundException {
        saveLastVersionFile();
    }

    @Override
    public void loadCache() throws SQLException, IOException, ClassNotFoundException {
        loadLastVersionFile();
    }
}
