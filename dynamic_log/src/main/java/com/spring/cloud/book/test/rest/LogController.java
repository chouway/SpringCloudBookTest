package com.spring.cloud.book.test.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * LogController
 * @author zhouyw
 * @date 2017.05.16
 */
@RestController
@RequestMapping(value = "/log")
public class LogController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 默认的日志级别为INFO
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testLogLevel() {
        logger.debug("Logger Level ：DEBUG");
        logger.info("Logger Level ：INFO");
        logger.error("Logger Level ：ERROR");
        return "welcome to log controller";
    }
}
