package com.mmall.dao;

import com.mmall.beans.PageQuery;
import com.mmall.dto.SearchLogDto;
import com.mmall.model.SysLog;
import com.mmall.model.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hx
 * @create 2020-03-16 22:49
 */
public interface SysLogMapper {

    /**
     * 根据id删除信息
     * @param id
     *          需要删除数据的id值
     * @return
     *          返回值
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * @param record
     *              新增数据对象
     * @return
     *          返回值
     */
    int insert(SysLogWithBLOBs record);

    /**
     * 新增（会对传入的值做非空判断）
     * @param record
     * @return
     */
    int insertSelective(SysLogWithBLOBs record);

    /**
     * 根据id查询
     * @param id
     *          需要查询的id值
     * @return
     *          返回值
     */
    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * 更新 （会对传入的值做非空判断）
     * @param record
     *              更新的数据对象
     * @return
     *          返回值
     */
    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    /**
     * 更新
     * @param record
     *               更新的数据对象
     * @return
     *          返回值
     */
    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    /**
     * 更新
     * @param record
     *              更新的数据对象
     * @return
     *          返回值
     */
    int updateByPrimaryKey(SysLog record);

    /**
     * 统计日志查询值
     * @param dto
     *         查询值
     * @return
     *          返回值
     */
    int countBySearchDto(@Param("dto") SearchLogDto dto);

    List<SysLogWithBLOBs> getPageListBySearchDto(@Param("dto") SearchLogDto dto, @Param("page") PageQuery page);
}