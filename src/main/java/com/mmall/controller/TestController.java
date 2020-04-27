package com.mmall.controller;

import com.mmall.common.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hx
 * @create 2020-03-16 22:32
 */

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    /*@RequestMapping("/hello.json")
    @ResponseBody*/
    public JsonData testHello(){
        log.info("hello");
        return JsonData.success("hello, permission project") ;
        //  return PermissionException("test permission Error") ;
        //  return RuntimeException("test error") ;
    }
}
