package com.cainiao.arrow.arrowservice.groovy;


import ch.qos.logback.classic.util.EnvUtil;
import com.cainiao.arrow.arrowcommon.constant.CacheKeysPrefixCenter;
import com.cainiao.arrow.arrowcommon.util.CreateHashAlgorithm;
import com.cainiao.arrow.arrowcommon.util.ExeLogUtil;
import com.cainiao.arrow.arrowcommon.util.LocalCacheUtil;
import com.cainiao.arrow.arrowcommon.util.SpringContextUtil;
import com.sun.javaws.CacheUtil;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GroovyScriptBaseService {


    private static Map<String, String> keyToHashMap = new ConcurrentHashMap<>();

//    @Resource
//    private GroovyScriptService groovyScriptService;
//    @Resource
//    private EnvUtil envUtil;
//    @Resource
//    private ScriptFileOperateServiceFactory scriptFileOperateServiceFactory;
//    @Autowired
//    private ScriptCacheService scriptCacheService;

    private static GroovyClassLoader getGroovyClassLoader() {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setSourceEncoding("UTF-8");
        // 设置该GroovyClassLoader的父ClassLoader为当前线程的加载器(默认)
        return new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
    }

    private static String getCacheKeyCode(String textCode) {
        return CacheKeysPrefixCenter.GROOVY_OBJECT_CACHE_PREFIX + CreateHashAlgorithm.toHash(textCode);
    }

    private static void invalidCache(String key, String textCode) {
        String newCacheKeyCode = getCacheKeyCode(textCode);
        if (keyToHashMap.containsKey(key)) {
            String oldCacheKeyCode = keyToHashMap.get(key);
            if (!newCacheKeyCode.equals(oldCacheKeyCode)) {
                LocalCacheUtil.clear(oldCacheKeyCode);
                LocalCacheUtil.clearExpired();
            }
        }
        keyToHashMap.put(key, newCacheKeyCode);
    }

    /**
     * 会根据当前的脚本模式获取脚本
     *
     * @param arrowKey 脚本路径
     * @param arrowType 脚本类型
     * @return 返回获取的脚本代码
     */
    public String judgeByScript(String arrowKey, Integer arrowType) {
//        try {
//            //当查询不到时，当前默认设置为NORMAL模式
//            String scriptMode = Objects.nonNull(ExecuteContextUtil.getContextInfo().get()) && Objects.nonNull(
//                    ExecuteContextUtil.getContextInfo().get().getScriptMode()) ? ExecuteContextUtil.getContextInfo().get()
//                    .getScriptMode() : ScriptMode.NORMAL.name();
//            JSONObject arrowBaseConfig = diamondConfigUtil.queryConfig(DiamondConfigConstants.ARROW_BASE_CONFIG,
//                    JSONObject.class);
//
//            //当未配置开关或开关为false时，默认不走脚本能力
//            if (arrowBaseConfig.getBoolean(ArrowBaseConfigConstants.GROOVY_SCRIPT_SWITCH) == null || !arrowBaseConfig.getBoolean(ArrowBaseConfigConstants.GROOVY_SCRIPT_SWITCH)) {
//                return null;
//            }
//            OtherArgs otherArgs = new OtherArgs();
//            otherArgs.setScriptType(arrowType);
//            ScriptFileInfo scriptFileInfo = null;
//            if(ScriptMode.DEBUG.name().equals(scriptMode)) {
//                scriptFileInfo = getScriptFileInDebugMode(arrowKey, otherArgs);
//            } else if(isUseScript(arrowKey)) {
//                scriptFileInfo = ScriptFileOperateServiceFactory.getBean(ScriptMode.NORMAL).getFile(arrowKey, otherArgs);
//            }
//
//            if (Objects.nonNull(scriptFileInfo)) {
//                String textCode = scriptFileInfo.getContent();
//                invalidCache(arrowKey + "_" + arrowType, textCode);
//                ExeLogUtil.executeAdd(String.format("配置了groovy脚本(%s), 通过脚本调用", arrowKey));
//                return textCode;
//            }
//        }catch (ServiceException e) {
//            throw new ServiceException(e);
//        } catch (Exception e) {
//            log.error("judgeByScript error", e);
//        }
        return null;
    }

    /**
     * debug模式下获取脚本代码的逻辑
     * <p>先获取db，如果db中为空，再根据机器中的jar包情况获取git</p>
     * @param arrowKey 脚本key
     * @param otherArgs 其他配置信息
     * @return 返回脚本内容
     */
//    private ScriptFileInfo getScriptFileInDebugMode(String arrowKey, OtherArgs otherArgs) {
//        ScriptFileInfo scriptFileInfo = ScriptFileOperateServiceFactory.getBean(ScriptMode.DEBUG).getFile(arrowKey, otherArgs);
//        //数据库中无数据时，要从git中获取最新数据(如果jar包中不是最新时)
//        if (Objects.isNull(scriptFileInfo) && isUseScript(arrowKey)) {
//            scriptFileInfo = ScriptFileOperateServiceFactory.getBean(ScriptMode.NORMAL).getFile(arrowKey, otherArgs);
//        }
//        return scriptFileInfo;
//    }


    /**
     * 通过groovy脚本的text，执行的入口
     */
    public Object executeByCode(String textCode, String methodName, Object[] objects) {
        GroovyObject groovyObject = compiledCodeToObject(textCode);
        // 反射调用方法得到返回值
        return groovyObject.invokeMethod(methodName, objects);
    }

    /**
     * 把groovy脚本的文本，转成GroovyObject
     */
    private GroovyObject compiledCodeToObject(String textCode) {

        return LocalCacheUtil.get(getCacheKeyCode(textCode), 60 * 60L, () -> {
            try {
                GroovyClassLoader groovyClassLoader = GroovyScriptBaseService.getGroovyClassLoader();
                groovyClassLoader.clearCache();
                // 替换包名，反正包冲突，在包名前面添加groovy.script.
                String finalTextCode = textCode.replaceFirst("public\\s+class\\s+", "public class Groovy");
                // 加载class类进入内存
                Class<?> groovyClass = groovyClassLoader.parseClass(finalTextCode);
                // 获得Groovy的实例
                GroovyObject groovyTempObject = (GroovyObject)groovyClass.newInstance();
                // 解决依赖注入问题
                this.solveDependInjection(groovyTempObject);
                return groovyTempObject;
            } catch (Throwable e) {/**/
                throw new RuntimeException(e);
            }
        });
    }

    private void solveDependInjection(Object object) {
        Class<?> tempClass = object.getClass();

        List<Field> allFieldList = new ArrayList<>();
        while (tempClass != null && !tempClass.equals(Object.class)) {
            allFieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }

        List<Field> dependFieldList = new ArrayList<>();
        List<String> dependResourceNameList = new ArrayList<>();
        for (Field field : allFieldList) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (ArrayUtils.isEmpty(annotations)) {
                continue;
            }
            String resourceName = null;
            for (Annotation annotation : annotations) {
                Class<?> annotationType = annotation.annotationType();
                // 只检查Resource和Autowired两个注解，其他就不管了
                if (annotationType.equals(Resource.class) || annotationType.equals(Autowired.class)) {
                    // 如果是Resource注解，还要判断是否有name属性
                    if (annotationType.equals(Resource.class)) {
                        Resource resource = (Resource)annotation;
                        // name不为空，那么保存对应的name值
                        if (StringUtils.isNotBlank(resource.name())) {
                            resourceName = resource.name();
                        }
                    }
                    dependFieldList.add(field);
                    dependResourceNameList.add(resourceName);
                    break;
                }
            }
        }

        List<String> fieldNameList = new ArrayList<>();
        for (int i = 0; i < dependFieldList.size(); i++) {
            Field field = dependFieldList.get(i);
            String resourceName = dependResourceNameList.get(i);
            fieldNameList.add(field.getName());
            try {
                field.setAccessible(true);
                Object beanObject;
                if (resourceName == null) {
                    beanObject = SpringContextUtil.getBean(field.getType());
                } else {
                    beanObject = SpringContextUtil.getBean(resourceName);
                }
                if (beanObject == null) {
                    throw new RuntimeException(String.format("字段%s通过spring注入失败", field.getName()));
                }
                field.set(object, beanObject);
            } catch (Throwable e) {
                ExeLogUtil.executeAdd(String.format("solveDependInjection: fieldName: %s, error: %s", field.getName(), ""));
            }
        }
        ExeLogUtil.executeAdd(String.format("solveDependInjection: fieldNameList: %s", ""));
    }


//    Result<String> canTextCodeBeCompiledCorrectly(String textCode) {
//        Result<String> result = new Result<>();
//        try {
//            this.compiledCodeToObject(textCode);
//            result.setSuccess(true);
//        } catch (MultipleCompilationErrorsException cfe) {
//            result.setSuccess(false);
//            result.setMsg(cfe.getMessage());
//        } catch (Exception e) {
//            result.setSuccess(false);
//            result.setMsg(e.getMessage());
//        }
//
//        return result;
//    }



    /**
     * 是否读取脚本
     * <p>执行模式下，通过判断部署时间来确认是否读脚本</p>
     * @param filePath 脚本路径
     * @return 返回判断结果
     */
//    private boolean isUseScript(String filePath) {
//        //获取本机ip
//        String env;
//        if (envUtil.isTanNei()) {
//            env = CacheKeysPrefixCenter.APP_DEPLOY_TIME_CACHE_PREFIX + "_TANNEI";
//        } else {
//            env = CacheKeysPrefixCenter.APP_DEPLOY_TIME_CACHE_PREFIX + "_TANWAI";
//        }
//        String ip = getIp();
//        Long deployTime = cacheUtil.hget(env, ip);
//
//        //当部署时间查询不到时，默认采用git(缓存)数据
//        if (Objects.isNull(deployTime)) {
//            return true;
//        }
//
//        ScriptCacheInfo scriptCacheInfo = scriptCacheService.newerThanCache(filePath, deployTime);
//        if (scriptCacheInfo != null) {
//            log.info("NORMAL 模式下，文件 {} 走脚本", filePath);
//            return true;
//        }
//        return false;
//    }


    /**
     * 获取缓存数据，并将缓存对象封装为ScriptFileInfo对象
     * @param filePath 文件路径
     * @return 返回ScriptFileInfo对象，如果为查到缓存，则返回null对象
     */
//    private ScriptFileInfo getCacheScriptCode(String filePath) {
//        ScriptCacheInfo scriptCacheInfo = scriptCacheService.get(filePath);
//        if (isInvalidCache(scriptCacheInfo)) {
//            return null;
//        }
//        ScriptFileInfo fileInfo = new ScriptFileInfo();
//        fileInfo.setContent(scriptCacheInfo.getContent());
//        fileInfo.setFilePath(scriptCacheInfo.getFilePath());
//        fileInfo.setVersion(scriptCacheInfo.getVersion());
//        fileInfo.setGmtModified(new Date(scriptCacheInfo.getTime()));
//        return fileInfo;
//    }

    /**
     * 缓存信息是否为非合法缓存
     * <p>true: 不合法;
     *    false: 合法</p>
     * @param scriptCacheInfo 缓存信息
     * @return 返回判断结果
     */
//    private boolean isInvalidCache(ScriptCacheInfo scriptCacheInfo) {
//        return Objects.isNull(scriptCacheInfo) || StringUtils.isBlank(scriptCacheInfo.getFilePath()) || StringUtils.isBlank(scriptCacheInfo.getContent())
//                || StringUtils.isBlank(scriptCacheInfo.getVersion()) || Objects.isNull(scriptCacheInfo.getTime());
//    }
}
