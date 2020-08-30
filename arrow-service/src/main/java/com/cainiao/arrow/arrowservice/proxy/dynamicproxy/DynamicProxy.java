package com.cainiao.arrow.arrowservice.proxy.dynamicproxy;

import com.alibaba.fastjson.JSON;
import com.cainiao.arrow.arrowcommon.dto.UserGroupDTO;
import com.cainiao.arrow.arrowservice.proxy.Person;
import com.cainiao.arrow.arrowservice.proxy.PersonImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class DynamicProxy {

    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    public Object getPersonProxy() {
        //通过传进来的被代理类的相关信息，生成一个代理对象的实例。
        System.out.printf("loader:"+target.getClass().getClassLoader());
        System.out.printf("getInterfaces:"+ JSON.toJSONString(target.getClass().getInterfaces()));
        Object p = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开始代理");
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        System.out.println("结束代理");
                        return returnValue;
                    }
                });
        return p;
    }

    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy(new PersonImpl());
        Person proxyPerson = (Person)dynamicProxy.getPersonProxy();
        proxyPerson.sayHello("csdcvd",3234);

    }
}