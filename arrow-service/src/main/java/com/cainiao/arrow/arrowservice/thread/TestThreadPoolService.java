package com.cainiao.arrow.arrowservice.thread;

import java.util.concurrent.*;
/**
 * corePoolSize:指定了线程池中的线程数量，它的数量决定了添加的任务是开辟新的线程去执行，还是放到workQueue任务队列中去；
 * maximumPoolSize:指定了线程池中的最大线程数量，这个参数会根据你使用的workQueue任务队列的类型，决定线程池会开辟的最大线程数量；
 * keepAliveTime:当线程池中线程数量超过corePoolSize时，多余的线程会在多长时间内没有等到任务被销毁；
 * unit:keepAliveTime的单位
 * ----------------------------------------------------------
 * workQueue:任务队列，被添加到线程池中，但尚未被执行的任务；
 * 它一般分为直接提交队列、有界任务队列、无界任务队列、优先任务队列几种
 *
 * ----------------------------------------------------------
 * threadFactory:线程工厂，用于创建线程，一般用默认即可；也可以自己定义
 * handler:拒绝策略；当任务太多来不及处理时，如何拒绝任务；
 */
public class TestThreadPoolService {

    private static ExecutorService pool;
    public static void main( String[] args )
    {
        //maximumPoolSize设置为2 ，拒绝策略为AbortPolicy策略，直接抛出异常


        /**
         * SynchronousQueue 直接提交队列
         *
         */
        pool = new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        for(int i=0;i<3;i++) {
            pool.execute(new ThreadTask());
        }




    }


    public static class ThreadTask implements Runnable{

        public ThreadTask() {

        }

        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
