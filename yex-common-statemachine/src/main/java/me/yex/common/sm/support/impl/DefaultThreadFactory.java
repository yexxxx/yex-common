package me.yex.common.sm.support.impl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yves on 2018/9/5.
 */
public final class DefaultThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private String threadPoolName;
    private boolean daemon;

    public DefaultThreadFactory(String threadPoolName, boolean daemon) {
        this.threadPoolName = threadPoolName;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadNamePrefix = threadPoolName + "-thread-";
        Thread t = new Thread(r, threadNamePrefix + threadNumber.getAndIncrement());
        t.setDaemon(daemon);
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
