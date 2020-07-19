package com.cainiao.arrow.arrowservice.singleton;

/**
 * 饱汉(也叫做懒汉)
 * 线程安全,加了synchronized控制线程安全
 * 缺点：synchronized 为独占排他锁，并发性能差。即使在创建成功以后，获取实例仍然是串行化操作
 */
public class Singleton2 {

    private static Singleton2 singleton2= null;
    private Singleton2(){

    }
    //获取实例的方法是公共的
    //synchronized 为独占排他锁，并发性能差。即使在创建成功以后，获取实例仍然是串行化操作
    public synchronized static Singleton2 getInstance(){
        if(singleton2 == null){
            singleton2 = new Singleton2();
        }
        return singleton2;
    }

    /**
     * 示意属性，单例可以有自己的属性。diamond获取配置化数据的时候，就是存的这个值
     */
    private String singletonData;

    /**
     * 示意方法，让外部通过这些方法来访问属性的值
     * @return 属性的值
     */
    public String getSingletonData(){
        return singletonData;
    }
}
