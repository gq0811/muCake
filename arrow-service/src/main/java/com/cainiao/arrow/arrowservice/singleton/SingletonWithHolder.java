package com.cainiao.arrow.arrowservice.singleton;

/**
 * Holder模式:就是将我们要构造的实例交于其内部类进行实例。
 * 基于的原理：静态的内部类会在主类调用的时候才会被加载
 * 优点：将懒加载和线程安全完美结合的一种方式
 */
public class SingletonWithHolder {

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     *
     *
     * 这个方法并没有事先声明instance的静态成员，而是把它放到了静态内部类Holder中,Holder中定义了静态变量，并直接进行了实例化，当Holder被主动引用的时候就会创建实例。
     * HolderSingleton在实例创建过程中被收集到<clinit>()方法中，这个方法是同步方法，保证了内存的可见性，JVM指令的顺序执行和原子性，该方法也是单例模式的最好设计之一。
     */
    private static class SingletonHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static SingletonWithHolder instance = new SingletonWithHolder();
    }
    /**
     * 私有化构造方法
     */
    private SingletonWithHolder(){
    }
    public static  SingletonWithHolder getInstance(){
        return SingletonHolder.instance;
    }

}
