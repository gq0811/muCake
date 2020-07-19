package com.cainiao.arrow.arrowservice.singleton;


/**
 * 饿汉模式
 * 优点：饿汉模式天生是线程安全的，使用时没有延迟。
 * 缺点：启动时即创建实例，启动慢，有可能造成资源浪费。
 */
public class SingletonHungry {
//4：定义一个静态变量来存储创建好的类实例

//直接在这里创建类实例，只会创建一次

    private static SingletonHungry instance = new SingletonHungry();

//1：私有化构造方法，好在内部控制创建实例的数目

    private SingletonHungry(){

    }

//2：定义一个方法来为客户端提供类实例

//3：这个方法需要定义成类方法，也就是要加static

//这个方法里面就不需要控制代码了

    public static SingletonHungry getInstance(){

//5：直接使用已经创建好的实例

        return instance;

    }

}
