package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

/**
 * @author hx
 * @create 2020-04-13 11:01
 *
 * 将一个对象转换成json，或将json装换成一个对象
 */

@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper() ;

    static {
        // config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    /**
     * 将对象转换成json字符
     * @param src
     *          字符
     * @param <T>
     *           某个对象
     * @return
     *          返回值
     */
    public static <T> String obj2String(T src){
        if (src == null){
            return null ;
        } else {
            try {
                return src instanceof String ? (String) src : objectMapper.writeValueAsString(src) ;
            } catch (Exception e){
                log.warn("parse object to String exception, error:{}", e);
                return null ;
            }
        }
    }

    /**
     * 将字符串转换成对象
     * @param src
     *          对象
     * @param tTypeReference
     *                      对象类型
     * @param <T>
     *             对象
     * @return
     *          返回值
     */
    public static <T> T string2Obj(String src, TypeReference<T> tTypeReference){
        if (src == null && tTypeReference == null){
            return null ;
        } else {
            try {
                return  (T) (tTypeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, tTypeReference));
            }catch (Exception e){
                log.warn("parse String to Object exception, String:{}, TypeReference<T>:{}, error:{}", src, tTypeReference.getType(), e);
                return null;
            }
        }
    }

}
