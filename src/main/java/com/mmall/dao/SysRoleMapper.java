package com.mmall.dao;

import com.mmall.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hx
 * @create 2020-03-16 22:49
 */
public interface SysRoleMapper {

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
     *         返回值
     */
    int insert(SysRole record);

    /**
     * 新增（对传入的值做非空判断）
     * @param record
     *              新增数据对象
     * @return
     *          返回值
     */
    int insertSelective(SysRole record);

    /**
     * 根据id查询数据
     * @param id
     *          需要查询数据的id
     * @return
     *          返回值
     */
    SysRole selectByPrimaryKey(Integer id);

    /**
     * 更新（对传入的值做非空判断）
     * @param record
     *              更新数据的对象
     * @return
     *          返回值
     */
    int updateByPrimaryKeySelective(SysRole record);

    /**
     * 更新
     * @param record
     *              更新数据对象
     * @return
     *          返回值
     */
    int updateByPrimaryKey(SysRole record);

    /**
     * 判断是否有重复的角色
     * @param name
     *          角色名称
     * @return
     *         返回值
     */
    int countByName(@Param("name") String name, @Param("id") Integer id) ;

    /**
     * 查询所有角色
     * @return
     *        返回值
     */
    List<SysRole> getAll();

    /**
     * 获取角色集合
     * @param idList
     *              角色id集合
     * @return
     *         返回值
     */
    List<SysRole> getByIdList(@Param("idList") List<Integer> idList);
}