package com.cainiao.arrow.arrowservice.singleton;


/**
 * 饱汉模式(懒汉模式)--双重加锁检查DCL（Double Check Lock）
 * 优点：和2比较，把锁的粒度放小，当实例为null，要去创建的时候再加锁
 */
public class Singleton3 {

    /**
     * 对保存实例的变量添加volatile的修饰
     * 为什么要使用volatile 修饰instance？
     * 主要在于instance = new Singleton()这句，这并非是一个原子操作，事实上在 JVM 中这句话大概做了下面 3 件事情:
     *
     * 1.给 instance 分配内存（todo 如何分配的）
     *
     * 2.调用 Singleton 的构造函数来初始化成员变量
     *
     * 3.将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）。
     *
     * 但是在 JVM 的即时编译器中存在指令重排序的优化。
     * 也就是说上面的第二步和第三步的顺序是不能保证的，最终的执行顺序可能是 1-2-3 也可能是 1-3-2。
     * 如果是后者，则在3执行完毕、2未执行之前，这时instance已经是非null了（但却没有初始化）。
     * 此时线程二进来之后做判断，instance不是null，所以线程二会直接返回 instance，然后使用，然后顺理成章地报错。
     */
    private volatile static Singleton3 instance = null;

    private Singleton3() {

    }

    public static Singleton3 getInstance() {
        //先检查实例是否存在，如果不存在才进入下面的同步块
        if (instance == null) {
            //同步块，线程安全的创建实例
            synchronized (Singleton3.class) {
            //再次检查实例是否存在，如果不存在才真的创建实例
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;

    }

}
