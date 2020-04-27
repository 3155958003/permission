package com.mmall.common;

import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hx
 * @create 2020-04-10 10:58
 *
 * 全局异常处理类
 */

@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        //  获取页面请求url
        String url = httpServletRequest.getRequestURL().toString() ;

        ModelAndView mv ;
        //  定义默认的错误信息
        String defaultMsg = "System error" ;

        // 系统中定义,请求json数据都是用 .josn结尾
        if (url.endsWith(".json")){
            if (e instanceof PermissionException){
                JsonData result = JsonData.fail(e.getMessage()) ;
                mv = new ModelAndView("jsonView", result.toMap()) ;
            } else {
                log.info("unknow json Exception , url:"+url, e);
                JsonData result = JsonData.fail(defaultMsg) ;
                mv = new ModelAndView("jsonView", result.toMap()) ;
            }
            // 系统中定义,请求page页面 都以.page结尾
        } else if (url.endsWith(".page")){
            log.info("unknow page Exception , url:"+url, e);
            JsonData result = JsonData.fail(defaultMsg) ;
            mv = new ModelAndView("exception", result.toMap()) ;
        } else {
            log.info("unknow Exception , url:"+url, e);
            JsonData result = JsonData.fail(defaultMsg) ;
            mv = new ModelAndView("jsonView", result.toMap()) ;
        }
        return null;
    }
}
