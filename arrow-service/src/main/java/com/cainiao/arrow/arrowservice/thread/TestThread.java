package com.cainiao.arrow.arrowservice.thread;

import com.cainiao.arrow.arrowservice.singleton.Singleton1;
import com.cainiao.arrow.arrowservice.singleton.Singleton2;

public class TestThread extends Thread{

    public TestThread(String name) {
        setName(name);
    }
    @Override
    public void run() {
        while(!interrupted()) {
            try {
                Thread.sleep(1000);
                Singleton2.getInstance().getSingletonData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.println(getName() + "线程执行了");
    }
    public static void main(String[] args) {

        TestThread t1 = new TestThread("first");
        TestThread t2 = new TestThread("second");
        //setDaemon()设置线程为守护线程
//        t1.setDaemon(true);
//        t2.setDaemon(true);
        t1.start();
        t2.start();
        t1.interrupt();
        t2.interrupt();
    }
}
