package primaryTasks;

import superCacheProxy.CachedInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ServiceImpl implements Service {
    @Override
    public Object doHardWork(Object t1, Object t2) {
        return "String{" + t1.toString() + ", " + t2.toString() + "}";
    }

    public static Service createNewProxy(){
        Service delegate=new ServiceImpl();
        Service service=(Service) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(), (InvocationHandler) new CachedInvocationHandler(delegate));
        return service;
    }
}
