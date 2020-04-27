package com.mmall.dao;

import com.mmall.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hx
 * @create 2020-03-16 22:49
 */
public interface SysRoleUserMapper {

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
    int insert(SysRoleUser record);

    /**
     * 新增 （对传入的值做非空判断）
     * @param record
     *              新增数据对象
     * @return
     *          返回值
     */
    int insertSelective(SysRoleUser record);

    /**
     * 根据id查询数据
     * @param id
     *          查询数据的id
     * @return
     *          返回值
     */
    SysRoleUser selectByPrimaryKey(Integer id);

    /**
     * 更新 （对传入的值做非空判断）
     * @param record
     *              更新数据对象
     * @return
     *          返回值
     */
    int updateByPrimaryKeySelective(SysRoleUser record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(SysRoleUser record);

    /**
     * 根据用户id查询权限点
     * @param userId
     *              用户id
     * @return
     *         返回值
     */
    List<Integer> getRoleIdListByUserId(@Param("userId") int userId) ;

    /**
     * 根据角色id查询用户
     * @param roleId
     *              角色id
     * @return
     *          返回值
     */
    List<Integer> getUserIdListByRoleId(@Param("roleId") int roleId);

    /**
     * 根据角色id删除信息
     * @param roleId
     *              角色id
     */
    void deleteByRoleId(@Param("roleId") int roleId);

    /**
     * 批量新增角色与用户关系
     * @param roleUserList
     *                    角色用户集合
     */
    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);

    /**
     * 根据角色列表获取用户
     * @param roleIdList
     *                  角色列表
     * @return
     *          返回值
     */
    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
}