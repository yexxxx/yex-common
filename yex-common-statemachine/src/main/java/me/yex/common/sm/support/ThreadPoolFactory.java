package me.yex.common.sm.support;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public interface ThreadPoolFactory {

    /**
     * Creates a thread pool with thread pool config
     *
     * @param config the config to use when creating new thread pool
     * @return the newly created thread pool
     */
    ExecutorService newThreadPool(ThreadPoolConfig config);

    /**
     * Creates a thread pool with parameters
     *
     * @param poolName thread pool name
     * @param poolSize the number of threads to keep in the pool
     * @param maxPoolSize the maximum number of threads to allow in the pool
     * @return the newly created thread pool
     */
    ExecutorService newThreadPool(String poolName, int poolSize, int maxPoolSize);

    /**
     * Creates an Executor that uses a single worker thread operating
     * off an unbounded queue
     *
     * @param poolName thread pool name
     * @return the newly created thread pool
     */
    ExecutorService newSingleThreadExecutor(String poolName);

    /**
     * Creates a thread pool that creates new threads as needed, but
     * will reuse previously constructed threads when they are available
     *
     * @param poolName thread pool name
     * @return the newly created thread pool
     */
    ExecutorService newCachedThreadPool(String poolName);

    /**
     *
     * @param poolName thread pool name
     * @param poolSize the number of threads to keep in the pool
     * @return the newly created thread pool
     */
    ExecutorService newFixedThreadPool(String poolName, int poolSize);

    /**
     * Creates a thread pool that can schedule commands to run after a
     * given delay, or to execute periodically
     *
     * @param config the config to use when creating new scheduled thread pool
     * @return the newly created scheduled thread pool
     */
    ScheduledExecutorService newScheduledThreadPool(ThreadPoolConfig config);

    /**
     * Creates a thread pool that can schedule commands to run after a
     * given delay, or to execute periodically
     *
     * @param poolName thread pool name
     * @param poolSize the number of threads to keep in the pool
     * @return the newly created scheduled thread pool
     */
    ScheduledExecutorService newScheduledThreadPool(String poolName, int poolSize);

    /**
     * Creates a single-threaded pool that can schedule commands
     * to run after a given delay, or to execute periodically.
     *
     * @param poolName thread pool name
     * @return the newly created scheduled thread pool
     */
    ScheduledExecutorService newSingleThreadScheduledExecutor(String poolName);

    /**
     * Initiates an orderly shutdown in which previously submitted
     * tasks are executed, but no new tasks will be accepted
     *
     * @param executorService the thread pool to be closed
     */
    void shutdownGraceful(ExecutorService executorService);

    /**
     * Blocks until all tasks have completed execution after a shutdown
     * request, or the timeout occurs, or the current thread is
     * interrupted, whichever happens first.
     *
     * @param executorService the thread pool to be closed
     * @param shutdownAwaitTermination the maximum time to wait
     */
    void shutdownGraceful(ExecutorService executorService, long shutdownAwaitTermination);

    /**
     * Attempts to stop all actively executing tasks, halts the
     * processing of waiting tasks, and returns a list of the tasks
     * that were awaiting execution
     *
     * @param executorService the thread pool to be closed
     * @return  list of tasks that never commenced execution
     */
    List<Runnable> shutdownNow(ExecutorService executorService);
}
