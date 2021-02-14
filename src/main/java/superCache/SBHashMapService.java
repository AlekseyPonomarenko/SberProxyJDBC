package superCache;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class SBHashMapService {
    static private Map <Object, Object> resultByArg = new ConcurrentHashMap <>();
    static final private String catalog = "D:\\Temp";
    static final private String nameLastversionMap = "lastVersion.sbdat";

    static final ReentrantLock locker = new ReentrantLock(); // создаем заглушку

    static void newTempCatalog() {
        File file = new File(catalog);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    static public Object put(Object key, Object value, Method invokeMethod) {

        System.out.println("Добавили SBHashMap:" + value);
        ParamsForCache paramsForCache = new ParamsForCache(invokeMethod);

        if (paramsForCache.cacheType == Cache.СacheType.FILE) {
            newTempCatalog();

            String newFileName = paramsForCache.prefix + "_" + UUID.randomUUID().toString() + ".sbdat";
            String fullFileName = catalog + "\\" + newFileName;

            //сериализовать
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFileName))) {
                oos.writeObject(value);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            value = newFileName;
        }

        return resultByArg.put(key, value);
    }

    static public Object get(Object key, Method invokeMethod) {

        Object result;

        result = resultByArg.get(key);
        ParamsForCache paramsForCache = new ParamsForCache(invokeMethod);

        if (paramsForCache.cacheType == Cache.СacheType.FILE) {

            String fullFileName = catalog + "\\" + result;

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullFileName))) {
                result = (Object) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return result;
        }

        System.out.println("Получили из SBHashMap " + result);
        return result;

    }

    static public boolean containsKey(Object key) {

        return resultByArg.containsKey(key);
    }

    static public boolean saveLastVersion() {

        String fullFileName = catalog + "\\" + nameLastversionMap;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFileName))) {
            oos.writeObject(resultByArg);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    static public boolean loadLastVersion() {

        String fullFileName = catalog + "\\" + nameLastversionMap;
        File file = new File(fullFileName);

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullFileName))) {
                resultByArg = (Map<Object, Object>) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }
            System.out.println("Кэш загружен из файлового хранилища");
            return true;
        }

        return false;
    }
}
