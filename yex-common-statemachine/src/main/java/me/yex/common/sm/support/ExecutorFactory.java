package me.yex.common.sm.support;

import me.yex.common.sm.support.impl.DefaultThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorFactory {

    public static ExecutorService makeExecutor(String name) {
        Logger logger = LoggerFactory.getLogger(name);
        ThreadPoolConfig config = new ThreadPoolConfig(name);
        config.setMaxQueueSize(1000);
        config.setMaxPoolSize(60);
        config.setPoolSize(60);
        config.setKeepAliveTime(300L);
        config.setTimeUnit(TimeUnit.SECONDS);
        config.setAllowCoreThreadTimeOut(false);
        config.setRejectedHandler((r, executor) -> {
            logger.error("queue is full:{}", name);
        });
        ThreadPoolFactory threadPoolFactory = new DefaultThreadPoolFactory();
        return threadPoolFactory.newThreadPool(config);
    }


}
