package primaryTasks;

import superCache.Cache;

public interface Service {
    @Cache(cacheType = Cache.СacheType.IN_MEMORY, fileNamePrefix = "data")
    Object doHardWork (Object t1, Object t2);
}
