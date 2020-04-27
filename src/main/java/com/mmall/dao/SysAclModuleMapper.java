package com.mmall.dao;

import com.mmall.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hx
 * @create 2020-03-16 22:49
 */
public interface SysAclModuleMapper {

    /**
     * 根据id删除数据
     * @param id
     *          需要删除数据的id值
     * @return
     *          返回值
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增信息
     * @param record
     *              新增数据对象
     * @return
     *         返回值
     */
    int insert(SysAclModule record);

    /**
     * 新增信息（会对传入的值做非空判断）
     * @param record
     * @return
     */
    int insertSelective(SysAclModule record);

    /**
     * 根据id查询数据
     * @param id
     *          需要查询数据的id值
     * @return
     *         返回值
     */
    SysAclModule selectByPrimaryKey(Integer id);

    /**
     * 更新数据信息（会对传入的值做非空判断）
     * @param record
     *              更新数据对象
     * @return
     *         返回值
     */
    int updateByPrimaryKeySelective(SysAclModule record);

    /**
     * 更新数据信息
     * @param record
     *              更新数据对象
     * @return
     *         返回值
     */
    int updateByPrimaryKey(SysAclModule record);

    /**
     * 根据parentId查询是否有重复权限
     * @param parentId
     *              parentId
     * @param name
     *             权限名称
     * @param id
     *          权限id
     * @return
     *          返回值
     */
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

    /**
     * 根据层级查询权限模块
     * @param level
     *              层级
     * @return
     *         返回值
     */
    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);

    /**
     *  批量更新
     * @param sysAclModuleList
     *                      权限模块集合
     */
    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

    /**
     * 获取权限模块
     * @return
     *         返回值
     */
    List<SysAclModule> getAllAclModule() ;

    /**
     * 判断权限模块是否有其他权限模块
     * @param aclModuleId
     *                  权限模块id
     * @return
     *          返回值
     */
    int countByParentId(@Param("aclModuleId") int aclModuleId);


}