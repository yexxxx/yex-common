package me.yex.common.sm.support;

import java.io.Serializable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;

/**
 * Created by yves on 2018/9/4.
 */
public class ThreadPoolConfig implements Serializable {

    private String poolName;

    private Integer poolSize;

    private Integer maxPoolSize;

    private Long keepAliveTime;

    private TimeUnit timeUnit;

    private Integer maxQueueSize;

    private boolean allowCoreThreadTimeOut;

    private RejectedExecutionHandler rejectedHandler;

    public ThreadPoolConfig(String poolName) {
        this.poolName = poolName;
    }

    public ThreadPoolConfig from(ThreadPoolConfig defaultThreadPoolConfig) {
        this.setPoolSize(defaultThreadPoolConfig.poolSize);
        this.setMaxPoolSize(defaultThreadPoolConfig.maxPoolSize);
        this.setKeepAliveTime(defaultThreadPoolConfig.keepAliveTime);
        this.setTimeUnit(defaultThreadPoolConfig.timeUnit);
        this.setMaxQueueSize(defaultThreadPoolConfig.maxQueueSize);
        this.setAllowCoreThreadTimeOut(defaultThreadPoolConfig.allowCoreThreadTimeOut);
        this.setRejectedHandler(defaultThreadPoolConfig.rejectedHandler);
        return this;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(Integer maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public boolean isAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public RejectedExecutionHandler getRejectedHandler() {
        return rejectedHandler;
    }

    public void setRejectedHandler(RejectedExecutionHandler rejectedHandler) {
        this.rejectedHandler = rejectedHandler;
    }


    @Override
    public String toString() {
        return "ThreadPoolConfig{" +
                "poolName='" + poolName + '\'' +
                ", poolSize=" + poolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", keepAliveTime=" + keepAliveTime +
                ", timeUnit=" + timeUnit +
                ", maxQueueSize=" + maxQueueSize +
                ", allowCoreThreadTimeOut=" + allowCoreThreadTimeOut +
                ", rejectedHandler=" + rejectedHandler +
                '}';
    }


}
