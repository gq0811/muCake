package com.cainiao.arrow.arrowservice.thread.producerconsumer;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BlockingQueue方式实现生产者，消费者
 * 利用BlockingQueue的阻塞能力（上层）
 * 当一个线程对已经满了的阻塞队列进行入队操作时会阻塞，除非有另外一个线程进行了出队操作。
 * 当一个线程对一个空的阻塞队列进行出队操作时也会阻塞，除非有另外一个线程进行了入队操作。
 * 所以BlockingQueue是线程安全的
 * 本质上，BlockingQueue在底层用的也是ReentrantLock来实现阻塞和同步控制的
 */
public class BlockingQueueProducerAndConsumer {

    private static Integer count = 0;
    //创建一个阻塞队列
    final BlockingQueue blockingQueue = new ArrayBlockingQueue<>(10);

    /**
     *     操作  抛异常    特定值        阻塞              超时
     *     插入 add(o)    offer(o)    put(o)       offer(o, timeout, timeunit)
     *     移除 remove(o)  poll(o)    take(o)      poll(timeout, timeunit)
     *     检查 element(o) peek(o)
     * --------------------------------------------
     * 用put和take可以实现阻塞的效果
     */
    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    blockingQueue.put(1);
                    count++;
                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    blockingQueue.take();
                    count--;
                    System.out.println(Thread.currentThread().getName()
                            + "消费者消费，目前总共有" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
