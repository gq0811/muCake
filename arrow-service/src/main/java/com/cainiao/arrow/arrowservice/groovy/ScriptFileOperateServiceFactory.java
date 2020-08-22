//package com.cainiao.arrow.arrowservice.groovy;
//
//
//import com.cainiao.arrow.arrowcommon.util.SpringContextUtil;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScriptFileOperateServiceFactory implements InitializingBean {
//
//    private static Map<ScriptMode, ScriptFileOperateService> scriptFileOperateMap = Maps.newConcurrentMap();
//
//    @Override
//    public void afterPropertiesSet(){
//        Map<String, Object> beanMap = SpringContextUtil.getBeanWithAnnotation(ScriptModeAnnotation.class);
//        for(Object userRelated : beanMap.values()) {
//            ScriptModeAnnotation annotation = userRelated.getClass().getAnnotation(ScriptModeAnnotation.class);
//            scriptFileOperateMap.put(annotation.value(), (ScriptFileOperateService)userRelated);
//        }
//    }
//
//    public static ScriptFileOperateService getBean(ScriptMode scriptMode) {
//        return scriptFileOperateMap.get( scriptMode );
//    }
//}
