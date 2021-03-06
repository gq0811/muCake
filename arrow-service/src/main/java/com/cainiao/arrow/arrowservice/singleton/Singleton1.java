package com.cainiao.arrow.arrowservice.singleton;


/**
 * 保证整个系统中一个类只有一个对象的实例，实现这种功能的方式就叫单例模式
 * 1、私有化构造器：既然对象只有一个，那就不能随便什么人都能创建对象了，所以这里要严格把控
 * 2、自己创建对象：既然不让别人创建对象，那就只能自己实例化来创建对象了
 * 3、提供获取对象的方法：因为对象是自己创建的，所以我们还的提供一个方法其他人能够拿到来使用。
 */
/**
 * 饱汉(也叫做懒汉)
 * 线程不安全
 */
public class Singleton1 {

    //定义一个私有静态的引用
    private static Singleton1 singleton1= null;
    private Singleton1(){

    }
    //获取实例的方法是公共的
    public static Singleton1 getInstance(){
        if(singleton1 == null){
            //实例化
            singleton1 = new Singleton1();
        }
        return singleton1;
    }

    public static void main(String[] args) {
        Singleton1 singleton1 = Singleton1.getInstance();
        Singleton1 singleton2 = Singleton1.getInstance();
        //返回的是同一个对象
        System.out.println("_"+singleton1.toString());
        System.out.println("_"+singleton2.toString());
    }
}



