package com.cainiao.arrow.arrowcommon.dto;

import com.cainiao.arrow.arrowcommon.inter.CacheDataGenerator;

public class CacheObject {

    private Long duration ;

    private Object value;

    private Long StartTime;

    private CacheDataGenerator cacheDataGenerator;

    public CacheDataGenerator getGenerator() {
        return cacheDataGenerator;
    }

    public void setGenerator(CacheDataGenerator cacheDataGenerator) {
        this.cacheDataGenerator = cacheDataGenerator;
    }

    public Long getStartTime() {
        return StartTime;
    }

    public void setStartTime(Long startTime) {
        StartTime = startTime;
    }

    public boolean isExpired(){
        //todo
        return true;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
