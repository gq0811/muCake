package com.cainiao.arrow.arrowservice.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
 *    AbortPolicy策略，直接抛出异常
 *
 *
 */
public class TestThreadPoolService {

    private static ExecutorService pool;
    public static void main( String[] args )
    {

        /**
         * 直接提交队列：用SynchronousQueue实现
         * 没有容量，是无缓冲等待队列，是一个不存储元素的阻塞队列
         * 会直接将任务交给消费者，必须等队列中的添加元素被消费后才能继续添加新的元素
         * maximumPoolSize设置为2 ，拒绝策略为AbortPolicy策略，直接抛出异常
         */
//        pool = new ThreadPoolExecutor(1, 3, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
//        for(int i=0;i<3;i++) {
//            pool.execute(new ThreadTask());
//        }
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.get();

        /**
         * 有界的任务队列：有界的任务队列可以使用ArrayBlockingQueue
         * 1、若有新的任务需要执行时，线程池会创建新的线程，直到创建的线程数量达到corePoolSize时，则会将新的任务加入到等待队列ArrayBlockingQueue中
         * 2、若等待队列已满，即超过ArrayBlockingQueue的容量10，此时才会再创建新的线程，直到达到maximumPoolSize的数量。
         * 3、当超过maximumPoolSize时，会执行拒绝策略
         * 所以说，ArrayBlockingQueue是优先等待的，队列满了才会继续创建线程。
         */
        pool = new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5),Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
        //同时提交10个线程，会瞬间撑满队列，然后再加一个线程。最后只有7个任务被执行，其他都被拒绝了
        //打印的结果，pool-1-thread-1和pool-1-thread-2两个线程轮番的执行
        for(int i=0;i<10;i++) {
            pool.execute(new ThreadTask());
        }

        /**
         * 无界的任务队列：有界任务队列可以使用LinkedBlockingQueue
         * 1、因为是无界队列，线程池的任务队列可以无限制的添加新的任务
         * 2、所以此时的maximumPoolSize是无效的，因为队列一直在增加任务。
         * 注意任务提交与处理之间的协调与控制，不然会出现队列中的任务由于无法及时处理导致一直增长，直到最后资源耗尽的问题。
         */
//        pool = new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
//        //此时就只会有一个pool-1-thread-1线程，一直把10个任务都跑完。
//        for(int i=0;i<10;i++) {
//            pool.execute(new ThreadTask());
//        }

        /**
         * 优先任务队列：优先任务队列通过PriorityBlockingQueue
         * 1、基本上原理和LinkedBlockingQueue一致，区别在于优先任务队列会把任务队列中的任务按优先级排序
         * 2、任务通过实现Comparable接口来比较优先级
         *
         */
//        pool = new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
//        //把序号作为优先级
//        for(int i=0;i<20;i++) {
//            pool.execute(new ThreadTask2(i));
//        }

        /**
         * 拒绝策略
         * 1、 AbortPolicy策略：该策略会直接抛出异常，阻止系统正常工作
         * 2、 CallerRunsPolicy策略：如果线程池的线程数量达到上限，该策略会把任务队列中的任务放在调用者线程当中运行；
         * 3、 DiscardOledestPolicy策略：该策略会丢弃任务队列中最老的一个任务，也就是当前任务队列中最先被添加进去的，马上要被执行的那个任务，并尝试再次提交；
         * 4、 DiscardPolicy策略：该策略会默默丢弃无法处理的任务，不予任何处理，不抛异常。当然使用此策略，业务场景中需允许任务的丢失；
         */


    }


    /**
     * 实现了Comparable接口的任务。可以用在优先队列中
     */
    public static class ThreadTask2 implements Runnable,Comparable<ThreadTask2>{

        private int priority;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public ThreadTask2() {

        }

        public ThreadTask2(int priority) {
            this.priority = priority;
        }

        //当前对象和其他对象做比较，当前优先级大就返回-1，优先级小就返回1,值越大优先级越高
        public int compareTo(ThreadTask2 o) {
            return  this.priority>o.priority?-1:1;
        }

        public void run() {
            try {
                //让线程阻塞，使后续任务进入缓存队列
                Thread.sleep(1000);
                System.out.println("priority:"+this.priority+",ThreadName:"+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public static class ThreadTask implements Runnable{

        public ThreadTask() {

        }

        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }
}
