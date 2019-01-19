package com.app.framework.mvp;

/**
 * Created by lowen on 30/03/2018.
 */

public class BaseRepository {

    private RepositoryManager repositoryManager;

    public BaseRepository(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    /**
     * 获取Retrofit Service对象实例
     *
     * @param service
     * @param <T>
     * @return
     */
    protected <T> T obtainRetrofitService(Class<T> service) {
        return repositoryManager.obtainRetrofitService(service);
    }

    /**
     * 获取RxCache Service对象实例
     *
     * @param service
     * @param <T>
     * @return
     */
    protected <T> T obtainRxCacheService(Class<T> service) {
        return repositoryManager.obtainRxCacheService(service);
    }

}
