package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author hx
 * @create 2020-04-14 11:54
 */

@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper ;

    @Resource
    private SysUserMapper sysUserMapper ;

    @Resource
    private SysLogService sysLogService ;

    /**
     * 新增部门
     * @param param
     *              部门参数
     */
    public void save(DeptParam param){
        BeanValidator.check(param);
        if (checkExit(param.getParentId(), param.getName(), param.getId())){
            throw new ParamException("同一层下存在相同名称的部门") ;
        }

        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build() ;

        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));

        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

        dept.setOperateTime(new Date());

        sysDeptMapper.insertSelective(dept) ;

        sysLogService.saveDeptLog(null, dept);
    }

    /**
     * 更新部门
     * @param param
     *              部门参数
     */
    public void update(DeptParam param){
        BeanValidator.check(param);
        if (checkExit(param.getParentId(), param.getName(), param.getId())){
            throw new ParamException("同一层下存在相同名称的部门") ;
        }

        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId()) ;

        Preconditions.checkNotNull(before, "待更新的部门不存在") ;

        SysDept after = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build() ;
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp("127.0.0.1");

        after.setOperateTime(new Date());

        updateWithChild(before, after);

        sysLogService.saveDeptLog(before, after);
    }

    /**
     *  如果要保证事务生效，需要调整这个方法，
     *  一个可行的方法是重新创建一个service类，然后把这个方法转移过去
     * @param before
     *              更新前部门数据
     * @param after
     *              更新后部门数据
     */
    @Transactional
    public void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            String curLevel = before.getLevel() + "." + before.getId();
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(curLevel + "%");
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                        // getChildAclModuleListByLevel可能会取出多余的内容，因此需要加个判断
                        // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }

    /**
     * 校验部门是否存在
     * @param parentId
     *              父类部门id
     * @param deptName
     *                部门名称
     * @param deptId
     *              部门id
     * @return
     *          返回值
     */
    private boolean checkExit(Integer parentId, String deptName, Integer deptId){
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    /***
     * 获取部门层级
     * @param deptId
     *              部门id
     * @return
     *          返回部门id
     */
    private String getLevel(Integer deptId){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId) ;
        if (dept == null){
            return null ;
        }
        return dept.getLevel() ;
    }

    /**
     * 删除部门
     * @param deptId
     *             部门id
     */
    public void delete(int deptId){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId) ;
        Preconditions.checkNotNull(dept, "待删除的部门不存在") ;
        if (sysDeptMapper.countByParentId(dept.getId()) > 0){
            throw new ParamException("当前部门下有子部门,无法删除") ;
        }
        if (sysUserMapper.countByDeptId(dept.getId()) > 0){
            throw new ParamException("当前部门下有用户，无法删除") ;
        }

        sysDeptMapper.deleteByPrimaryKey(deptId) ;
    }
}
