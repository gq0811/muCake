package com.cainiao.arrow.arrowservice.thread;

public class TestRunnable implements Runnable{

    @Override
    public void run() {
        while(true) {
            System.out.println("thread running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //传入TestRunnable对象作为Target, 开启线程
        Thread t = new Thread(new TestRunnable());
        t.start();
        //采用匿名内部类的方式创建和启动线程
        new Thread() {
            @Override
            public void run() {
                System.out.println("Thread的匿名内部类");
            }
        }.start();
        //父类采用匿名实现Runnable接口, 并由子类继承
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("父类的线程");
            }
        }) {
            @Override
            public void run() {
                System.out.println("子类的线程");
            }
        }.start();
    }
}