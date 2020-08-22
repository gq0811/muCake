package com.cainiao.arrow.arrowcommon.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
    /**
     * 少量线程池，执行调度使用，其他地方不要使用
     */
    public static ExecutorService manualExecuteExecutor = new ThreadPoolExecutor(20, 20, 1L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100000));
    /**
     * 执行时额外线程，执行调度使用，其他地方不要使用
     */
    public static ExecutorService executeBackstageExecutor = new ThreadPoolExecutor(100, 100, 1L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100000));
    /**
     * 模型构建专用线程池
     */
    public static ExecutorService modelBuildExecutor = new ThreadPoolExecutor(50, 50, 1L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100000));

    /**
     * 大量的普通线程池，一般后台服务可以使用这个
     */
    public static ExecutorService largeCommonExecutor = new ThreadPoolExecutor(100, 100, 1L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100000));

    /**
     * 中等的普通线程池，一般后台服务可以使用这个
     */
    public static ExecutorService mediumCommonExecutor = new ThreadPoolExecutor(50, 50, 1L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100000));

    /**
     * 少量的普通线程池，一般后台服务可以使用这个
     */
    public static ExecutorService smallCommonExecutor = new ThreadPoolExecutor(20, 20, 1L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100000));

    /**
     * 少量的普通线程池，一般后台服务可以使用这个
     */
    public static ExecutorService verySmallCommonExecutor = new ThreadPoolExecutor(10, 10, 1L, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100000));
}