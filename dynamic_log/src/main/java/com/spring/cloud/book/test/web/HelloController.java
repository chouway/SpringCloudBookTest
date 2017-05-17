package com.spring.cloud.book.test.web;

import com.spring.cloud.book.test.bean.TestBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.*;

/**
 * HelloController
 * @author zhouyw
 * @date 2017.05.16
 */
@Controller
public class HelloController {
    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        // 加入一个属性，用来在模板中读取
        modelMap.addAttribute("host", "http://localhost:1234/");
        addMap(modelMap);
        // return模板文件的名称，对应src/main/resources/templates/test/index.html
        return "/test/index";
    }

    /* -----private method spilt----- */
    private void addMap(ModelMap modelMap){
        List<TestBean> list = new ArrayList<TestBean>();
        Map<String,Object> map = new HashMap<String,Object>();

        TestBean testBean_0 = new TestBean();
        testBean_0.setBigDecimal(new BigDecimal("0.01"));
        testBean_0.setDate(new Date());
        testBean_0.setInteger(10);
        testBean_0.setStr("a");

        TestBean testBean_1 = new TestBean();
        testBean_1.setBigDecimal(new BigDecimal("0.02"));
        testBean_1.setDate(new Date());
        testBean_1.setInteger(20);
        testBean_1.setStr("b");

        list.add(testBean_0);
        list.add(testBean_1);
        map.put("testBean_0",testBean_0);
        map.put("testBean_1",testBean_1);

        modelMap.addAttribute("map",map);
        modelMap.addAttribute("list",list);
        modelMap.addAttribute("testBean_0",testBean_0);
        modelMap.addAttribute("testBean_1",testBean_1);
    }

}