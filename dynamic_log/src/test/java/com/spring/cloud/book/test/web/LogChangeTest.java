package com.spring.cloud.book.test.web;

import com.spring.cloud.book.test.util.HttpUtil;
import com.spring.cloud.book.test.util.constant.URLType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * LogChangeTest
 * @author zhouyw
 * @date 2017.05.16
 */
public class LogChangeTest {

    /**
     *
     32:50,33:24 访问http://localhost:1234/log/test，期间通过运行test改变日记记录水平，由默认的INFO 变为 DEBUG
     2017-05-16 11:32:50.244  INFO 16992 --- [nio-1234-exec-4] c.s.cloud.book.test.web.LogController    : Logger Level ：INFO
     2017-05-16 11:32:50.245 ERROR 16992 --- [nio-1234-exec-4] c.s.cloud.book.test.web.LogController    : Logger Level ：ERROR
     2017-05-16 11:33:24.521 DEBUG 16992 --- [nio-1234-exec-9] c.s.cloud.book.test.web.LogController    : Logger Level ：DEBUG
     2017-05-16 11:33:24.521  INFO 16992 --- [nio-1234-exec-9] c.s.cloud.book.test.web.LogController    : Logger Level ：INFO
     2017-05-16 11:33:24.522 ERROR 16992 --- [nio-1234-exec-9] c.s.cloud.book.test.web.LogController    : Logger Level ：ERROR
     */
    /**
     * 浏览器的离线文件 无法直接ajax请求 ，会被拦截  跨域问题。
     * @throws Exception
     */
    @Test
    public void sendChangeLog() throws Exception {
        String url = "http://localhost:1234/loggers/com.spring.cloud.book.test.web";
        String message = "{\"configuredLevel\":\"DEBUG\"}";
        Map<String,String> params = new HashMap<String,String>();
        params.put("Content-Type","application/json");
        String resp = HttpUtil.request(url, message, URLType.HTTP,null,params);
        System.out.println("resp="+resp);
    }


}
