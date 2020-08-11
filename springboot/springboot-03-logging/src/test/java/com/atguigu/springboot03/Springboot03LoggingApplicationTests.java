package com.atguigu.springboot03;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03LoggingApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void contextLoads() {
        logger.trace("trace");
        logger.debug("debug");
        //springboot 默认的是info级别的，没有指定就使用springboot默认规定的级别，
        // 我们还可以通过配置文件进行设置springboot的日志级别
        //
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

}

