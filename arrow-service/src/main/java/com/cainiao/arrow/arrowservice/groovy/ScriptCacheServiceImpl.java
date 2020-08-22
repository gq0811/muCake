//package com.cainiao.arrow.arrowservice.groovy;
//
//
//import com.sun.javaws.CacheUtil;
//import groovy.util.logging.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class ScriptCacheServiceImpl implements ScriptCacheService {
//    /**
//     * 默认buffer时间为1小时(后期可以根据需求修改)
//     */
//    private static final long BUFFER_TIME = 3600000L;
//
//    private static final String FOLDER = "GIT_STORE";
//
//    @Autowired
//    private CacheUtil cacheUtil;
//
//    @Override
//    public boolean put(ScriptCacheInfo scriptCacheInfo) {
//        if (Objects.isNull(scriptCacheInfo) || StringUtils.isEmpty(scriptCacheInfo.getFilePath()) || Objects.isNull(scriptCacheInfo.getTime())) {
//            log.warn("输入脚本为空、名称或时间为空. 待填充信息:{}", Objects.isNull(scriptCacheInfo) ? null : scriptCacheInfo);
//            return true;
//        }
//        try {
//            cacheUtil.hset(FOLDER, scriptCacheInfo.getFilePath(), scriptCacheInfo);
//        } catch (Exception e) {
//            log.error("添加脚本数据到Tair缓存失败", e);
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public ScriptCacheInfo get(String filePath) {
//        if (StringUtils.isEmpty(filePath)) {
//            return null;
//        }
//        try {
//            return cacheUtil.hget(FOLDER, filePath);
//        } catch (Exception e) {
//            log.error("Tair缓存获取失败", e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<ScriptCacheInfo> getAll() {
//        return cacheUtil.hvals(FOLDER);
//    }
//
//    @Override
//    public Long delete(String filePath) {
//        if (StringUtils.isEmpty(filePath)) {
//            log.warn("未传递有效路径");
//            return 0L;
//        }
//        return cacheUtil.hdel(FOLDER, filePath);
//    }
//
//    @Override
//    public ScriptCacheInfo equalWithCache(String groovyFullName, String commitId) {
//        if (StringUtils.isEmpty(groovyFullName) || StringUtils.isEmpty(commitId)) {
//            log.warn("传入判断相等的参数存在空值: groovyFullName: {}, commitId: {}", groovyFullName, commitId);
//            return null;
//        }
//
//        ScriptCacheInfo scriptCacheInfo = get(groovyFullName);
//        if (Objects.isNull(scriptCacheInfo)) {
//            return null;
//        }
//
//        return commitId.trim().equals(scriptCacheInfo.getVersion().trim()) ? null : scriptCacheInfo;
//    }
//
//    @Override
//    public ScriptCacheInfo newerThanCache(String groovyFullName, Long deployTime) {
//        if (StringUtils.isEmpty(groovyFullName) || Objects.isNull(deployTime)) {
//            log.warn("传入判断相等的参数存在空值: groovyFullName: {}, deployTime: {}", groovyFullName, deployTime);
//            return null;
//        }
//
//        long subBufferTime = deployTime - BUFFER_TIME;
//        ScriptCacheInfo scriptCacheInfo = get(groovyFullName);
//        if (Objects.isNull(scriptCacheInfo)) {
//            return null;
//        }
//
//        return subBufferTime > scriptCacheInfo.getTime() ? null : scriptCacheInfo;
//    }
//
//    @Override
//    public int clearUseless(Long deployTime) {
//        List<ScriptCacheInfo> scriptCacheInfoList = cacheUtil.hvals(FOLDER);
//        if (CollectionUtils.isEmpty(scriptCacheInfoList)) {
//            return 0;
//        }
//
//        long subBufferTime = deployTime - BUFFER_TIME;
//        int count = 0;
//        for(ScriptCacheInfo scriptCacheInfo : scriptCacheInfoList) {
//            if (scriptCacheInfo.getTime() < subBufferTime) {
//                log.info("脚本 {} 的上次更新时间 {}，已经小于部署的前一个小时({})，删除该缓存",
//                        scriptCacheInfo.getFilePath(),
//                        scriptCacheInfo.getTime(),
//                        subBufferTime);
//                cacheUtil.hdel(FOLDER, scriptCacheInfo.getFilePath());
//                count++;
//            }
//        }
//
//        return count;
//    }
//}
//
