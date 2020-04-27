package com.mmall.dao;

import com.mmall.beans.PageQuery;
import com.mmall.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hx
 * @create 2020-03-16 22:49
 */
public interface SysAclMapper {

    /**
     * 根据主键id删除
     * @param id
     *          主键id值
     * @return
     *          返回值
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增数据
     * @param record
     *              新增数据对象
     * @return
     *         返回值
     */
    int insert(SysAcl record);

    /**
     *新增数据（对传入的值做非空判断）
     * @param record
     *              新增数据对象
     * @return
     *          返回值
     */
    int insertSelective(SysAcl record);

    /**
     * 根据id查询数据
     * @param id
     *          id值
     * @return
     *          返回数据
     */
    SysAcl selectByPrimaryKey(Integer id);

    /**
     * 根据主键值id进行更新 (对传入的值做非空判断)
     * @param record
     *              更新的对象
     * @return
     *         返回值
     */
    int updateByPrimaryKeySelective(SysAcl record);

    /**
     * 根据主键值id进行更新
     * @param record
     *              更新数据对象
     * @return
     *        返回值
     */
    int updateByPrimaryKey(SysAcl record);

    /**
     * 查询是否有重复的权限点
     * @param aclModuleId
     *                   权限模块id
     * @param name
     *              权限点名称
     * @param id
     *          权限点id
     * @return
     *         返回值
     */
    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

    /**
     * 根据权限模块id统计权限点
     * @param aclModuleId
     *                  权限模块id
     * @return
     *          返回值
     */
    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

    /**
     * 根据权限模块查询权限点
     * @param aclModuleId
     *                  权限模块id
     * @param page
     *              分页数据
     * @return
     *         返回值
     */
    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page") PageQuery page);

    /**
     * 获取所有的权限
     * @return
     *          返回值
     */
    List<SysAcl> getAll() ;

    /**
     * 根据权限点集合查询数据
     * @param idList
     *              权限点集合
     * @return
     *          返回值
     */
    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList) ;

    /**
     * 根据url查询权限点
     * @param url
     *            传入的rl
     * @return
     *          返回值
     */
    List<SysAcl> getByUrl(@Param("url") String url);
}