package com.app.framework.mvp;

/**
 * Created by lowen on 14/08/2017 21:42
 */

public interface RepositoryManager {

    /**
     * 获取Retrofit Service对象实例
     *
     * @param service Retrofit Service 接口Class
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * 获取RxCache Service对象实例
     *
     * @param cache
     * @param <T>
     * @return
     */
    <T> T obtainRxCacheService(Class<T> cache);

}
