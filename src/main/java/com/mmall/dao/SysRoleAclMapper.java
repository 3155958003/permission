package com.mmall.dao;

import com.mmall.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hx
 * @create 2020-03-16 22:49
 */
public interface SysRoleAclMapper {

    /**
     * 根据id删除数据
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
    int insert(SysRoleAcl record);

    /**
     * 新增 （会对传入的值做非空判断）
     * @param record
     *              新增数据对象
     * @return
     *         返回值
     */
    int insertSelective(SysRoleAcl record);

    /**
     * 根据id查询数据
     * @param id
     *          需要查询的数据id
     * @return
     *          返回值
     */
    SysRoleAcl selectByPrimaryKey(Integer id);

    /**
     * 更新数据 （会对传入的值做非空判断）
     * @param record
     *              更新数据对象
     * @return
     *          返回值
     */
    int updateByPrimaryKeySelective(SysRoleAcl record);

    /**
     * 更新数据
     * @param record
     *              更新数据对象
     * @return
     *         返回值
     */
    int updateByPrimaryKey(SysRoleAcl record);

    /**
     * 根据权限模块id查询权限点集合
     * @param roleIdList
     *                  角色集合
     * @return
     *         返回值
     */
    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList) ;

    /**
     * 根据角色id进行删除
     * @param roleId
     *              角色id
     */
    void deleteByRoleId(@Param("roleId") int roleId);

    /**
     * 批量新增
     * @param roleAclList
     *                  角色权限集合
     */
    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    /**
     * 根据权限点获取角色
     * @param aclId
     *              权限点
     * @return
     *          返回值
     */
    List<Integer> getRoleIdListByAclId(@Param("aclId") int aclId);
}