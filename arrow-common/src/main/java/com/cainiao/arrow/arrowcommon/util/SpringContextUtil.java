package com.cainiao.arrow.arrowcommon.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil  implements ApplicationContextAware, DisposableBean{

    private static ApplicationContext applicationContext = null;

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws Exception{
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) throws Exception{
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext appContext) {
        applicationContext = appContext;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        SpringContextUtil.clearHolder();
    }

    public static void clearHolder() {
        applicationContext = null;
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() throws Exception {
        if(applicationContext == null){
           throw new Exception("applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
        }
   }
}
