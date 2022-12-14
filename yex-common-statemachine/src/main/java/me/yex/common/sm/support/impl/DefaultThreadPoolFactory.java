package me.yex.common.sm.support.impl;

import me.yex.common.sm.support.ThreadPoolConfig;
import me.yex.common.sm.support.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by yves on 2018/9/4.
 */

public class DefaultThreadPoolFactory implements ThreadPoolFactory {

    private final Logger LOGGER = LoggerFactory.getLogger(DefaultThreadPoolFactory.class);

    private final List<ExecutorService> executorServiceList = new CopyOnWriteArrayList<>();

    private final ThreadPoolConfig defaultThreadPoolConfig;

    private final int DEFAULT_SHUTDOWN_AWAIT_TERMINATION = 10000;

    private final static int MIN_SHUTDOWN_AWAIT_TERMINATION = 2000;

    public DefaultThreadPoolFactory() {
        defaultThreadPoolConfig = new ThreadPoolConfig(null);
        defaultThreadPoolConfig.setPoolSize(2);
        defaultThreadPoolConfig.setMaxPoolSize(4);
        defaultThreadPoolConfig.setKeepAliveTime(60L);
        defaultThreadPoolConfig.setTimeUnit(TimeUnit.SECONDS);
        defaultThreadPoolConfig.setMaxQueueSize(Integer.MAX_VALUE);
        defaultThreadPoolConfig.setAllowCoreThreadTimeOut(false);
        defaultThreadPoolConfig.setRejectedHandler(new ThreadPoolExecutor.AbortPolicy());
    }

    private ExecutorService createThreadPool(ThreadPoolConfig config) {
        int corePoolSize = config.getPoolSize(),
                maxPoolSize = config.getMaxPoolSize(),
                maxQueueSize = config.getMaxQueueSize();
        long keepAliveTime = config.getKeepAliveTime();
        TimeUnit timeUnit = config.getTimeUnit();
        RejectedExecutionHandler rejectedExecutionHandler = config.getRejectedHandler();

        if (corePoolSize < 0) {
            throw new IllegalArgumentException("CorePoolSize must be >= 0, was " + corePoolSize);
        }

        if (maxPoolSize < corePoolSize) {
            throw new IllegalArgumentException("MaxPoolSize must be >= corePoolSize, was " + maxPoolSize + " >= " + corePoolSize);
        }

        BlockingQueue<Runnable> workQueue;
        if (corePoolSize == 0 && maxQueueSize <= 0) {
            workQueue = new SynchronousQueue<>();
            corePoolSize = 1;
            maxPoolSize = 1;
        } else if (maxQueueSize <= 0) {
            workQueue = new SynchronousQueue<>();
        } else {
            workQueue = new LinkedBlockingQueue<>(maxQueueSize);
        }

        ThreadFactory threadFactory = createThreadFactory(config.getPoolName(), true);
        if (rejectedExecutionHandler == null) {
            rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, timeUnit, workQueue, threadFactory, rejectedExecutionHandler);
        executor.allowCoreThreadTimeOut(config.isAllowCoreThreadTimeOut());
        executorServiceList.add(executor);
        return executor;
    }

    private ExecutorService createCachedThreadPool(ThreadPoolConfig config) {
        ThreadFactory threadFactory = createThreadFactory(config.getPoolName(), true);
        ExecutorService executorService = Executors.newCachedThreadPool(threadFactory);
        executorServiceList.add(executorService);
        return executorService;
    }

    private ScheduledExecutorService createScheduledThreadPool(ThreadPoolConfig config) {
        RejectedExecutionHandler rejectedExecutionHandler = config.getRejectedHandler();
        if (rejectedExecutionHandler == null) {
            rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        }
        ThreadFactory threadFactory = createThreadFactory(config.getPoolName(), true);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(config.getPoolSize(),
                threadFactory, rejectedExecutionHandler);
        executor.setRemoveOnCancelPolicy(true);
        executorServiceList.add(executor);
        return executor;
    }

    @SuppressWarnings("SameParameterValue")
    private ThreadFactory createThreadFactory(String name, boolean isDaemon) {
        if (!name.startsWith("pool")) {
            name = "pool-" + name;
        }
        return new DefaultThreadFactory(name, isDaemon);
    }

    @Override
    public ExecutorService newThreadPool(ThreadPoolConfig config) {
        Objects.requireNonNull(config, "ThreadPoolConfig is null.");
        Objects.requireNonNull(config.getPoolName(), "Identity of Thread Pool is null.");
        return createThreadPool(config);
    }

    @Override
    public ExecutorService newThreadPool(String poolName, int poolSize, int maxPoolSize) {
        ThreadPoolConfig config = new ThreadPoolConfig(poolName).from(defaultThreadPoolConfig);
        config.setPoolName(poolName);
        config.setMaxPoolSize(maxPoolSize);
        config.setPoolSize(poolSize);
        return newThreadPool(config);
    }

    @Override
    public ExecutorService newSingleThreadExecutor(String poolName) {
        return newFixedThreadPool(poolName, 1);
    }

    @Override
    public ExecutorService newFixedThreadPool(String poolName, int poolSize) {
        ThreadPoolConfig config = new ThreadPoolConfig(poolName).from(defaultThreadPoolConfig);
        config.setPoolName(poolName);
        config.setPoolSize(poolSize);
        config.setMaxPoolSize(poolSize);
        config.setKeepAliveTime(0L);
        return newThreadPool(config);
    }

    @Override
    public ExecutorService newCachedThreadPool(String poolName) {
        Objects.requireNonNull(poolName, "Identity of Thread Pool is null.");
        ThreadPoolConfig config = new ThreadPoolConfig(poolName);
        return createCachedThreadPool(config);
    }

    @Override
    public ScheduledExecutorService newScheduledThreadPool(ThreadPoolConfig config) {
        return createScheduledThreadPool(config);
    }

    @Override
    public ScheduledExecutorService newScheduledThreadPool(String poolName, int poolSize) {
        ThreadPoolConfig config = new ThreadPoolConfig(poolName);
        config.setPoolSize(poolSize);
        return createScheduledThreadPool(config);
    }

    @Override
    public ScheduledExecutorService newSingleThreadScheduledExecutor(String poolName) {
        return newScheduledThreadPool(poolName, 1);
    }

    @Override
    public void shutdownGraceful(ExecutorService executorService) {
        doShutdown(executorService, DEFAULT_SHUTDOWN_AWAIT_TERMINATION, false);
    }

    @Override
    public void shutdownGraceful(ExecutorService executorService, long shutdownAwaitTermination) {
        doShutdown(executorService, shutdownAwaitTermination, false);
    }

    @Override
    public List<Runnable> shutdownNow(ExecutorService executorService) {
        return doShutdownNow(executorService, false);
    }

    private boolean doShutdown(ExecutorService executorService, long shutdownAwaitTermination, boolean failSafe) {
        if (executorService == null) {
            return false;
        }
        boolean warned = false;
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            if (shutdownAwaitTermination > 0) {
                try {
                    if (!awaitTermination(executorService, shutdownAwaitTermination)) {
                        warned = true;
                        LOGGER.warn("Forcing shutdown of ExecutorService: {} due first await termination elapsed.", executorService);
                        executorService.shutdownNow();
                        if (!awaitTermination(executorService, shutdownAwaitTermination)) {
                            LOGGER.warn("Cannot completely force shutdown of ExecutorService: {} due second await termination elapsed.",
                                    executorService);
                        }
                    }
                } catch (InterruptedException e) {
                    warned = true;
                    executorService.shutdownNow();
                }
            }
        }
        if (!failSafe) {
            executorServiceList.remove(executorService);
        }
        return warned;
    }


    @SuppressWarnings("SameParameterValue")
    private List<Runnable> doShutdownNow(ExecutorService executorService, boolean failSafe) {
        Objects.requireNonNull(executorService, "executorService");
        List<Runnable> neverCommencedTasks = null;
        if (!executorService.isShutdown()) {
            neverCommencedTasks = executorService.shutdownNow();
        }
        if (!failSafe) {
            executorServiceList.remove(executorService);
        }
        return neverCommencedTasks;
    }

    private boolean awaitTermination(ExecutorService executorService, long shutdownAwaitTermination)
            throws InterruptedException {
        long start = System.currentTimeMillis();
        long interval = Math.min(MIN_SHUTDOWN_AWAIT_TERMINATION, shutdownAwaitTermination);
        boolean done = false;
        while (!done && interval > 0) {
            if (executorService.awaitTermination(interval, TimeUnit.MILLISECONDS)) {
                done = true;
            } else {
                LOGGER.info("Waited {} ms for ExecutorService: {} to terminate...",
                        (System.currentTimeMillis() - start), executorService);
                interval = Math.min(MIN_SHUTDOWN_AWAIT_TERMINATION,
                        shutdownAwaitTermination - System.currentTimeMillis() - start);
            }
        }
        return done;
    }

    /**
     * shutdown all existed executor service.
     */
    public void shutdownAll() {
        Set<ExecutorService> forced = new LinkedHashSet<>();
        if (!executorServiceList.isEmpty()) {
            for (ExecutorService executorService : executorServiceList) {
                try {
                    boolean warned = doShutdown(executorService, DEFAULT_SHUTDOWN_AWAIT_TERMINATION, true);
                    if (warned) {
                        forced.add(executorService);
                    }
                } catch (Exception e) {
                    LOGGER.error("Error occurred during shutdown of ExecutorService: "
                            + executorService + ". This exception will be ignored.", e);
                }
            }
        }

        if (!forced.isEmpty()) {
            LOGGER.error("Forced shutdown of {} ExecutorService's which has not been shutdown properly (acting as fail-safe)",
                    forced.size());
            for (ExecutorService executorService : forced) {
                LOGGER.error("  forced -> {}", executorService);
            }
        }
        forced.clear();
        executorServiceList.clear();
    }
}
