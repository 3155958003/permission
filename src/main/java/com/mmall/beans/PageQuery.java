package com.mmall.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author hx
 * @create 2020-04-17 09:27
 *  分页查询类
 */
public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1 ;

    @Getter
    @Setter
    @Max(value = 10, message = "每页展示数量不合法")
    private int pageSize = 10 ;

    @Setter
    private int offset ;

    public int getOffset(){
        return (pageNo - 1) * pageSize ;
    }
}
