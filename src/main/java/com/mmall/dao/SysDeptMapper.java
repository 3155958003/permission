package com.mmall.dao;

import com.mmall.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hx
 * @create 2020-03-16 22:49
 */
public interface SysDeptMapper {

    /**
     * 根据id删除信息
     * @param id
     *          删除信息的id
     * @return
     *          返回值
     */
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * 新增信息
     * @param record
     *              新增信息对象
     * @return
     *          返回值
     */
    int insert(SysDept record);

    /**
     * 新增信息 （会对传入的值做非空判断）
     * @param record
     *              新增信息对象
     * @return
     *         返回值
     */
    int insertSelective(SysDept record);

    /**
     * 根据id查询数据
     * @param id
     *          需要查询数据的id
     * @return
     *          返回值
     */
    SysDept selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 更新信息 （会对传入的值做非空判断）
     * @param record
     *              更新信息对象
     * @return
     *          返回值
     */
    int updateByPrimaryKeySelective(SysDept record);

    /**
     * 更新信息
     * @param record
     *              更新信息对象
     * @return
     *          返回值
     */
    int updateByPrimaryKey(SysDept record);

    /**
     * 获取所有部门
     * @return
     *        返回值
     */
    List<SysDept> getAllDept() ;

    /**
     * 根据部门等级获取部门
     * @param level
     *              部门层级
     * @return
     *         返回值
     */
    List<SysDept> getChildDeptListByLevel(@Param("level") String level) ;

    /**
     * 批量更新部门层级
     * @param sysDeptList
     *                  部门list
     */
    void batchUpdateLevel(List<SysDept> sysDeptList) ;

    /**
     * 查询部门是否存在
     * @param parentId
     *               父类id
     * @param name
     *              部门名称
     * @param id
     *            部门id
     * @return
     *          返回值
     */
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name,@Param("id") Integer id) ;

    /**
     * 判断是否有子部门
     * @param deptId
     *              部门id
     * @return
     *          返回值
     */
    int countByParentId(@Param("deptId") int deptId);
}