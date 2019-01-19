package com.app.framework.configuration;

import com.orhanobut.logger.Logger;

import java.io.File;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.JolyglotGenerics;

public class RxCacheBuilder extends RxCache.Builder {

    private File cacheDirectory;
    private JolyglotGenerics jolyglot;

    /**
     * RxCache 的缓存路径
     * <br/>
     * 请优先确保是否有读写权限
     *
     * @param cacheDirectory
     * @return
     */
    public RxCacheBuilder setCacheDirectory(File cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
        return this;
    }

    /**
     * 配置RxCache JolyglotGenerics <br/>
     * <p>
     * 框架已经默认实现Gson {@link GenericGsonSpeaker},已支持泛型解析
     *
     * @param jolyglot {@link JolyglotGenerics}的具体实现
     * @return
     */
    public RxCacheBuilder setJolyglotGenerics(JolyglotGenerics jolyglot) {
        this.jolyglot = jolyglot;
        return this;
    }

    public RxCache build() {
        if (cacheDirectory == null || jolyglot == null) {
            Logger.w("Not config RxCache, so RxCache will be not work.");
            return null;
        }
        return persistence(cacheDirectory, jolyglot);
    }
}
