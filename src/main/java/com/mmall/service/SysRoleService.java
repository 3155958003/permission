package com.mmall.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysRole;
import com.mmall.model.SysUser;
import com.mmall.param.RoleParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hx
 * @create 2020-04-19 11:17
 *
 * 角色的服务层
 */

@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper ;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper ;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper ;

    @Resource
    private SysUserMapper sysUserMapper ;

    @Resource
    private SysLogService sysLogService ;


    /**
     * 新增角色
     * @param param
     *              传入的角色参数
     */
    public void save(RoleParam param){
        BeanValidator.check(param);
        if (checkExit(param.getName(), param.getId())){
            throw new ParamException("角色名称已经存在");
        }

        SysRole role = SysRole.builder().name(param.getName()).status(param.getStatus()).type(param.getType())
                .remark(param.getRemark()).build();

        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());

        sysRoleMapper.insertSelective(role);

        sysLogService.saveRoleLog(null, role);
    }

    /**
     * 更新角色
     * @param param
     */
    public void update(RoleParam param){
        BeanValidator.check(param);
        if (checkExit(param.getName(), param.getId())){
            throw new ParamException("角色名称已经存在");
        }

        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId()) ;
        Preconditions.checkNotNull(before, "待更新的角色不存在");

        SysRole after = SysRole.builder().id(param.getId()).name(param.getName())
                .status(param.getStatus()).type(param.getType())
                .remark(param.getRemark()).build();

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysRoleMapper.updateByPrimaryKeySelective(after) ;

        sysLogService.saveRoleLog(before, after);
    }

    /**
     * 查询所有角色信息
     * @return
     *        返回值
     */
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }


    /**
     * 判断是否有重复的角色
     * @param name
     *              角色名称
     * @param id
     *          角色id
     * @return
     *         返回值
     */
    private boolean checkExit(String name, Integer id){
        return sysRoleMapper.countByName(name, id) > 0;
    }

    /**
     * 根据用户id获取角色列表
     * @param userId
     *              用户id
     * @return
     *        返回值
     */
    public List<SysRole> getRoleListByUserId(int userId) {
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    /**
     * 根据权限点获取角色列表
     * @param aclId
     *              权限点
     * @return
     *         返回值
     */
    public List<SysRole> getRoleListByAclId(int aclId) {
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    /**
     * 根据角色列表获取用户列表
     * @param roleList
     *              角色列表
     * @return
     *         返回值
     */
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }


}
