package com.cainiao.arrow.arrowservice.thread.producerconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *  可重入锁ReentrantLock实现的生产者，消费者
 *  和synchronized对比：
 *  1、使用方式不同，synchronized依赖于JVM实现的，而ReentrantLock是JDK实现的。
 *  synchronized由编译器去保证锁的加锁和释放，而ReentrantLock使用起来相当灵活，需要手动添加lock和unlock方法。
 *  2、ReenTrantLock可以指定是公平锁还是非公平锁（默认是非公平锁）。而synchronized只能是非公平锁。所谓的公平锁就是先等待的线程先获得锁。
 *  3、ReenTrantLock提供了一个Condition（条件）类，用来实现分组唤醒需要唤醒的线程们，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程。
 *  4、ReenTrantLock是可中断锁，通过lock.lockInterruptibly()来实现。
 *  5、使用上，ReenTrantLock是 await(),singnal(),signalAll().synchronized用的方式是wait(),
 */
public class ReentrantLockProducerAndConsumer {

    private static Integer count = 0;
    private static final Integer FULL = 10;
    //创建一个锁对象
    private Lock lock = new ReentrantLock();
    //创建两个条件变量，一个为缓冲区非满，一个为缓冲区非空
    //这也是和synchronized相比的一个特点。提供了一个Condition（条件）类，用来实现分组唤醒需要唤醒的线程们，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程
    //这样就可以精准的确定是唤醒生产者还是消费者。
    // 在这里，notFull用来作为生产者的锁，notEmpty作为消费者的锁
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    class Producer implements Runnable{
        @Override
        public void run() {
            //模拟多次生产，加快效果
            for(int i=0;i<3;i++){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                try{
                    //while循环是预防 当前线程虽然wait进入了_waitset,但是别的线程notify把它游拉了起来。如果是if的话，就不会判断
                    //直接往下走了，那这个时候count会超数量。
                    while (count == FULL){
                        try {
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println("current Producer Thread"+Thread.currentThread().getName()+",count:"+count);
                    //
                    notEmpty.signalAll();
                }finally {
                    //一般在finally加上解锁的动作。
                    lock.unlock();
                }
            }
        }
    }

    class Consumer implements Runnable{
        @Override
        public void run() {
            //模拟多次消费，加快效果
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                try{
                    //while循环是预防 当前线程虽然wait进入了_waitset,但是别的线程notify把它游拉了起来。如果是if的话，就不会判断
                    //直接往下走了，那这个时候count会变成负数。
                    while (count == 0){
                        try {
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println("current Consumer Thread"+Thread.currentThread().getName()+",count:"+count);
                    //唤醒等待池中的线程，加入到_entryList
                    notFull.signalAll();
                }finally {
                    //一般在finally加上解锁的动作。
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer producerAndConsumer = new ProducerAndConsumer();
        for(int i=0;i<10;i++){
            new Thread(producerAndConsumer.new Producer()).start();
            new Thread(producerAndConsumer.new Consumer()).start();
        }
    }

}
