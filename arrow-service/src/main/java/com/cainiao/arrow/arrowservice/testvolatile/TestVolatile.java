package com.cainiao.arrow.arrowservice.testvolatile;


/**
 * 测试volatile关键字
 * 1、volatile关键字无法保证操作的原子性
 * 2、使用需要满足两个条件：1）对变量的写操作不依赖于当前值
 *               2）该变量没有包含在具有其他变量的不变式中
 *
 */
public class TestVolatile{
    public static boolean flag = false;
    public volatile int count =0 ;

    public void inc(){
        count++;
    }
    /**
     * 1、Java内存模型规定所有的变量都是存在主存当中，每个线程都有自己的工作内存。
     * 线程对变量的所有操作都必须在"工作内存"中进行，而不能直接对"主存"进行操作。
     * 并且每个线程不能访问其他线程的工作内存。变量的值何时从线程的工作内存写回主存，无法确定
     *
     * 2、所以volatile保证内存的可见性，写的时候会强制线程把最新的值刷回到"主内存"。读的时候强制让线程从"主内存"读最新的值。
     * 3、volatile关键字无法保证操作的原子性
     * 4、volatile关键字修饰的字段，会禁用编译器和处理器对指令进行重排序，这个在单例模式中可以体会到用处。
     */

    public static void main(String[] args) {
        TestVolatile testVolatile = new TestVolatile();
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (TestVolatile.class){
                       for(int j=0;j<1000;j++){
                           testVolatile.inc();
                       }
                   }
                }
            }).start();
        }
        System.out.printf("count"+testVolatile.count);
    }

}