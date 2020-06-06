package com.cainiao.arrow.arrowstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
*  读取bean文件
*/
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },scanBasePackages = {"com.cainiao.arrow"})
@ImportResource("classpath:spring/beans.xml")
public class ArrowStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArrowStartApplication.class, args);
    }

}
