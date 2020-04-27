package com.mmall.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hx
 * @create 2020-04-02 19:51
 *
 * 定义页面常用的返回数据格式
 */

@Getter
@Setter
public class JsonData {

    /**
     * 返回值为结果
     */
    private boolean ret ;

    /**
     * 返回信息
     */
    private String msg ;

    /**
     * 返回数据
     */
    private Object data ;

    public JsonData(boolean ret){
        this.ret = ret ;
    }

    /**
     * 返回数据和信息
     * @param object
     *              对象值
     * @param msg
     *            返回的信息
     * @return
     *         返回值
     */
    public static JsonData success(Object object, String msg){
        JsonData jsonData = new JsonData(true) ;
        jsonData.data = object ;
        jsonData.msg = msg ;

        return jsonData ;
    }

    /**
     * 只返回数据
     * @param object
     *              返回的对象值
     * @return
     *          返回值
     */
    public static JsonData success(Object object){
        JsonData jsonData = new JsonData(true) ;
        jsonData.data = object ;

        return jsonData ;
    }

    /**
     * 只返回结果
     * @return
     *         返回值
     *
     */
    public static JsonData success(){
        return new JsonData(true) ;
    }


    /**
     * 失败时，返回的结果
     * @param msg
     *          返回的信息
     * @return
     *         返回值
     */
    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false) ;
        jsonData.msg = msg ;

        return jsonData ;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<String, Object>() ;
        result.put("ret", ret) ;
        result.put("msg", msg) ;
        result.put("data", data) ;

        return result ;

    }
}
