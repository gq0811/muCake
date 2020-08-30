package com.cainiao.arrow.arrowservice.proxy;


import com.cainiao.arrow.arrowservice.proxy.Person;

/**
 * 被代理类PersonImpl
 */
public class PersonImpl implements Person {


    @Override
    public void sayHello(String content, int age){
        System.out.println("this is PersonImpl");
    }

}
