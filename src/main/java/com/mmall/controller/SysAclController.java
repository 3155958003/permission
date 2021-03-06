package com.mmall.controller;

import com.google.common.collect.Maps;
import com.mmall.beans.PageQuery;
import com.mmall.common.JsonData;
import com.mmall.model.SysRole;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
import com.mmall.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author hx
 * @create 2020-04-18 21:27
 *
 * 权限点模块的控制层
 */

@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Resource
    private SysAclService sysAclService ;

    @Resource
    private SysRoleService sysRoleService ;

    /**
     * 新增权限点
     * @param param
     *              权限点参数
     * @return
     *          返回值
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAcl(AclParam param){
        sysAclService.save(param);
        return JsonData.success() ;
    }

    /**
     * 更新权限点参数
     * @param param
     *              权限点参数
     * @return
     *         返回值
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclParam param){
        sysAclService.update(param);
        return JsonData.success() ;
    }

    /**
     * 根据权限模块id查询权限点且分页
     * @param aclModuleId
     *                  模块权限id
     * @param pageQuery
     *                  分页数据
     * @return
     *          返回值
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }

    /**
     * 用户已分配的权限点
     * @param aclId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") int aclId){
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles", roleList);
        map.put("users", sysRoleService.getUserListByRoleList(roleList));
        return JsonData.success(map);
    }
}
