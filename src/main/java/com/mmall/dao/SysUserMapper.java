package com.mmall.dao;

import com.mmall.beans.PageQuery;
import com.mmall.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hx
 * @create 2020-03-16 22:49
 */
public interface SysUserMapper {

    /**
     * 根据id删除信息
     * @param id
     *          需要删除数据的id
     * @return
     *          返回值
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * @param record
     *              新增信息对象
     * @return
     *          返回值
     */
    int insert(SysUser record);

    /**
     * 新增 （对传入的值做非空判断）
     * @param record
     *              新增数据对象
     * @return
     *          返回值
     */
    int insertSelective(SysUser record);

    /**
     * 根据id查询对象
     * @param id
     *          数据id
     * @return
     *          返回值
     */
    SysUser selectByPrimaryKey(Integer id);

    /**
     * 更新 （对传入的值做非空判断）
     * @param record
     *              更新数据对象
     * @return
     *          返回值
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 更新
     * @param record
     *              更新数据对象
     * @return
     *          返回值
     */
    int updateByPrimaryKey(SysUser record);

    /**
     * 根据关键字查询用户
     * @param keyword
     *                 关键字
     * @return
     *      返回值
     */
    SysUser findByKeyword(@Param("keyword") String keyword) ;

    /**
     * 根据邮箱是否重复
     * @param mail
     *            邮箱
     * @param userId
     *              用户id
     * @return
     *         返回值
     */
    int countByMail(@Param("mail") String mail, @Param("id") Integer userId) ;

    /**
     * 根据电话号码查询用户是否重复
     * @param telephone
     *                 电话号码
     * @param userId
     *              用户id
     * @return
     *         返回值
     */
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer userId) ;

    /**
     * 根据部门id统计部门人数
     * @param deptId
     *             部门id
     * @return
     *          返回值
     */
    int countByDeptId(@Param("deptId") int deptId) ;

    /**
     * 根据部门查询用户
     * @param deptId
     *              部门id
     * @param page
     *             每页的用户人数
     * @return
     *         返回值
     */
    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page")PageQuery page) ;

    /**
     * 根据userId查询用户
     * @param idList
     *              用户idList
     * @return
     *         返回值
     */
    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

    /**
     * 查询所有用户
     * @return
     *      返回值
     */
    List<SysUser> getAll() ;
}