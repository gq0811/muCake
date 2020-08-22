package com.cainiao.arrow.arrowservice.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AutoConfigService {

    private Logger logger = LoggerFactory.getLogger(AutoConfigService.class);
    public void add() {
        logger.info("这是AutoConfigService类的add方法！");
    }

}
