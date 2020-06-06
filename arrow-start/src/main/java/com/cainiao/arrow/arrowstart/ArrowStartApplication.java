package com.cainiao.arrow.arrowstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },scanBasePackages = {"com.cainiao.arrow"})
public class ArrowStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArrowStartApplication.class, args);
    }

}
