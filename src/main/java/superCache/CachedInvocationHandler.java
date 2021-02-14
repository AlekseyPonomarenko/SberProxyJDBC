package superCache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CachedInvocationHandler implements InvocationHandler {

    private final Object delegate;
    private ReentrantLock locker;

    public CachedInvocationHandler(Object delegate) {
        this.delegate=delegate;
        this.locker = new ReentrantLock();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = null;
        boolean stopInvoke = false;
        Calendar metric1 =Calendar.getInstance();
        Calendar metric2 = null;

        if(method.isAnnotationPresent(Cache.class)) {

            if (SBHashMapService.containsKey(key(method, args), method)) {
                result = SBHashMapService.get(key(method, args), method);
            }
            else {
                locker.lock();
                try {
                    if (SBHashMapService.containsKey(key(method, args), method)) {
                        result = SBHashMapService.get(key(method, args), method);
                    } else {
                        result = method.invoke(delegate, args);
                        SBHashMapService.put(key(method, args), result, method);
                    }
                } finally {
                    locker.unlock();
                }
            }
            stopInvoke = true;
        }

        if (!stopInvoke) {
            result=method.invoke(delegate, args);
        }

        if(method.isAnnotationPresent(Metric.class)){
            metric2 = Calendar.getInstance();
            System.out.println("Время выполенения: " + (metric2.getTime().getTime()-metric1.getTime().getTime()) + " наносек");
        }

        return result;

    }

    private Object key(Method method , Object[] args){
        List <Object> key = new ArrayList <>();
        key.add(method.getName());
        key.addAll(Arrays.asList(args));
        return key;
    }
}
