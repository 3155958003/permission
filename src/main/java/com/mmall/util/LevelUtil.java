package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author hx
 * @create 2020-04-14 12:07
 *
 * 计算部门层级
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String calculateLevel(String levelParent, Integer parentId) {
        if (StringUtils.isBlank(levelParent)) {
            return ROOT;
        } else {

            return StringUtils.join(levelParent, SEPARATOR, parentId) ;
        }
    }

}
