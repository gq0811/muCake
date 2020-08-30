package com.cainiao.arrow.arrowservice.proxy.staticproxy;

import com.cainiao.arrow.arrowservice.proxy.Person;
import com.cainiao.arrow.arrowservice.proxy.PersonImpl;

/**
 * 代理类Proxy
 * 所谓的静态代理，也就是说，
 * 1、代理类和被代理类继承同一个接口，然后实现相同的方法。
 * 2、然后代理类中持有一个被代理的对象。
 * 3、然后代理类执行这个相同的方法，可以在前后做增强，然后中间调用被代理类的方法
 */
public class Proxy implements Person {

    private Person o;
    public Proxy(Person o){
        this.o = o;
    }
    @Override
    public void sayHello(String content, int age) {
        // TODO Auto-generated method stub
        System.out.println("ProxyTest sayHello begin");
        //在代理类的方法中 间接访问被代理对象的方法
        o.sayHello(content, age);
        System.out.println("ProxyTest sayHello end");
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //s为被代理的对象，某些情况下 我们不希望修改已有的代码，我们采用代理来间接访问
        Person s = new PersonImpl();
        //创建代理类对象
        Proxy proxy = new Proxy(s);
        //调用代理类对象的方法
        proxy.sayHello("welcome to java", 20);

        System.out.println(null == null);
    }
}
