package com.mmall.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.JsonData;
import com.mmall.model.SysUser;
import com.mmall.param.RoleParam;
import com.mmall.service.*;
import com.mmall.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hx
 * @create 2020-04-19 11:16
 *
 * 角色的控制层
 */

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService ;

    @Resource
    private SysTreeService sysTreeService ;

    @Resource
    private SysRoleAclService sysRoleAclService ;

    @Resource
    private SysRoleUserService sysRoleUserService ;

    @Resource
    private SysUserService sysUserService ;


    /**
     * 界面跳转
     * @return
     */
    @RequestMapping("role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    /**
     * 新增角色
     * @param param
     *              传入新增的角色参数
     * @return
     *          返回值
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam param){
        sysRoleService.save(param);
        return JsonData.success() ;
    }

    /**
     * 更新角色
     * @param param
     *              传入更新角色的参数
     * @return
     *         返回值
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateRole(RoleParam param){
        sysRoleService.update(param);
        return JsonData.success() ;
    }

    /**
     * 查询所有角色信息
     * @return
     *         返回值
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }

    /**
     * 权限树
     * @param roleId
     *              权限模块id
     * @return
     *          返回值
     */
    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId){
        return JsonData.success(sysTreeService.roleTree(roleId)) ;
    }

    /**
     * 保存角色管理模块中，角色与权限点的新增
     * @param roleId
     *              角色id
     * @param aclIds
     *              权限点
     * @return
     *          返回值
     */
    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId,
                               @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds){
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds) ;
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success() ;
    }

    /**
     * 保存角色和用户
     * @param roleId
     *              角色id
     * @param userIds
     *              用户ids
     * @return
     *          返回值
     */
    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId,
                               @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds){
        List<Integer> userIdList = StringUtil.splitToListInt(userIds) ;
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return JsonData.success() ;
    }

    /**
     * 用户与角色的关系
     * @return
     *         返回值
     */
    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId){
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId) ;
        List<SysUser> allUserList = sysUserService.getAll() ;
        List<SysUser> unselectedUserList = Lists.newArrayList() ;

        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for (SysUser sysUser: allUserList){
            if(sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())){
                unselectedUserList.add(sysUser) ;
            }
        }

        // selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return JsonData.success(map) ;
    }




}
