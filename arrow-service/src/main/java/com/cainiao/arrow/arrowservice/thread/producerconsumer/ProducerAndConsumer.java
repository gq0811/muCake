package com.cainiao.arrow.arrowservice.thread.producerconsumer;

/**
 *  生产者，消费者
 */
public class ProducerAndConsumer {

    private static volatile int count = 0;
    private static Object LOCK = "LOCK";
    private static final int FULL = 10;

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
                synchronized (LOCK){
                    //while循环是预防 当前线程虽然wait进入了_waitset,但是别的线程notify把它游拉了起来。如果是if的话，就不会判断
                    //直接往下走了，那这个时候count会超数量。
                    while (count == FULL){
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println("current Producer Thread"+Thread.currentThread().getName()+",count:"+count);
                    //唤醒等待池中的线程，加入到_entryList
                    LOCK.notifyAll();
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
                synchronized (LOCK){
                    //while循环是预防 当前线程虽然wait进入了_waitset,但是别的线程notify把它游拉了起来。如果是if的话，就不会判断
                    //直接往下走了，那这个时候count会变成负数。
                    while (count == 0){
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println("current Consumer Thread"+Thread.currentThread().getName()+",count:"+count);
                    //唤醒等待池中的线程，加入到_entryList
                    LOCK.notifyAll();
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
