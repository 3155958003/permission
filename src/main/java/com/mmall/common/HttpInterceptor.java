package com.mmall.common;

import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author hx
 * @create 2020-04-14 11:25
 *
 * http 前后端请求监听
 */

@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "requestStartTime";

    /**
     * 请求之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  获取当前请求
        String url = request.getRequestURL().toString() ;

        //  获取参数
        Map parameterMap = request.getParameterMap() ;
        log.info("request start. url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        return true ;
    }

    /**
     * 请求时
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //  获取当前请求
        //  String url = request.getRequestURL().toString() ;
        //  long start = (Long) request.getAttribute(START_TIME);
        //  long end = System.currentTimeMillis();
        //  log.info("request finished. url:{}, cost:{}", url, end - start);

        removeThreadLocalInfo() ;
    }

    /**
     * 请求之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //  获取当前请求
        String url = request.getRequestURL().toString() ;

        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();

        removeThreadLocalInfo() ;
    }

    public void removeThreadLocalInfo(){
        RequestHolder.remove();
    }
}
